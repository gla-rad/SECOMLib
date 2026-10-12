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

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Pattern;

/**
 * The SECOM Envelope Public Key Filter Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopePublicKeyFilterObject extends AbstractEnvelope {

    // Class Variables
    @Schema(description = "Flag indicating that this is a request for the certificate needed for encryption key exchange")
    private Boolean dataProtection;
    @Schema(description = "Claimed Thumbprint for signed key (X.509 Certificate) SHA-384 and HEX as format")
    @Pattern(regexp = "^[A-Fa-f0-9]{40,96}$")
    private String certificateThumbprint;

    /**
     * Gets data protection.
     *
     * @return the data protection
     */
    public Boolean getDataProtection() {
        return dataProtection;
    }

    /**
     * Sets data protection.
     *
     * @param dataProtection the data protection
     */
    public void setDataProtection(Boolean dataProtection) {
        this.dataProtection = dataProtection;
    }

    /**
     * Gets certificate thumbprint.
     *
     * @return the certificate thumbprint
     */
    public String getCertificateThumbprint() {
        return certificateThumbprint;
    }

    /**
     * Sets certificate thumbprint.
     *
     * @param certificateThumbprint the certificate thumbprint
     */
    public void setCertificateThumbprint(String certificateThumbprint) {
        this.certificateThumbprint = certificateThumbprint;
    }

    /**
     * This method should be implemented by all envelop objects to allow the
     * generation of the signature CSV attribute array
     *
     * @return the generated signature CSV attribute array
     */
    @Override
    public Object[] getAttributeArray() {
        return new Object[]{
                dataProtection,
                certificateThumbprint,
                envelopeSignatureCertificate,
                envelopeRootCertificateThumbprint,
                envelopeSignatureTime
        };
    }
}
