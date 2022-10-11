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
import org.grad.secom.core.models.AbstractEnvelope;
import org.grad.secom.core.models.DigitalSignatureValue;
import org.grad.secom.core.models.SECOM_ExchangeMetadataObject;
import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;
import org.grad.secom.core.utils.SecomPemUtils;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.time.LocalDateTime;
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
    @JsonIgnore
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
     * @param signatureProvider     The SECOM signature provider to be used.
     */
    @JsonIgnore
    default void signEnvelope(SecomSignatureProvider signatureProvider) {
        // Get the certificate to be used for singing the envelope
        DigitalSignatureCertificate signatureCertificate = signatureProvider.getSignatureCertificate();

        // Update the envelope with the certificate information
        try {
            this.getEnvelope().setEnvelopeSignatureCertificate(SecomPemUtils.getMinifiedPemFromCert(signatureCertificate.getCertificate()));
            this.getEnvelope().setEnvelopeRootCertificateThumbprint(SecomPemUtils.getCertThumbprint(signatureCertificate.getRootCertificate(), SecomConstants.CERTIFICATE_THUMBPRINT_HASH));
            this.getEnvelope().setEnvelopeSignatureTime(LocalDateTime.now());
        } catch (CertificateEncodingException | NoSuchAlgorithmException exception) {
            throw new SecomInvalidCertificateException(exception.getMessage());
        }

        // Sign the envelope context as well if possible
        if(this.getEnvelope() instanceof DigitalSignatureBearer) {
            ((DigitalSignatureBearer)this.getEnvelope()).signData(signatureProvider);
        }

        // Get the envelope as a CSV string to be signed
        final byte[] payload = Optional.of(this)
                .map(EnvelopeSignatureBearer::getEnvelope)
                .map(AbstractEnvelope::getCsvString)
                .map(String::getBytes)
                .orElse(new byte[]{});

        // And update the metadata
        this.setEnvelopeSignature(signatureProvider.generateSignature(DigitalSignatureAlgorithmEnum.DSA.getValue(), payload));
    }

}
