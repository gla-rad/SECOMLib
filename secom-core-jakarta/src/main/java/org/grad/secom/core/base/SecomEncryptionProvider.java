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

package org.grad.secom.core.base;

import org.grad.secom.core.models.enums.EncryptionAlgorithmEnum;

/**
 * The SECOM Encryption Provider Interface.
 *
 * This interface dictates the implementation of the SECOM encryption provider.
 * This is required for the SECOM library to be able to automatically encrypt
 * the payloads of the appropriate messages (e.g. get objects).
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface SecomEncryptionProvider {

    /**
     * Returns the encryption algorithm for the encryption provider.
     * In SECOM, by default this should be AES with cipher block chaining
     * (CBC) and PKCS#7 padding method, as specified in section 7.4.2 of
     * the documentation.
     *
     * @return the encryption algorithm for the encryption provider
     */
    default EncryptionAlgorithmEnum getEncryptionAlgorithm() {
        return EncryptionAlgorithmEnum.AES_CBC_PKCS7;
    }

    /**
     * Returns the encryption algorithm for the encryption provider.
     * In SECOM, by default this should be AES with cipher block chaining
     * (CBC) and PKCS#7 padding method, as specified in section 7.4.2 of
     * the documentation.
     *
     * @return the encryption algorithm for the encryption provider
     */
    String getEncryptionKey();

    /**
     * The encryption function. It simply requires the encryption algorithm and
     * the payload that will be encrypted. The final result will be returned as
     * a byte array.
     *
     * @param encryptionAlgorithm   The algorithm to be used for the encryption operation
     * @param encryptionKey         The key to be used for the encryption process
     * @param payload               The payload to be encrypted
     * @return The encrypted data in a Base64 string
     */
    byte[] encrypt(EncryptionAlgorithmEnum encryptionAlgorithm, String encryptionKey,  byte[] payload);

    /**
     * The decryption operation. This should be provided the encryption
     * algorithm used as well as the data to be decrypted. The final result
     * will be returned as a byte array.
     *
     * @param encryptionAlgorithm   The algorithm used for the decryption
     * @param encryptionKey         The key to be used for the decryption process
     * @param data                  The encrypted data
     * @return the decryption result
     */
    byte[] decrypt(EncryptionAlgorithmEnum encryptionAlgorithm, String encryptionKey, byte[] data);

}
