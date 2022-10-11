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

import org.grad.secom.core.models.DigitalSignatureValue;
import org.grad.secom.core.models.SECOM_ExchangeMetadataObject;

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
     * This function can be overridden by the provider to enable the provision
     * of the certificate to be used for the signing operation.
     * @return
     */
    DigitalSignatureCertificate getSignatureCertificate();

    /**
     * The signature generation function. It simply required the payload that
     * will be used to generate the signature, which will be returned as a
     * String.
     *
     * @param algorithm     The algorithm to be used for the signature generation
     * @param payload       The payload to be signed
     * @return The signature generated
     */
    String generateSignature(String algorithm, byte[] payload);

}
