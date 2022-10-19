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

import org.grad.secom.core.base.EnvelopeSignatureBearer;

import javax.validation.constraints.NotNull;

/**
 * The SECOM Encryption Key Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EncryptionKeyObject implements EnvelopeSignatureBearer {

    // Class Variables
    @NotNull
    private EnvelopeKeyObject envelope;
    @NotNull
    private String envelopeSignature;

    /**
     * Gets envelope.
     *
     * @return the envelope
     */
    @Override
    public EnvelopeKeyObject getEnvelope() {
        return envelope;
    }

    /**
     * Sets envelope.
     *
     * @param envelope the envelope
     */
    public void setEnvelope(EnvelopeKeyObject envelope) {
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
}
