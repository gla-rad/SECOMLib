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

import org.grad.secom.models.enums.EncryptionKeyTypeEnum;

import javax.validation.constraints.NotNull;

/**
 * The SECOM Encryption Key Request Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EncryptionKeyRequest {

    // Class Variables
    @NotNull
    private String encryptionKey;
    @NotNull
    private String transactionIdentifier;
    @NotNull
    private DigitalSignatureValue digitalSignatureValue;
    @NotNull
    private EncryptionKeyTypeEnum encryptionKeyTypeEnum;

    /**
     * Gets encryption key.
     *
     * @return the encryption key
     */
    public String getEncryptionKey() {
        return encryptionKey;
    }

    /**
     * Sets encryption key.
     *
     * @param encryptionKey the encryption key
     */
    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    /**
     * Gets transaction identifier.
     *
     * @return the transaction identifier
     */
    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    /**
     * Sets transaction identifier.
     *
     * @param transactionIdentifier the transaction identifier
     */
    public void setTransactionIdentifier(String transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }

    /**
     * Gets digital signature value.
     *
     * @return the digital signature value
     */
    public DigitalSignatureValue getDigitalSignatureValue() {
        return digitalSignatureValue;
    }

    /**
     * Sets digital signature value.
     *
     * @param digitalSignatureValue the digital signature value
     */
    public void setDigitalSignatureValue(DigitalSignatureValue digitalSignatureValue) {
        this.digitalSignatureValue = digitalSignatureValue;
    }

    /**
     * Gets encryption key type enum.
     *
     * @return the encryption key type enum
     */
    public EncryptionKeyTypeEnum getEncryptionKeyTypeEnum() {
        return encryptionKeyTypeEnum;
    }

    /**
     * Sets encryption key type enum.
     *
     * @param encryptionKeyTypeEnum the encryption key type enum
     */
    public void setEncryptionKeyTypeEnum(EncryptionKeyTypeEnum encryptionKeyTypeEnum) {
        this.encryptionKeyTypeEnum = encryptionKeyTypeEnum;
    }
}
