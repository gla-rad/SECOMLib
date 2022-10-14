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

package org.grad.secom.core.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.grad.secom.core.exceptions.SecomInvalidCertificateException;
import org.grad.secom.core.models.DigitalSignatureValue;
import org.grad.secom.core.models.SECOM_ExchangeMetadataObject;
import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;
import org.grad.secom.core.utils.SecomPemUtils;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.util.Optional;

/**
 * The SECOM Digital Signature Bearer Interface.
 *
 * This interface forces the implementing classes to provide a way of signing
 * the contained data using a SECOM signature provider implementation.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface DigitalSignatureBearer extends GenericSignatureBearer {

    /**
     * Allows the data signature bearer object to access to data included to
     * be signed.
     *
     * @return the data included in the data signature bearer
     */
    @JsonIgnore
    String getData();

    /**
     * Allows the data signature bearer object to access the SECOM exchange
     * metadata that will contain the signature information.
     *
     * @return the signature information of the data signature bearer
     */
    @JsonIgnore
    SECOM_ExchangeMetadataObject getExchangeMetadata();

    /**
     * This is the main function that sets the digital signature onto a SECOM
     * signature bearer message.
     *
     * @param digitalSignature  The digital signature to be set
     */
    @JsonIgnore
    @Override
    default void setDigitalSignature(String digitalSignature) {
        Optional.of(this)
                .map(DigitalSignatureBearer::getExchangeMetadata)
                .map(SECOM_ExchangeMetadataObject::getDigitalSignatureValue)
                .ifPresent(digitalSignatureValue -> digitalSignatureValue.setDigitalSignature(digitalSignature));
    }

    /**
     * This function performs the actual signing operation of the data signature
     * bearer. In this, the data to be signed is accessed and translated onto
     * a byte array. Then the provided SECOM signature provider will be used
     * to perform the signing wich will be used to generate and attach the
     * SECOM exchange metadata.
     *
     * @param certificateProvider   The SECOM certificate provider to be used
     * @param signatureProvider     The SECOM signature provider to be used.
     */
    @JsonIgnore
    default void signData(SecomCertificateProvider certificateProvider, SecomSignatureProvider signatureProvider) {
        // Get the certificate to be used for singing the message
        DigitalSignatureCertificate signatureCertificate = Optional.ofNullable(certificateProvider)
                .map(SecomCertificateProvider::getDigitalSignatureCertificate)
                .orElse(null);

        // If we have a signature certificate and metadata, update the metadata
        if(signatureCertificate != null && this.getExchangeMetadata() != null) {
            SECOM_ExchangeMetadataObject metadata = this.getExchangeMetadata();
            metadata.setDataProtection(Boolean.TRUE);
            metadata.setCompressionFlag(Optional.of(metadata).map(SECOM_ExchangeMetadataObject::getCompressionFlag).orElse(Boolean.FALSE));
            metadata.setDigitalSignatureReference(signatureProvider.getSignatureAlgorithm());
            metadata.setProtectionScheme(SecomConstants.SECOM_PROTECTION_SCHEME);
            metadata.setDigitalSignatureValue(Optional.of(metadata)
                    .map(SECOM_ExchangeMetadataObject::getDigitalSignatureValue)
                    .orElseGet(DigitalSignatureValue::new));

            try {
                metadata.getDigitalSignatureValue().setPublicCertificate(SecomPemUtils.getMinifiedPemFromCert(signatureCertificate.getCertificate()));
                metadata.getDigitalSignatureValue().setPublicRootCertificateThumbprint(SecomPemUtils.getCertThumbprint(signatureCertificate.getRootCertificate(), SecomConstants.CERTIFICATE_THUMBPRINT_HASH));
            } catch (CertificateEncodingException | NoSuchAlgorithmException ex) {
                throw new SecomInvalidCertificateException(ex.getMessage());
            }
        }

        // If we have a signature provider, generate the signature
        if(signatureCertificate != null) {
            // Get the data to be signed
            final byte[] payload = Optional.of(this)
                    .map(DigitalSignatureBearer::getData)
                    .map(String::getBytes)
                    .orElse(new byte[]{});

            this.setDigitalSignature(signatureProvider.generateSignature(signatureCertificate, signatureProvider.getSignatureAlgorithm().getValue(), payload));
        }
    }

}
