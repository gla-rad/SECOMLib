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

package org.grad.secom.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.grad.secom.core.base.InstantDeserializer;
import org.grad.secom.core.base.InstantSerializer;
import org.grad.secom.core.models.enums.AckTypeEnum;
import org.grad.secom.core.models.enums.NackTypeEnum;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

/**
 * The SECOM Envelope Ack Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeAckObject extends AbstractEnvelope {

    // Class Variables
    @NotNull
    @Schema(description = "The creation date-time", type = "string",example = "19850412T101530", pattern = "(\\d{8})T(\\d{6})(Z|\\+\\d{4})?")
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant createdAt;
    @NotNull
    private UUID transactionIdentifier;
    @NotNull
    private AckTypeEnum ackType;
    private NackTypeEnum nackType;

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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
     * Gets envelope certificate.
     *
     * @return the envelope certificate
     */
    public String getEnvelopeCertificate() {
        return envelopeSignatureCertificate;
    }

    /**
     * Gets envelope signature certificate.
     * <p/>
     * NOTE: For some reason this field here should be titles (according to
     * SECOM) envelopeCertificate.
     *
     * @return the envelope signature certificate
     */
    @JsonProperty("envelopeCertificate")
    @Override
    public String getEnvelopeSignatureCertificate() {
        return envelopeSignatureCertificate;
    }

    /**
     * This method should be implemented by all envelop objects to allow the
     * generation of the signature CSV attribute array
     *
     * @return the generated signature CSV attribute array
     */
    @Override
    public Object[] getAttributeArray() {
        return new Object[] {
                createdAt,
                envelopeSignatureCertificate,
                envelopeRootCertificateThumbprint,
                transactionIdentifier,
                ackType,
                nackType,
                envelopeSignatureTime
        };
    }
}
