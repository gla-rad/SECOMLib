/*
 * Copyright (c) 2026 GLA Research and Development Directorate
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.grad.secomv2.core.base.EnvelopeSignatureBearer;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

/**
 * The SECOM Public Key Filter Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class PublicKeyFilterObject implements EnvelopeSignatureBearer {

    // Class Variables
    @NotNull
    private EnvelopePublicKeyFilterObject envelope;
    @NotNull
    @Schema(description = "The signature of the EnvelopePublicKeyFilterObject in HEX format without whitespace or linebreaks")
    private String envelopeSignature;

    /**
     * Gets envelope.
     *
     * @return the envelope
     */
    @Override
    public EnvelopePublicKeyFilterObject getEnvelope() {
        return envelope;
    }

    /**
     * Sets envelope.
     *
     * @param envelope the envelope
     */
    public void setEnvelope(EnvelopePublicKeyFilterObject envelope) {
        this.envelope = envelope;
    }

    /**
     * Gets envelope signature.
     *
     * @return the envelope signature
     */
    @Override
    public String getEnvelopeSignature() {
        return envelopeSignature;
    }

    /**
     * Sets envelope signature.
     *
     * @param envelopeSignature the envelope signature
     */
    @Override
    public void setEnvelopeSignature(String envelopeSignature) {
        this.envelopeSignature = envelopeSignature;
    }

    /**
     * According to the SECOM standard, the filter shall include either the
     * dataProtection attribute or the certificateThumbprint attribute, but
     * not both.
     *
     * @return whether the filter attribute combination is valid
     */
    @JsonIgnore
    @AssertTrue(message = "either dataProtection or certificateThumbprint must be provided, but not both")
    public boolean isFilterValid() {
        return envelope == null
                || (envelope.getDataProtection() == null) != (envelope.getCertificateThumbprint() == null);
    }
}
