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

package org.grad.secom.core.models.enums;

import java.util.Arrays;

/**
 * The Encryption Algorithm Enum.
 *
 * This enumeration describes the algorithms supported for encrypting SECOM
 * payloads.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public enum EncryptionAlgorithmEnum implements SECOM_Enum {
    AES_CBC_PKCS7("AES", "AES/CBC/PKCS7Padding");

    // Enum Variables
    private final String algorithm;
    private final String cipher;

    /**
     * Enum Constructor.
     *
     * @param algorithm the encryption algorithm
     * @param cipher    the Java encryption cipher
     */
    EncryptionAlgorithmEnum(String algorithm, String cipher) {
        this.algorithm = algorithm;
        this.cipher = cipher;
    }

    /**
     * Gets algorithm.
     *
     * @return the algorithm
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * Gets cipher.
     *
     * @return the cipher
     */
    public String getCipher() {
        return cipher;
    }


    /**
     * The conversion to a string operation.
     *
     * @return the SECOM string representation of the enum
     */
    @Override
    public String asString() {
        return this.name().toLowerCase();
    }
}
