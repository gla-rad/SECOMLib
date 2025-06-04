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

package org.grad.secom.v2.core.base;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Base64;

/**
 * The SECOM Generic Data Bearer Interface.
 *
 * This class can be used to identify all SECOM objects that bear a data
 * payload.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface GenericDataBearer {

    /**
     * This function allows access to the data payload of the data bearer.
     *
     * @return the data payload of the data bearer
     */
    @JsonIgnore
    byte[] getData();

    /**
     * This function allows updating the data payload of the data bearer.
     *
     * @param data the data payload of the data bearer
     */
    @JsonIgnore
    void setData( byte[] data);

    /**
     * A helper function that automatically encodes the provided data into
     * a Base64 string.
     *
     * @return the updated generic data bearer
     */
    @JsonIgnore
    default GenericDataBearer encodeData() {
        // Sanity Check
        if(this.getData() == null) {
            return this;
        }

        // Encode the data
        final byte[] encodedData = Base64.getEncoder().encode(this.getData());
        this.setData(encodedData);

        // Return the same object for further processing
        return this;
    }

    /**
     * A helper function that automatically decodes the Base64 data into the
     * required string.
     *
     * @return the updated generic data bearer
     */
    @JsonIgnore
    default GenericDataBearer decodeData() {
        // Sanity Check
        if(this.getData() == null) {
            return this;
        }

        // Decode the data
        final byte[] decodedData = Base64.getDecoder().decode(this.getData());
        this.setData(decodedData);

        // Return the same object for further processing
        return this;
    }

    /**
     * A helper function that automatically encrypts the data bearers data
     * payload using the SECOM encryption provider.
     *
     * @param encryptionProvider    The SECOM encryption provider
     * @return the updated generic data bearer
     */
    default GenericDataBearer encryptData(SecomEncryptionProvider encryptionProvider) {
        // Sanity Check
        if(encryptionProvider == null) {
            return this;
        }

        // Encrypt the data
        final byte[] encryptedData = encryptionProvider.encrypt(
                encryptionProvider.getEncryptionAlgorithm(),
                encryptionProvider.getEncryptionKey(),
                this.getData()
        );
        this.setData(encryptedData);

        // Return the same object for further processing
        return this;
    }

    /**
     * A helper function that automatically dencrypts the data bearers data
     * payload using the SECOM encryption provider.
     *
     * @param encryptionProvider    The SECOM encryption provider
     * @return the updated generic data bearer
     */
    default GenericDataBearer decryptData(SecomEncryptionProvider encryptionProvider) {
        // Sanity Check
        if(encryptionProvider == null) {
            return this;
        }

        // Decrypt the data
        final byte[] decryptedData = encryptionProvider.decrypt(
                encryptionProvider.getEncryptionAlgorithm(),
                encryptionProvider.getEncryptionKey(),
                this.getData()
        );
        this.setData(decryptedData);

        // Return the same object for further processing
        return this;
    }

    /**
     * A helper function that automatically compresses the data bearers data
     * payload using the SECOM compression provider.
     *
     * @param compressionProvider   The SECOM compression provider
     * @return the updated generic data bearer
     */
    default GenericDataBearer compressData(SecomCompressionProvider compressionProvider) {
        // Sanity Check
        if(compressionProvider == null) {
            return this;
        }

        // Compress the data
        final byte[] compressedData = compressionProvider.compress(
                compressionProvider.getCompressionAlgorithm(),
                this.getData()
        );
        this.setData(compressedData);

        // Return the same object for further processing
        return this;
    }

    /**
     * A helper function that automatically decompresses the data bearers data
     * payload using the SECOM compression provider.
     *
     * @param compressionProvider   The SECOM compression provider
     * @return the updated generic data bearer
     */
    default GenericDataBearer decompressData(SecomCompressionProvider compressionProvider) {
        // Sanity Check
        if(compressionProvider == null) {
            return this;
        }

        // Decompress the data
        final byte[] decompressedData = compressionProvider.decompress(
                compressionProvider.getCompressionAlgorithm(),
                this.getData()
        );
        this.setData(decompressedData);

        // Return the same object for further processing
        return this;
    }

}
