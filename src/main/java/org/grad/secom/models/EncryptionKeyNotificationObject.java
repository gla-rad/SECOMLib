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

package org.grad.secom.models;

import javax.validation.constraints.NotNull;

/**
 * The SECOM Encryption Key Notification Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EncryptionKeyNotificationObject {

    // Class Variables
    @NotNull
    private EnvelopeKeyNotificationObject envelope;
    @NotNull
    private String envelopeSignature;

    /**
     * Gets envelope.
     *
     * @return the envelope
     */
    public EnvelopeKeyNotificationObject getEnvelope() {
        return envelope;
    }

    /**
     * Sets envelope.
     *
     * @param envelope the envelope
     */
    public void setEnvelope(EnvelopeKeyNotificationObject envelope) {
        this.envelope = envelope;
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
