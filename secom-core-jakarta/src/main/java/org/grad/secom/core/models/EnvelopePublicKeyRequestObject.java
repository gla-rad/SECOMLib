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

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * The SECOM Envelope Public Key Request Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopePublicKeyRequestObject extends AbstractEnvelope {

    // Class Variables
    @NotNull
    @Schema(description = "(S100) Public Key for claimed identity")
    private String publicCertificate;

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
     * This method should be implemented by all envelop objects to allow the
     * generation of the signature CSV attribute array
     *
     * @return the generated signature CSV attribute array
     */
    @Override
    public Object[] getAttributeArray() {
        return new Object[] {
                publicCertificate,
                digitalSignatureReference,
                envelopeSignatureCertificate,
                envelopeRootCertificateThumbprint,
                envelopeSignatureTime
        };
    }
}
