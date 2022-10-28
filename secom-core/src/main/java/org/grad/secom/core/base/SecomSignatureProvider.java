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

import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;

/**
 * The SECOM Signature Provider Interface.
 *
 * This interface dictates the implementation of the SECOM signature provider.
 * This is required for the SECOM library to be able to automatically generate
 * signature to be applied to the appropriate messages (e.g. get objects).
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface SecomSignatureProvider {

    /**
     * Returns the digital signature algorithm for the signature provider.
     * In SECOM, by default this should be DSA, also ECDSA could be used
     * to generate smaller signatures.
     *
     * @return the digital signature algorithm for the signature provider
     */
    default DigitalSignatureAlgorithmEnum getSignatureAlgorithm() {
        return DigitalSignatureAlgorithmEnum.DSA;
    }

    /**
     * The signature generation operation. It simply required the payload that
     * will be used to generate the signature, which will be returned as a
     * String.
     *
     * @param signatureCertificate  The digital signature certificate to be used for the signature generation
     * @param algorithm             The algorithm to be used for the signature generation
     * @param payload               The payload to be signed, (preferably Base64 encoded)
     * @return The signature generated
     */
    byte[] generateSignature(DigitalSignatureCertificate signatureCertificate, DigitalSignatureAlgorithmEnum algorithm, String payload);

    /**
     * The signature validation operation. This should support the provision
     * of the message content (preferably in a Base64 format, and the signature
     * to validate the content against.
     *
     * @param signatureCertificate  The digital signature certificate to be used for the signature generation
     * @param algorithm             The algorithm used for the signature generation
     * @param signature             The signature to validate the content against
     * @param content               The content to be validated
     * @return whether the signature validation was successful or not
     */
    boolean validateSignature(String signatureCertificate, DigitalSignatureAlgorithmEnum algorithm, byte[] signature, byte[] content);

}
