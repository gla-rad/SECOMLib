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

package org.grad.secom.core.models;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * The SECOM Envelope Key Object.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeKeyObject extends AbstractEnvelope {

    // Class Variables
    @NotNull
    private String encryptionKey;
    @NotNull
    private String iv;
    @NotNull
    private UUID transactionIdentifier;
    @NotNull
    private DigitalSignatureValueObject digitalSignatureValueObject;

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
     * Gets iv.
     *
     * @return the iv
     */
    public String getIv() {
        return iv;
    }

    /**
     * Sets iv.
     *
     * @param iv the iv
     */
    public void setIv(String iv) {
        this.iv = iv;
    }

    /**
     * Gets transaction identifier.
     *
     * @return the transaction identifier
     */
    public UUID getTransactionIdentifier() {
        return transactionIdentifier;
    }

    /**
     * Sets transaction identifier.
     *
     * @param transactionIdentifier the transaction identifier
     */
    public void setTransactionIdentifier(UUID transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }

    /**
     * Gets digital signature value.
     *
     * @return the digital signature value
     */
    public DigitalSignatureValueObject getDigitalSignatureValue() {
        return digitalSignatureValueObject;
    }

    /**
     * Sets digital signature value.
     *
     * @param digitalSignatureValueObject the digital signature value
     */
    public void setDigitalSignatureValue(DigitalSignatureValueObject digitalSignatureValueObject) {
        this.digitalSignatureValueObject = digitalSignatureValueObject;
    }

    /**
     * This method should be implemented by all envelop objects to allow the
     * generation of the signature CSV attribute array
     *
     * @return the generated signature CSV attribute array
     */
    @Override
    public Object[] getAttributeArray() {
        return new Object[] {
                encryptionKey,
                iv,
                transactionIdentifier,
                digitalSignatureValueObject,
                envelopeSignatureCertificate,
                envelopeRootCertificateThumbprint,
                envelopeSignatureTime
        };
    }
}
