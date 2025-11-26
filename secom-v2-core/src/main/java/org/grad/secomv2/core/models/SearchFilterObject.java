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


import javax.validation.constraints.NotNull;

/**
 * {Description}
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
public class SearchFilterObject {

    @NotNull
    private EnvelopeSearchFilterObject envelope;
    @NotNull
    private String envelopeSignature;

    /**
     * Get the envelope
     * @return envelope
     */
    public EnvelopeSearchFilterObject getEnvelope() {
        return envelope;
    }

    /**
     * Sets the envelope
     *
     * @param envelope the envelope search filter object
     */
    public void setEnvelope(EnvelopeSearchFilterObject envelope) {
        this.envelope = envelope;
    }

    /**
     * Gets the envelope signature
     *
     * @return envelopeSignature
     */
    public String getEnvelopeSignature() {
        return envelopeSignature;
    }

    /**
     * Sets the envelope signature
     *
     * @param envelopeSignature the envelope signature array
     */
    public void setEnvelopeSignature(String envelopeSignature) {
        this.envelopeSignature = envelopeSignature;
    }

}
