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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.grad.secom.core.base.CsvStringGenerator;

import jakarta.validation.constraints.NotNull;
import org.grad.secom.core.base.InstantDeserializer;
import org.grad.secom.core.base.InstantSerializer;

import java.time.Instant;

public abstract class AbstractEnvelope implements CsvStringGenerator {

    // Class Variables
    @NotNull
    protected String envelopeSignatureCertificate;
    @NotNull
    protected String envelopeRootCertificateThumbprint;
    @NotNull
    @Schema(description = "The envelope signature date-time", type = "string", example = "19850412T101530", pattern = "(\\d{8})T(\\d{6})(Z|\\+\\d{4})?")
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    protected Instant envelopeSignatureTime;

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
    public Instant getEnvelopeSignatureTime() {
        return envelopeSignatureTime;
    }

    /**
     * Sets envelope signature time.
     *
     * @param envelopeSignatureTime the envelope signature time
     */
    public void setEnvelopeSignatureTime(Instant envelopeSignatureTime) {
        this.envelopeSignatureTime = envelopeSignatureTime;
    }

}
