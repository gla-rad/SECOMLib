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
 * The SECOM Certificate Provider Interface.
 *
 * This interface dictates the implementation of the SECOM certificate provider.
 * This is required for the SECOM library to be able to automatically pick up
 * the certificates used for signing and verifying the messages sent and
 * received respectively.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface SecomCertificateProvider {

    /**
     * Returns a list of trusted certificates for the signature validation.
     * This is only required for SECOM consumers so the default operation does
     * not return any certificates.
     *
     * @return the list of trusted certificates for SECOM
     */
    default X509Certificate[] getTrustedCertificates() {
        return new X509Certificate[]{};
    }

    /**
     * This function can be overridden by the provider to enable the provision
     * of the certificate to be used for the signing and verification
     * operations.
     *
     * @return the digital signature certificate to be used for signing/verification
     */
    DigitalSignatureCertificate getDigitalSignatureCertificate();

}
