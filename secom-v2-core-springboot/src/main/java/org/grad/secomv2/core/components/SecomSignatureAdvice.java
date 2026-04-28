/*
 * Copyright (c) 2026 GLA Research and Development Directorate
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
package org.grad.secomv2.core.components;


import jakarta.xml.bind.DatatypeConverter;
import org.grad.secomv2.core.base.*;
import org.grad.secomv2.core.exceptions.SecomInvalidCertificateException;
import org.grad.secomv2.core.exceptions.SecomSignatureVerificationException;
import org.grad.secomv2.core.models.AbstractEnvelope;
import org.grad.secomv2.core.models.DigitalSignatureValueObject;
import org.grad.secomv2.core.models.ExchangeMetadata;
import org.grad.secomv2.core.models.enums.DigitalSignatureAlgorithmEnum;
import org.grad.secomv2.core.utils.PkiUtils;
import org.grad.secomv2.core.utils.SecomPemUtils;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Optional;

/**
 * {Description}
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@ControllerAdvice
public class SecomSignatureAdvice implements RequestBodyAdvice {

    private final SecomCompressionProvider compressionProvider;
    private final SecomEncryptionProvider encryptionProvider;
    private final SecomTrustStoreProvider trustStoreProvider;
    private final SecomSignatureProvider signatureProvider;


    public SecomSignatureAdvice(SecomCompressionProvider compressionProvider,
                                SecomEncryptionProvider encryptionProvider,
                                SecomTrustStoreProvider trustStoreProvider,
                                SecomSignatureProvider signatureProvider) {
        this.compressionProvider = compressionProvider;
        this.encryptionProvider = encryptionProvider;
        this.trustStoreProvider = trustStoreProvider;
        this.signatureProvider = signatureProvider;
    }

    @NullMarked
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return EnvelopeSignatureBearer.class
                .isAssignableFrom(methodParameter.getParameterType());
    }

    @NullMarked
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage,
                                           MethodParameter parameter,
                                           Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @NullMarked
    @Override
    public Object afterBodyRead(Object body,
                                HttpInputMessage inputMessage,
                                MethodParameter parameter,
                                Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        // Path check
        ServletServerHttpRequest servletRequest =
                (ServletServerHttpRequest) inputMessage;

        String path = servletRequest.getServletRequest().getRequestURI();

        if (!path.startsWith("/" + SecomConstants.SECOM_VERSION)) {
            return body;
        }

        if (!(body instanceof EnvelopeSignatureBearer obj)) {
            return body;
        }

        validateSignature(obj);

        return body;
    }

    @NullMarked
    @Override
    public @Nullable Object handleEmptyBody(@Nullable Object body,
                                            HttpInputMessage inputMessage,
                                            MethodParameter parameter,
                                            Type targetType,
                                            Class<? extends HttpMessageConverter<?>> converterType) {
        return null;
    }

    private void validateSignature(EnvelopeSignatureBearer obj) {
        boolean valid = true;

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
                                    .map(ExchangeMetadata::getDigitalSignatureValue)
                                    .map(DigitalSignatureValueObject::getPublicCertificate)
                                    .orElse(null),
                            Optional.of(dataObj)
                                    .map(DigitalSignatureBearer::getExchangeMetadata)
                                    .map(ExchangeMetadata::getDigitalSignatureValue)
                                    .map(DigitalSignatureValueObject::getPublicRootCertificateThumbprint)
                                    .orElse(null)
                    );
                }

                // Then validate the data signature
                valid &= this.signatureProvider.validateSignature(
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::getExchangeMetadata)
                                .map(ExchangeMetadata::getDigitalSignatureValue)
                                .map(DigitalSignatureValueObject::getPublicCertificate)
                                .orElse(null),
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::getExchangeMetadata)
                                .map(ExchangeMetadata::getDigitalSignatureReference)
                                .orElse(digitalSignatureAlgorithm),
                        Optional.of(dataObj)
                                .map(DigitalSignatureBearer::getExchangeMetadata)
                                .map(ExchangeMetadata::getDigitalSignatureValue)
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
     * As specified in section 6.2.2 of SECOM, the procedure to verify the
     * client’s certificate shall be:
     * <ul>
     *     <li>the server shall verify that the client’s certificate is valid;</li>
     *     <li>the server shall verify that the client’s certificate is issued by SECOM PKI.</li>
     * </ul>
     * @param certificates                   The received certificate to be verified
     * @param rootCertificateThumbprint     The received root certificate thumbprint
     */
    private void checkCertificate(String[] certificates, String rootCertificateThumbprint) {
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
        final X509Certificate[] x509Certificates;
        try {
            x509Certificates = SecomPemUtils.getCertsFromPem(certificates);
            if(x509Certificates != null) {
                for(X509Certificate x509Certificate : x509Certificates) {
                    x509Certificate.checkValidity();
                }
            }
        } catch (CertificateException ex) {
            throw new SecomInvalidCertificateException(ex.getMessage());
        }

        // Finally verify the provided certificate and check its validity
        try {
            if(!PkiUtils.verifyCertificateChain(x509Certificates, trustStore)) {
                throw new SecomInvalidCertificateException("Failed to verify the certificate chain...");
            }
        } catch (GeneralSecurityException ex) {
            throw new SecomInvalidCertificateException(ex.getMessage());
        }
    }


}
