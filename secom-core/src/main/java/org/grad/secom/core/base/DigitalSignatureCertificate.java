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

package org.grad.secom.core.base;

import java.security.cert.X509Certificate;

/**
 * The SECOM Digital Signature Certificate Class.
 *
 * This is class is not specified by the SECOM standard, but it is used by
 * the signature provider and validator objects to allow access to the
 * certificate to be used for generating and validating the signatures.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class DigitalSignatureCertificate {

    // Class Variables
    private X509Certificate certificate;
    private String publicKey;
    private X509Certificate rootCertificate;

    /**
     * Gets certificate.
     *
     * @return the certificate
     */
    public X509Certificate getCertificate() {
        return certificate;
    }

    /**
     * Sets certificate.
     *
     * @param certificate the certificate
     */
    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

    /**
     * Gets public key.
     *
     * @return the public key
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Sets public key.
     *
     * @param publicKey the public key
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * Gets root certificate.
     *
     * @return the root certificate
     */
    public X509Certificate getRootCertificate() {
        return rootCertificate;
    }

    /**
     * Sets root certificate.
     *
     * @param rootCertificate the root certificate
     */
    public void setRootCertificate(X509Certificate rootCertificate) {
        this.rootCertificate = rootCertificate;
    }
}
