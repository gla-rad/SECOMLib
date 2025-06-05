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

import org.grad.secom.core.base.CsvStringGenerator;

import jakarta.validation.constraints.NotNull;

/**
 * The SECOM Digital Signature Value Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class DigitalSignatureValue implements CsvStringGenerator {

    // Class Variables
    private String publicRootCertificateThumbprint;
    @NotNull
    private String publicCertificate;
    @NotNull
    private String digitalSignature;

    /**
     * Gets public root certificate thumbprint.
     *
     * @return the public root certificate thumbprint
     */
    public String getPublicRootCertificateThumbprint() {
        return publicRootCertificateThumbprint;
    }

    /**
     * Sets public root certificate thumbprint.
     *
     * @param publicRootCertificateThumbprint the public root certificate thumbprint
     */
    public void setPublicRootCertificateThumbprint(String publicRootCertificateThumbprint) {
        this.publicRootCertificateThumbprint = publicRootCertificateThumbprint;
    }

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
     * Gets digital signature.
     *
     * @return the digital signature
     */
    public String getDigitalSignature() {
        return digitalSignature;
    }

    /**
     * Sets digital signature.
     *
     * @param digitalSignature the digital signature
     */
    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }

    /**
     * This method should be implemented by all envelop objects to allow the
     * generation of the signature CSV attribute array
     *
     * @return the generated signature CSV attribute array
     */
    @Override
    public Object[] getAttributeArray() {
        // Create the CSV array
        return new Object[] {
                publicRootCertificateThumbprint,
                publicCertificate,
                digitalSignature
        };
    }
}
