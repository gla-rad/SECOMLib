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

import org.grad.secom.core.models.enums.AckTypeEnum;
import org.grad.secom.core.models.enums.NackTypeEnum;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The SECOM Envelope Ack Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeAckObj {

    // Class Variables
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private String envelopeCertificate;
    @NotNull
    private String envelopeRootCertificateThumbprint;
    @NotNull
    private UUID transactionIdentifier;
    @NotNull
    private AckTypeEnum ackType;
    private NackTypeEnum nackType;
    @NotNull
    private LocalDateTime envelopeSignatureTime;

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets envelope certificate.
     *
     * @return the envelope certificate
     */
    public String getEnvelopeCertificate() {
        return envelopeCertificate;
    }

    /**
     * Sets envelope certificate.
     *
     * @param envelopeCertificate the envelope certificate
     */
    public void setEnvelopeCertificate(String envelopeCertificate) {
        this.envelopeCertificate = envelopeCertificate;
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
     * Gets ack type.
     *
     * @return the ack type
     */
    public AckTypeEnum getAckType() {
        return ackType;
    }

    /**
     * Sets ack type.
     *
     * @param ackType the ack type
     */
    public void setAckType(AckTypeEnum ackType) {
        this.ackType = ackType;
    }

    /**
     * Gets nack type.
     *
     * @return the nack type
     */
    public NackTypeEnum getNackType() {
        return nackType;
    }

    /**
     * Sets nack type.
     *
     * @param nackType the nack type
     */
    public void setNackType(NackTypeEnum nackType) {
        this.nackType = nackType;
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
