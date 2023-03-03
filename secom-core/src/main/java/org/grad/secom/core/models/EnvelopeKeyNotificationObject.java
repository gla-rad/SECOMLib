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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.grad.secom.core.base.DateTimeDeSerializer;
import org.grad.secom.core.base.DateTimeSerializer;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The SECOM Envelope Key Notification Object.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeKeyNotificationObject {

    // Class Variables
    @NotNull
    private UUID dataReference;
    @NotNull
    private String publicCertificate;
    @NotNull
    private String envelopeSignatureCertificate;
    @NotNull
    @Schema(description = "The envelope signature date-time", type = "string",example = "19850412T101530")
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeSerializer.class)
    private LocalDateTime envelopeSignatureTime;

    /**
     * Gets data reference.
     *
     * @return the data reference
     */
    public UUID getDataReference() {
        return dataReference;
    }

    /**
     * Sets data reference.
     *
     * @param dataReference the data reference
     */
    public void setDataReference(UUID dataReference) {
        this.dataReference = dataReference;
    }

    /**
     * Gets public certificate.
     *
     * @return the public certificate
     */
    public String getPublicCertificate() {
        return publicCertificate;
    }

    /**
     * Sets public certificate.
     *
     * @param publicCertificate the public certificate
     */
    public void setPublicCertificate(String publicCertificate) {
        this.publicCertificate = publicCertificate;
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
