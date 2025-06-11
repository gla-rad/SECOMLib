/*
 * Copyright (c) 2025 GLA Research and Development Directorate
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
import org.grad.secom.core.models.DigitalSignatureValueObject;
import org.grad.secom.core.models.ExchangeMetadata;
import org.grad.secom.core.utils.SecomPemUtils;

import jakarta.xml.bind.DatatypeConverter;
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
public interface DigitalSignatureBearer extends GenericSignatureBearer, GenericExchangeMetadataBearer, GenericDataBearer {

    /**
     * This is the main function that sets the digital signature onto a SECOM
     * signature bearer message.
     *
     * @param digitalSignature  The digital signature to be set
     */
    @Override
    default void setDigitalSignature(String digitalSignature) {
        Optional.of(this)
                .map(DigitalSignatureBearer::getExchangeMetadata)
                .map(ExchangeMetadata::getDigitalSignatureValue)
                .ifPresent(digitalSignatureValue -> digitalSignatureValue.setDigitalSignature(digitalSignature));
    }

    /**
     * This is a helper function to initialise the populate the SECOM exchange
     * metadata object with the appropriate values, depending on the current
     * configuration and available resources.
     *
     * @param signatureProvider     The SECOM signature provider, if it exists
     * @return the updated digital signature bearer
     */
    @Override
    default DigitalSignatureBearer prepareMetadata(SecomSignatureProvider signatureProvider) {
        return (DigitalSignatureBearer) GenericExchangeMetadataBearer.super.prepareMetadata(signatureProvider);
    }

    /**
     * A helper function that automatically encodes the provided data into
     * a Base64 string.
     *
     * @return the updated digital signature bearer
     */
    @Override
    default DigitalSignatureBearer encodeData() {
        return (DigitalSignatureBearer) GenericDataBearer.super.encodeData();
    }

    /**
     * A helper function that automatically decodes the Base64 data into the
     * required string.
     *
     * @return the updated digital signature bearer
     */
    @Override
    default DigitalSignatureBearer decodeData() {
        return (DigitalSignatureBearer) GenericDataBearer.super.decodeData();
    }

    /**
     * A helper function that automatically encrypts the data bearers data
     * payload using the SECOM encryption provider.
     *
     * @param encryptionProvider    The SECOM encryption provider
     * @return the updated digital signature bearer
     */
    @Override
    default DigitalSignatureBearer encryptData(SecomEncryptionProvider encryptionProvider) {
        if(encryptionProvider != null) {
            this.getExchangeMetadata().setDataProtection(Boolean.TRUE);
        }
        return (DigitalSignatureBearer) GenericDataBearer.super.encryptData(encryptionProvider);
    }

    /**
     * A helper function that automatically dencrypts the data bearers data
     * payload using the SECOM encryption provider.
     *
     * @param encryptionProvider    The SECOM encryption provider
     * @return the updated digital signature bearer
     */
    @Override
    default DigitalSignatureBearer decryptData(SecomEncryptionProvider encryptionProvider) {
        if(this.getExchangeMetadata().getDataProtection()) {
            return (DigitalSignatureBearer) GenericDataBearer.super.decryptData(encryptionProvider);
        }
        return this;
    }

    /**
     * A helper function that automatically compresses the data bearers data
     * payload using the SECOM compression provider.
     *
     * @param compressionProvider   The SECOM compression provider
     * @return the updated digital signature bearer
     */
    @Override
    default DigitalSignatureBearer compressData(SecomCompressionProvider compressionProvider) {
        if(compressionProvider != null) {
            this.getExchangeMetadata().setCompressionFlag(Boolean.TRUE);
        }
        return (DigitalSignatureBearer) GenericDataBearer.super.compressData(compressionProvider);
    }

    /**
     * A helper function that automatically decompresses the data bearers data
     * payload using the SECOM compression provider.
     *
     * @param compressionProvider   The SECOM compression provider
     * @return the updated digital signature bearer
     */
    @Override
    default DigitalSignatureBearer decompressData(SecomCompressionProvider compressionProvider) {
        if(this.getExchangeMetadata().getCompressionFlag()) {
            return (DigitalSignatureBearer) GenericDataBearer.super.decompressData(compressionProvider);
        }
        return this;
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
     * @return the upated digital signature bearer
     */
    @JsonIgnore
    default DigitalSignatureBearer signData(SecomCertificateProvider certificateProvider, SecomSignatureProvider signatureProvider) {
        // Sanity Check
        if(signatureProvider == null) {
            return this;
        }

        // Get the certificate to be used for singing the message
        final DigitalSignatureCertificate signatureCertificate = Optional.ofNullable(certificateProvider)
                .map(SecomCertificateProvider::getDigitalSignatureCertificate)
                .orElse(null);

        // If we have a signature certificate and metadata, update the metadata
        if(this.getExchangeMetadata() != null) {
            final ExchangeMetadata metadata = this.getExchangeMetadata();
            metadata.setDigitalSignatureValue(Optional.of(metadata)
                    .map(ExchangeMetadata::getDigitalSignatureValue)
                    .orElseGet(DigitalSignatureValueObject::new));

            // If we have a certificate set it in the metadata
            if(signatureCertificate != null) {
                try {
                    metadata.getDigitalSignatureValue().setPublicCertificate(SecomPemUtils.getMinifiedPemFromCerts(signatureCertificate.getCertificate()));
                    metadata.getDigitalSignatureValue().setPublicRootCertificateThumbprint(SecomPemUtils.getCertThumbprint(signatureCertificate.getRootCertificate(), SecomConstants.CERTIFICATE_THUMBPRINT_HASH));
                } catch (CertificateEncodingException | NoSuchAlgorithmException ex) {
                    throw new SecomInvalidCertificateException(ex.getMessage());
                }
            }
        }

        // And sign the data
        byte[] signature = signatureProvider.generateSignature(signatureCertificate, signatureProvider.getSignatureAlgorithm(), this.getData());
        final String signatureHex =  Optional.ofNullable(signature).filter(ba -> ba.length>0).map(DatatypeConverter::printHexBinary).orElse(null);
        this.setDigitalSignature(signatureHex);

        // Return the same object for further processing
        return this;
    }

}
