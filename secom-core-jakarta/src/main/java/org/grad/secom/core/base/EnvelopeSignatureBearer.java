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
import org.grad.secom.core.models.AbstractEnvelope;
import org.grad.secom.core.models.SECOM_ExchangeMetadataObject;
import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;
import org.grad.secom.core.utils.SecomPemUtils;

import jakarta.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.time.Instant;
import java.util.Optional;

/**
 * The SECOM Envelope Signature Bearer Interface.
 *
 * This interface forces the implementing classes to provide a way of signing
 * the contained data using a SECOM signature provider implementation.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface EnvelopeSignatureBearer extends GenericSignatureBearer {

    /**
     * Allows the envelope signature bearer object to access the envelope to be
     * used to product the signature.
     *
     * @return the signature information of the data signature bearer
     */
    AbstractEnvelope getEnvelope();

    /**
     * gets envelope signature.
     *
     * @return the envelope signature
     */
    String getEnvelopeSignature();

    /**
     * Sets envelope signature.
     *
     * @param envelopeSignature the envelope signature
     */
    void setEnvelopeSignature(String envelopeSignature);

    /**
     * This is the main function that sets the digital signature onto a SECOM
     * signature bearer message.
     *
     * @param digitalSignature  The digital signature to be set
     */
    @Override
    default void setDigitalSignature(String digitalSignature) {
        this.setEnvelopeSignature(digitalSignature);
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
     * @return the updated envelope signature bearer
     */
    @JsonIgnore
    default EnvelopeSignatureBearer signEnvelope(SecomCertificateProvider certificateProvider, SecomSignatureProvider signatureProvider) {
        // Sanity Check
        if(signatureProvider == null) {
            return this;
        }

        // Get the certificate to be used for singing the envelope
        final DigitalSignatureCertificate signatureCertificate = Optional.ofNullable(certificateProvider)
                .map(SecomCertificateProvider::getDigitalSignatureCertificate)
                .orElse(null);

        // If we have a signature certificate, update the envelope
        if(signatureCertificate != null) {
            try {
                this.getEnvelope().setEnvelopeSignatureCertificate(SecomPemUtils.getMinifiedPemFromCert(signatureCertificate.getCertificate()));
                this.getEnvelope().setEnvelopeRootCertificateThumbprint(SecomPemUtils.getCertThumbprint(signatureCertificate.getRootCertificate(), SecomConstants.CERTIFICATE_THUMBPRINT_HASH));
                this.getEnvelope().setEnvelopeSignatureTime(Instant.now());
            } catch (CertificateEncodingException | NoSuchAlgorithmException exception) {
                throw new SecomInvalidCertificateException(exception.getMessage());
            }
        }

        // And sign the envelope
        final byte[] signature = signatureProvider.generateSignature(signatureCertificate, signatureProvider.getSignatureAlgorithm(), this.getEnvelope().getCsvString().getBytes(StandardCharsets.UTF_8));
        final String signatureHex =  Optional.ofNullable(signature).filter(ba -> ba.length>0).map(DatatypeConverter::printHexBinary).orElse(null);
        this.setEnvelopeSignature(signatureHex);

        // Return the same object for further processing
        return this;
    }

    /**
     * A helper function that retrieves the envelope signature algorithm from
     * the exchange metadata level (if that exists) and returns it so that is
     * can be used during the signature checks.
     *
     * @return The digital signature algorithm type
     */
    @JsonIgnore
    default DigitalSignatureAlgorithmEnum getEnvelopeSignatureAlgorithm() {
        return Optional.ofNullable(this.getEnvelope())
                .filter(GenericExchangeMetadataBearer.class::isInstance)
                .map(GenericExchangeMetadataBearer.class::cast)
                .map(GenericExchangeMetadataBearer::getExchangeMetadata)
                .map(SECOM_ExchangeMetadataObject::getDigitalSignatureReference)
                .orElse(null);
    }

}
