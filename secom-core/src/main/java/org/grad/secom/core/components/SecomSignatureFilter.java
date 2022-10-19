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

package org.grad.secom.core.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grad.secom.core.base.*;
import org.grad.secom.core.exceptions.SecomInvalidCertificateException;
import org.grad.secom.core.exceptions.SecomSignatureVerificationException;
import org.grad.secom.core.interfaces.AcknowledgementSecomInterface;
import org.grad.secom.core.interfaces.EncryptionKeySecomInterface;
import org.grad.secom.core.interfaces.UploadLinkSecomInterface;
import org.grad.secom.core.interfaces.UploadSecomInterface;
import org.grad.secom.core.models.*;
import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;
import org.grad.secom.core.utils.SecomPemUtils;

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
import java.security.cert.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    private SecomCertificateProvider certificateProvider;
    private SecomSignatureValidator signatureValidator;

    /**
     * The Class Constructor.
     *
     * @param signatureValidator    The signature validator
     */
    public SecomSignatureFilter(SecomCertificateProvider certificateProvider, SecomSignatureValidator signatureValidator) {
        this.certificateProvider = certificateProvider;
        this.signatureValidator = signatureValidator;
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
        if(this.signatureValidator == null) {
             return;
        }

        // Start with a true valid flag
        boolean valid = true;
        EnvelopeSignatureBearer obj = null;

        // Currently SECOM only needs to validate POST requests
        if(rqstCtx.getRequest().getMethod().equals("POST")) {
            // For the Upload Interface Requests
            if (rqstCtx.getUriInfo().getPath().endsWith(UploadSecomInterface.UPLOAD_INTERFACE_PATH)){
                obj = this.parseRequestBody(rqstCtx, UploadObject.class);
            }
            // For the Upload Link Interface Requests
            else if (rqstCtx.getUriInfo().getPath().endsWith(UploadLinkSecomInterface.UPLOAD_LINK_INTERFACE_PATH)) {
                obj = this.parseRequestBody(rqstCtx, UploadLinkObject.class);
            }
            // For the Acknowledgement Interface Requests
            else if (rqstCtx.getUriInfo().getPath().endsWith(AcknowledgementSecomInterface.ACKNOWLEDGMENT_INTERFACE_PATH)) {
                obj = this.parseRequestBody(rqstCtx, AcknowledgementObject.class);
            }
            // For the Encryption Key Interface Requests
            else if (rqstCtx.getUriInfo().getPath().endsWith(EncryptionKeySecomInterface.ENCRYPTION_KEY_INTERFACE_PATH)) {
                obj = this.parseRequestBody(rqstCtx, EncryptionKeyObject.class);
            }
        }

        // If we have an object, validate the signatures
        if(obj != null && obj.getEnvelope() != null) {
            // First decide on the signature algorithm
            final DigitalSignatureAlgorithmEnum digitalSignatureAlgorithm = Optional.ofNullable(this.signatureValidator)
                    .map(SecomSignatureValidator::getSignatureAlgorithm)
                    .orElse(DigitalSignatureAlgorithmEnum.DSA);

            // First validate the envelope certificate
            checkCertificate(
                    obj.getEnvelope().getEnvelopeSignatureCertificate(),
                    obj.getEnvelope().getEnvelopeRootCertificateThumbprint()
            );

            // Then validate the envelope signature
            valid &= this.signatureValidator.validateSignature(
                    Optional.of(obj)
                            .map(EnvelopeSignatureBearer::getEnvelope)
                            .map(AbstractEnvelope::getEnvelopeSignatureCertificate)
                            .orElse(null),
                    digitalSignatureAlgorithm,
                    Optional.of(obj)
                            .map(EnvelopeSignatureBearer::getEnvelope)
                            .map(AbstractEnvelope::getCsvString)
                            .orElse(null),
                    Optional.of(obj)
                            .map(EnvelopeSignatureBearer::getEnvelopeSignature)
                            .map(DatatypeConverter::parseHexBinary)
                            .orElse(null));

            // Then validate the data signature if present
            if(obj.getEnvelope() instanceof DigitalSignatureBearer) {
                final DigitalSignatureBearer dataObj = (DigitalSignatureBearer)obj.getEnvelope();

                // First validate the data certificate
                checkCertificate(
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::getExchangeMetadata)
                                .map(SECOM_ExchangeMetadataObject::getDigitalSignatureValue)
                                .map(DigitalSignatureValue::getPublicCertificate)
                                .orElse(null),
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::getExchangeMetadata)
                                .map(SECOM_ExchangeMetadataObject::getDigitalSignatureValue)
                                .map(DigitalSignatureValue::getPublicRootCertificateThumbprint)
                                .orElse(null)
                );

                // Then validate the data signature
                valid &= this.signatureValidator.validateSignature(
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::getExchangeMetadata)
                                .map(SECOM_ExchangeMetadataObject::getDigitalSignatureValue)
                                .map(DigitalSignatureValue::getPublicCertificate)
                                .orElse(null),
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::getExchangeMetadata)
                                .map(SECOM_ExchangeMetadataObject::getDigitalSignatureReference)
                                .orElse(digitalSignatureAlgorithm),
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::getData)
                                .orElse(null),
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::getExchangeMetadata)
                                .map(SECOM_ExchangeMetadataObject::getDigitalSignatureValue)
                                .map(DigitalSignatureValue::getDigitalSignature)
                                .map(DatatypeConverter::parseHexBinary)
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
        // Get our own root certificate
        final X509Certificate rootX509Certificate = Optional.ofNullable(this.certificateProvider)
                .map(SecomCertificateProvider::getDigitalSignatureCertificate)
                .map(DigitalSignatureCertificate::getRootCertificate)
                .orElse(null);

        // Get the whole list of trusted certificates
        final X509Certificate[] trustedCertificates = Optional.ofNullable(this.certificateProvider)
                .map(SecomCertificateProvider::getTrustedCertificates)
                .orElse(null);

        // In SECOM the root certificate is actually optional, so just check if
        // it exists and id so match the thumbprint with our root certificate
        if(rootCertificateThumbprint != null) {
            Optional.ofNullable(rootX509Certificate)
                    .map(rc -> {
                        try {
                            return SecomPemUtils.getCertThumbprint(rc, SecomConstants.CERTIFICATE_THUMBPRINT_HASH);
                        } catch (Exception ex) {
                            return null;
                        }
                    })
                    .filter(thumbprint -> thumbprint.compareTo(rootCertificateThumbprint) == 0)
                    .orElseThrow(() -> new SecomInvalidCertificateException("The provided SECOM CA root certificate is not recognised"));
        }

        // Now parse the provided certificate into an X509 Certificate object
        final X509Certificate x509Certificate;
        try {
            x509Certificate = SecomPemUtils.getCertFromPem(certificate);
        } catch (CertificateException ex) {
            throw new SecomInvalidCertificateException(ex.getMessage());
        }

        // Now verify the provided certificate and check its validity
        try {
            x509Certificate.checkValidity();
            this.verifyCertificateChain(x509Certificate, new HashSet<>(Arrays.asList(rootX509Certificate)), new HashSet<>(Arrays.asList(trustedCertificates)));
        } catch (Exception ex) {
            throw new SecomInvalidCertificateException(ex.getMessage());
        }
    }

    /**
     * This function uses the CertPathBuilder method to build the certificate
     * path, including the SECOM X.509 certificate received with this request
     * to verify that it's valid and a part of the chain that includes the
     * root certificate of the SECOM service. It does not return an output
     * but will raise a GeneralSecurityException if the operation fails.
     *
     * @param cert                  the certificate to be validated
     * @param trustedRootCerts      the list of trusted certificates
     * @param intermediateCerts     the list of intermediate certificates
     * @throws GeneralSecurityException An exception will be raised if the validation fails
     */
    private void verifyCertificateChain(X509Certificate cert,
                                       Set<X509Certificate> trustedRootCerts,
                                       Set<X509Certificate> intermediateCerts) throws GeneralSecurityException {
        // Create the selector that specifies the starting certificate
        final X509CertSelector selector = new X509CertSelector();
        selector.setCertificate(cert);

        // Create the trust anchors (set of root CA certificates)
        final Set<TrustAnchor> trustAnchors = new HashSet<>();
        for (X509Certificate trustedRootCert : trustedRootCerts) {
            trustAnchors.add(new TrustAnchor(trustedRootCert, null));
        }

        // Configure the PKIX certificate builder algorithm parameters
        final PKIXBuilderParameters pkixParams = new PKIXBuilderParameters(trustAnchors, selector);

        // Disable CRL checks (this is done manually as additional step)
        pkixParams.setRevocationEnabled(false);

        // To check digitalSignature and keyEncipherment bits
        final X509CertSelector keyUsageSelector = new X509CertSelector();
        keyUsageSelector.setKeyUsage(new boolean[]{true, false, true});
        pkixParams.setTargetCertConstraints(keyUsageSelector);

        // Specify a list of intermediate certificates
        // certificate itself has to be added to the list
        intermediateCerts.add(cert);
        final CertStore intermediateCertStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(intermediateCerts), "BC");
        pkixParams.addCertStore(intermediateCertStore);

        // Build and verify the certification chain
        final CertPathBuilder builder = CertPathBuilder.getInstance("PKIX", "BC");
        builder.build(pkixParams);
    }
}
