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

/**
 * The SECOM Digital Signature Value Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class DigitalSignatureValue {

    // Class Variables
    private String publicRootCertificateThumbprint;
    private byte[] publicCertificate;
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
     * Get public certificate byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getPublicCertificate() {
        return publicCertificate;
    }

    /**
     * Sets public certificate.
     *
     * @param publicCertificate the public certificate
     */
    public void setPublicCertificate(byte[] publicCertificate) {
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
}
