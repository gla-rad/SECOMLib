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
 * The SECOM Acknowledgement Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class AcknowledgementRequest {

    // Class Variables
    @NotNull
    private AcknowledgementData data;
    @NotNull
    private DigitalSignatureValue digitalSignature;

    /**
     * Gets data.
     *
     * @return the data
     */
    public AcknowledgementData getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(AcknowledgementData data) {
        this.data = data;
    }

    /**
     * Gets digital signature.
     *
     * @return the digital signature
     */
    public DigitalSignatureValue getDigitalSignature() {
        return digitalSignature;
    }

    /**
     * Sets digital signature.
     *
     * @param digitalSignature the digital signature
     */
    public void setDigitalSignature(DigitalSignatureValue digitalSignature) {
        this.digitalSignature = digitalSignature;
    }
}
