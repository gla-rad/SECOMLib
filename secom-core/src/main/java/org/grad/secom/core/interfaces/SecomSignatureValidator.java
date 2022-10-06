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

package org.grad.secom.core.interfaces;

/**
 * The SECOM Signature Validator Interface.
 *
 * This interface dictates the implementation of the SECOM signature validators.
 * This is required for the SECOM library to be able to automatically validate
 * the available message signatures for the appropriate received messages (e.g.
 * the upload and upload link messages).
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface SecomSignatureValidator {

    /**
     * The signature validation operation. This should support the provision
     * of the message content (preferably in a Base64 format) and the signature
     * to validate the content against.
     *
     * @param content       The context (in Base64 format) to be validated
     * @param signature     The signature to validate the context against
     * @return whether the signature validation was successful or not
     */
    boolean validateSignature(String content, String signature);

}
