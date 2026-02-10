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

package org.grad.secomv2.core.models;

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.grad.secomv2.core.base.SecomByteArrayDeSerializer;
import org.grad.secomv2.core.base.SecomByteArraySerializer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

/**
 * The SECOM Envelope Key Object.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeKeyObject extends AbstractEnvelope {

    // Class Variables
    @NotNull
    @Schema(type = "string", format = "byte", description = "The protected symmetric encryption key, Base64 encoded\r\nExample : KQdEi+9iUlq8B9cwWY...U8A2iDPhz7g==")
    @JsonSerialize(using = SecomByteArraySerializer.class)
    @JsonDeserialize(using = SecomByteArrayDeSerializer.class)
    private byte[] encryptionKey;
    @NotNull
    @Schema(type = "string", format = "byte", description = "Inititalisation vector, Base64 encoded\r\nExample: c9fUXeC5xrFuXGNNnGv9iA==")
    @JsonSerialize(using = SecomByteArraySerializer.class)
    @JsonDeserialize(using = SecomByteArrayDeSerializer.class)
    private byte[] iv;
    @NotNull
    @Schema(type = "string", format = "uuid", description = "Identifier to the transaction with the encrypted data", example = "550e8400-e29b-41d4-a716-446655440000")
    @Pattern(regexp = "^[{(]?[0-9a-fA-F]{8}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{12}[)}]?$")
    private UUID transactionIdentifier;
    @NotNull
    private DigitalSignatureValueObject digitalSignatureValue;

    /**
     * Gets encryption key.
     *
     * @return the encryption key
     */
    public byte[] getEncryptionKey() {
        return encryptionKey;
    }

    /**
     * Sets encryption key.
     *
     * @param encryptionKey the encryption key
     */
    public void setEncryptionKey(byte[] encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    /**
     * Gets iv.
     *
     * @return the iv
     */
    public byte[] getIv() {
        return iv;
    }

    /**
     * Sets iv.
     *
     * @param iv the iv
     */
    public void setIv(byte[] iv) {
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
        return digitalSignatureValue;
    }

    /**
     * Sets digital signature value.
     *
     * @param digitalSignatureValueObject the digital signature value
     */
    public void setDigitalSignatureValue(DigitalSignatureValueObject digitalSignatureValueObject) {
        this.digitalSignatureValue = digitalSignatureValueObject;
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
                digitalSignatureValue,
                envelopeSignatureCertificate,
                envelopeRootCertificateThumbprint,
                envelopeSignatureTime,
                digitalSignatureReference
        };
    }
}
