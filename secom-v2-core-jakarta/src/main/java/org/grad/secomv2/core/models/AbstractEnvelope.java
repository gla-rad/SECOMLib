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

package org.grad.secomv2.core.models;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.grad.secomv2.core.base.CsvStringGenerator;

import jakarta.validation.constraints.NotNull;
import org.grad.secomv2.core.base.SecomInstantDeserializer;
import org.grad.secomv2.core.base.SecomInstantSerializer;

import java.time.Instant;

public abstract class AbstractEnvelope implements CsvStringGenerator {

    // Class Variables
    @NotNull
    @Schema(description = "The public certificate (chain) of the sender, used to verify the EnvelopeKeyObject signature")
    protected String[] envelopeSignatureCertificate;
    @NotNull
    @Schema(description = "Claimed Thumbprint for Signed Root Key (X.509 Certificate) Format: SHA-1 or SHA-256 thumbprint.", example = "AB12CD34EF56AB78CD90EF12AB34CD56EF78AB90")
    @Pattern(regexp = "^[A-Fa-f0-9]{40,64}$")
    @Size(min = 1)
    protected String envelopeRootCertificateThumbprint;
    @NotNull
    @Schema(description = "Time when encryptionKey envelope is signed Must be in UTC format: yyyy-MM-ddTHH:mm:ssZ.", type = "string",example = "1985-04-12T10:15:30Z", pattern =  "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(Z|\\+\\d{4})?")
    @JsonSerialize(using = SecomInstantSerializer.class)
    @JsonDeserialize(using = SecomInstantDeserializer.class)
    protected Instant envelopeSignatureTime;
    @NotNull
    @Schema(type = "string", description = "(S-100) Specifies the algorithm used to compute envelopeSignature\\r\\nFor example \\\"ECDSA-384-SHA2\\\"")
    @Size(min = 1)
    protected String digitalSignatureReference;

    /**
     * Gets envelope signature certificate.
     *
     * @return the envelope signature certificate
     */
    public String[] getEnvelopeSignatureCertificate() {
        return envelopeSignatureCertificate;
    }

    /**
     * Sets envelope signature certificate.
     *
     * @param envelopeSignatureCertificate the envelope signature certificate
     */
    public void setEnvelopeSignatureCertificate(String[] envelopeSignatureCertificate) {
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

    /**
     * Gets digital signature reference.
     *
     * @return the digital signature reference
     */
    public String getDigitalSignatureReference() {
        return digitalSignatureReference;
    }

    /**
     * Sets digital signature reference.
     *
     * @param digitalSignatureReference the digital signature reference
     */
    public void setDigitalSignatureReference(String digitalSignatureReference) {
        this.digitalSignatureReference = digitalSignatureReference;
    }
}
