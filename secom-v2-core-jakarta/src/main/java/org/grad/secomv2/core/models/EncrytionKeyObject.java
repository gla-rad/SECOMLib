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

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * The SECOM Encryption Key Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EncrytionKeyObject {

    // Class Variables
    @NotNull
    private EnvelopeKeyObject envelopeKeyObject;
    @NotNull
    @Schema(description = "The signature of the EnvelopeKeyObject in HEX format without whitespace or linebreaks")
    @Size(min = 1)
    private String envelopeSignature;

    /**
     * Gets envelope key object.
     *
     * @return the envelope key object
     */
    public EnvelopeKeyObject getEnvelopeKeyObject() {
        return envelopeKeyObject;
    }

    /**
     * Sets envelope key object.
     *
     * @param envelopeKeyObject the envelope key object
     */
    public void setEnvelopeKeyObject(EnvelopeKeyObject envelopeKeyObject) {
        this.envelopeKeyObject = envelopeKeyObject;
    }

    /**
     * Gets envelope signature.
     *
     * @return the envelope signature
     */
    public String getEnvelopeSignature() {
        return envelopeSignature;
    }

    /**
     * Sets envelope signature.
     *
     * @param envelopeSignature the envelope signature
     */
    public void setEnvelopeSignature(String envelopeSignature) {
        this.envelopeSignature = envelopeSignature;
    }
}
