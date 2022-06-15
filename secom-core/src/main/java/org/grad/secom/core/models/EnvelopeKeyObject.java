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

package org.grad.secom.core.models;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The SECOM Envelope Key Object.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeKeyObject {

    // Class Variables
    @NotNull
    private String encryptionKey;
    @NotNull
    private String iv;
    @NotNull
    private UUID transactionIdentifier;
    @NotNull
    private DigitalSignatureValue digitalSignatureValue;
    @NotNull
    private String envelopeSignatureCertificate;
    @NotNull
    private String envelopeRootCertificateThumbprint;
    @NotNull
    private LocalDateTime envelopeSignatureTime;

    /**
     * Gets encryption key.
     *
     * @return the encryption key
     */
    public String getEncryptionKey() {
        return encryptionKey;
    }

    /**
     * Sets encryption key.
     *
     * @param encryptionKey the encryption key
     */
    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    /**
     * Gets iv.
     *
     * @return the iv
     */
    public String getIv() {
        return iv;
    }

    /**
     * Sets iv.
     *
     * @param iv the iv
     */
    public void setIv(String iv) {
        this.iv = iv;
    }

    /**
     * Gets transaction identifier.
     *
     * @return the transaction identifier
     */
    public UUID getTransactionIdentifier() {
        return transactionIdentifier;
    }

    /**
     * Sets transaction identifier.
     *
     * @param transactionIdentifier the transaction identifier
     */
    public void setTransactionIdentifier(UUID transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }

    /**
     * Gets digital signature value.
     *
     * @return the digital signature value
     */
    public DigitalSignatureValue getDigitalSignatureValue() {
        return digitalSignatureValue;
    }

    /**
     * Sets digital signature value.
     *
     * @param digitalSignatureValue the digital signature value
     */
    public void setDigitalSignatureValue(DigitalSignatureValue digitalSignatureValue) {
        this.digitalSignatureValue = digitalSignatureValue;
    }

    /**
     * Gets envelope signature certificate.
     *
     * @return the envelope signature certificate
     */
    public String getEnvelopeSignatureCertificate() {
        return envelopeSignatureCertificate;
    }

    /**
     * Sets envelope signature certificate.
     *
     * @param envelopeSignatureCertificate the envelope signature certificate
     */
    public void setEnvelopeSignatureCertificate(String envelopeSignatureCertificate) {
        this.envelopeSignatureCertificate = envelopeSignatureCertificate;
    }

    /**
     * Gets envelope root certificate thumbprint.
     *
     * @return the envelope root certificate thumbprint
     */
    public String getEnvelopeRootCertificateThumbprint() {
        return envelopeRootCertificateThumbprint;
    }

    /**
     * Sets envelope root certificate thumbprint.
     *
     * @param envelopeRootCertificateThumbprint the envelope root certificate thumbprint
     */
    public void setEnvelopeRootCertificateThumbprint(String envelopeRootCertificateThumbprint) {
        this.envelopeRootCertificateThumbprint = envelopeRootCertificateThumbprint;
    }

    /**
     * Gets envelope signature time.
     *
     * @return the envelope signature time
     */
    public LocalDateTime getEnvelopeSignatureTime() {
        return envelopeSignatureTime;
    }

    /**
     * Sets envelope signature time.
     *
     * @param envelopeSignatureTime the envelope signature time
     */
    public void setEnvelopeSignatureTime(LocalDateTime envelopeSignatureTime) {
        this.envelopeSignatureTime = envelopeSignatureTime;
    }
}
