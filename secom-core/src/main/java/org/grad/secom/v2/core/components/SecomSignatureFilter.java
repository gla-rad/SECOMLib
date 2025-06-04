/*
 * Copyright (c) 2022 GLA Research and Development Directorate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.grad.secom.v2.core.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grad.secom.v2.core.base.*;
import org.grad.secom.v2.core.exceptions.SecomInvalidCertificateException;
import org.grad.secom.v2.core.exceptions.SecomSignatureVerificationException;
import org.grad.secom.v2.core.interfaces.AcknowledgementServiceInterface;
import org.grad.secom.v2.core.interfaces.EncryptionKeyServiceInterface;
import org.grad.secom.v2.core.interfaces.UploadLinkServiceInterface;
import org.grad.secom.v2.core.interfaces.UploadServiceInterface;
import org.grad.secom.v2.core.models.*;
import org.grad.secom.v2.core.models.enums.DigitalSignatureAlgorithmEnum;
import org.grad.secom.v2.core.utils.PkiUtils;
import org.grad.secom.v2.core.utils.SecomPemUtils;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Optional;

/**
 * The SECOM Signature Filter
 *
 * When receiving a SECOM request, upload (and upload linke) messages might
 * contain a signature. These need to be verified to ensure that the request
 * has not been altered by unauthorised entities.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Provider
@PreMatching
public class SecomSignatureFilter implements ContainerRequestFilter {

    /**
     * The JAX-RS Providers Context.
     */
    @Context
    Providers providers;

    // Class Variables
    private SecomCompressionProvider compressionProvider;
    private SecomEncryptionProvider encryptionProvider;
    private SecomTrustStoreProvider trustStoreProvider;
    private SecomSignatureProvider signatureProvider;

    /**
     * The Class Constructor.
     *
     * @param trustStoreProvider    The SECOM trust store provider
     * @param signatureProvider     The SECOM signature provider
     */
    public SecomSignatureFilter(SecomCompressionProvider compressionProvider,
                                SecomEncryptionProvider encryptionProvider,
                                SecomTrustStoreProvider trustStoreProvider,
                                SecomSignatureProvider signatureProvider) {
        this.compressionProvider = compressionProvider;
        this.encryptionProvider = encryptionProvider;
        this.trustStoreProvider = trustStoreProvider;
        this.signatureProvider = signatureProvider;
    }

    /**
     * The ContainerResponseFilter filter function implementation.
     *
     * @param rqstCtx   The filter's request context
     * @throws IOException When IO Exceptions occur
     */
    @Override
    public void filter(ContainerRequestContext rqstCtx) throws IOException {
        // No need to do anything without a signature validator
        if(this.signatureProvider == null) {
             return;
        }

        // Start with a true valid flag
        boolean valid = true;
        EnvelopeSignatureBearer obj = null;

        // Currently, SECOM only needs to validate POST requests
        if(rqstCtx.getRequest().getMethod().equals("POST")) {
            // For the Upload Interface Requests
            if (rqstCtx.getUriInfo().getPath().endsWith(UploadServiceInterface.UPLOAD_INTERFACE_PATH)){
                obj = this.parseRequestBody(rqstCtx, UploadObject.class);
            }
            // For the Upload Link Interface Requests
            else if (rqstCtx.getUriInfo().getPath().endsWith(UploadLinkServiceInterface.UPLOAD_LINK_INTERFACE_PATH)) {
                obj = this.parseRequestBody(rqstCtx, UploadLinkObject.class);
            }
            // For the Acknowledgement Interface Requests
            else if (rqstCtx.getUriInfo().getPath().endsWith(AcknowledgementServiceInterface.ACKNOWLEDGMENT_INTERFACE_PATH)) {
                obj = this.parseRequestBody(rqstCtx, AcknowledgementObject.class);
            }
            // For the Encryption Key Interface Requests
            else if (rqstCtx.getUriInfo().getPath().endsWith(EncryptionKeyServiceInterface.ENCRYPTION_KEY_INTERFACE_PATH)) {
                obj = this.parseRequestBody(rqstCtx, EncryptionKeyObject.class);
            }
        }

        // If we have an object, validate the signatures
        if(obj != null && obj.getEnvelope() != null) {
            // First decide on the signature algorithm
            final DigitalSignatureAlgorithmEnum digitalSignatureAlgorithm = Optional.of(obj)
                    .map(EnvelopeSignatureBearer::getEnvelopeSignatureAlgorithm)
                    .orElseGet(() -> Optional.of(this.signatureProvider)
                            .map(SecomSignatureProvider::getSignatureAlgorithm)
                            .orElse(DigitalSignatureAlgorithmEnum.DSA));

            // Then validate the envelope certificate
            if(this.trustStoreProvider != null) {
                checkCertificate(
                        obj.getEnvelope().getEnvelopeSignatureCertificate(),
                        obj.getEnvelope().getEnvelopeRootCertificateThumbprint()
                );
            }

            // Then validate the envelope signature
            valid &= this.signatureProvider.validateSignature(
                    Optional.of(obj)
                            .map(EnvelopeSignatureBearer::getEnvelope)
                            .map(AbstractEnvelope::getEnvelopeSignatureCertificate)
                            .orElse(null),
                    digitalSignatureAlgorithm,
                    Optional.of(obj)
                            .map(EnvelopeSignatureBearer::getEnvelopeSignature)
                            .map(DatatypeConverter::parseHexBinary)
                            .orElse(null),
                    Optional.of(obj)
                            .map(EnvelopeSignatureBearer::getEnvelope)
                            .map(AbstractEnvelope::getCsvString)
                            .map(String::getBytes)
                            .orElse(null));

            // Finally validate the data signature if present
            if(obj.getEnvelope() instanceof DigitalSignatureBearer) {
                final DigitalSignatureBearer dataObj = (DigitalSignatureBearer)obj.getEnvelope();

                // First validate the data certificate
                if(this.trustStoreProvider != null) {
                    checkCertificate(
                            Optional.of(dataObj)
                                    .map(DigitalSignatureBearer::getExchangeMetadata)
                                    .map(SECOM_ServiceExchangeMetadataObject::getDigitalSignatureValue)
                                    .map(DigitalSignatureValueObject::getPublicCertificate)
                                    .orElse(null),
                            Optional.of(dataObj)
                                    .map(DigitalSignatureBearer::getExchangeMetadata)
                                    .map(SECOM_ServiceExchangeMetadataObject::getDigitalSignatureValue)
                                    .map(DigitalSignatureValueObject::getPublicRootCertificateThumbprint)
                                    .orElse(null)
                    );
                }

                // Then validate the data signature
                valid &= this.signatureProvider.validateSignature(
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::getExchangeMetadata)
                                .map(SECOM_ServiceExchangeMetadataObject::getDigitalSignatureValue)
                                .map(DigitalSignatureValueObject::getPublicCertificate)
                                .orElse(null),
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::getExchangeMetadata)
                                .map(SECOM_ServiceExchangeMetadataObject::getDigitalSignatureReference)
                                .orElse(digitalSignatureAlgorithm),
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::getExchangeMetadata)
                                .map(SECOM_ServiceExchangeMetadataObject::getDigitalSignatureValue)
                                .map(DigitalSignatureValueObject::getDigitalSignature)
                                .map(DatatypeConverter::parseHexBinary)
                                .orElse(null),
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::decodeData)
                                .map(dataBearer -> dataBearer.decompressData(this.compressionProvider))
                                .map(dataBearer -> dataBearer.decryptData(this.encryptionProvider))
                                .map(GenericDataBearer::getData)
                                .orElse(null));
            }
        }

        // For everything else just move one if valid
        if(!valid) {
            throw new SecomSignatureVerificationException("Received message signature could not be verified!");
        }
    }

    /**
     * A helper method that parses the data in the body of the incoming request.
     * One issue is that after we read that data, the request input stream is
     * left empty, so we need to recreate it with the original data once more.
     * <p/>
     * Here is a pointer: https://github.com/quarkusio/quarkus/issues/17430
     * <p/>
     * Once we have the request we can use the provided object mapper to
     * translate the JSON string into an actual SECOM object.
     *
     * @param rqstCtx       The incoming request context
     * @param clazz         The class to map the request body into
     * @return the mapped object, populated by the request body
     * @param <T> the generic class to use for the mapping
     * @throws IOException for any IO exceptions while reading the data
     */
    private <T> T parseRequestBody(ContainerRequestContext rqstCtx, Class<T> clazz) throws IOException {
        // Get the request input stream and read the data
        final InputStream is = rqstCtx.getEntityStream();
        final byte[] data = is.readAllBytes();
        final String body = new String(data, StandardCharsets.UTF_8);

        // Update the input stream with a new one to re-initialise it
        rqstCtx.setEntityStream(new ByteArrayInputStream(data));

        // Get the JAX-RS registered object mapper and map the data to the object
        return providers.getContextResolver(ObjectMapper.class, rqstCtx.getMediaType())
                .getContext(UploadObject.class)
                .readValue(body, clazz);
    }

    /**
     * As specified in section 6.2.2 of SECOM, the procedure to verify the
     * client’s certificate shall be:
     * <ul>
     *     <li>the server shall verify that the client’s certificate is valid;</li>
     *     <li>the server shall verify that the client’s certificate is issued by SECOM PKI.</li>
     * </ul>
     * @param certificate                   The received certificate to be verified
     * @param rootCertificateThumbprint     The received root certificate thumbprint
     */
    private void checkCertificate(String certificate, String rootCertificateThumbprint) {
        // Access out trust store
        final KeyStore trustStore = this.trustStoreProvider.getTrustStore();

        // Get our own root certificate
        final X509Certificate rootX509Certificate;
        try {
            rootX509Certificate = (X509Certificate) trustStore.getCertificate(this.trustStoreProvider.getCARootCertificateAlias());
        } catch (KeyStoreException ex) {
            throw new SecomInvalidCertificateException(ex.getMessage());
        }

        // In SECOM the root certificate is actually optional, so just check if
        // it exists and id so match the thumbprint with our root certificate
        if(rootCertificateThumbprint != null) {
            final String rootX509CertificateThumbprint;
            try {
                rootX509CertificateThumbprint = SecomPemUtils.getCertThumbprint(rootX509Certificate, SecomConstants.CERTIFICATE_THUMBPRINT_HASH);
                if(rootCertificateThumbprint.compareTo(rootX509CertificateThumbprint) != 0) {
                    throw new SecomInvalidCertificateException("The provided SECOM CA root certificate is not recognised");
                }
            } catch (GeneralSecurityException ex) {
                throw new SecomInvalidCertificateException("Failed to generate the SECOM CA root certificate thumbprint");
            }
        }

        // Now parse the provided certificate and check its validity
        final X509Certificate x509Certificate;
        try {
            x509Certificate = SecomPemUtils.getCertFromPem(certificate);
            if(x509Certificate != null) {
                x509Certificate.checkValidity();
            }
        } catch (CertificateException ex) {
            throw new SecomInvalidCertificateException(ex.getMessage());
        }

        // Finally verify the provided certificate and check its validity
        try {
            if(!PkiUtils.verifyCertificateChain(x509Certificate, trustStore)) {
                throw new SecomInvalidCertificateException("Failed to verify the certificate chain...");
            }
        } catch (GeneralSecurityException ex) {
            throw new SecomInvalidCertificateException(ex.getMessage());
        }
    }

}
