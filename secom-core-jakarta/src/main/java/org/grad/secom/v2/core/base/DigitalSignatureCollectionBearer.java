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

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * The SECOM Digital Signature Bearer Interface.
 *
 * This interface forces the implementing classes to provide a way of signing
 * the contained data using a SECOM signature provider implementation.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface DigitalSignatureCollectionBearer {

    /**
     * Gets digital signature collection.
     *
     * @return the digital signature collection
     */
    @JsonIgnore
    Collection<DigitalSignatureBearer> getDigitalSignatureCollection();

    /**
     * Sets digital signature collection.
     *
     * @param digitalSignatureCollection the digital signature collection
     */
    @JsonIgnore
    void setDigitalSignatureCollection(Collection<DigitalSignatureBearer> digitalSignatureCollection);

    /**
     * This is a helper function to initialise the populate the SECOM exchange
     * metadata object with the appropriate values, depending on the current
     * configuration and available resources.
     *
     * @param signatureProvider     The SECOM signature provider, if it exists
     * @return the updated digital signature collection bearer
     */
    default DigitalSignatureCollectionBearer prepareMetadata(SecomSignatureProvider signatureProvider) {
        Optional.of(this)
                .map(DigitalSignatureCollectionBearer::getDigitalSignatureCollection)
                .orElse(Collections.emptyList())
                .forEach(dsb -> dsb.prepareMetadata(signatureProvider));
        return this;
    }

    /**
     * A helper function that automatically encodes the provided digital
     * signature collection bearer's data into Base64 strings.
     *
     * @return the updated digital signature collection bearer
     */
    default DigitalSignatureCollectionBearer encodeData() {
        Optional.of(this)
                .map(DigitalSignatureCollectionBearer::getDigitalSignatureCollection)
                .orElse(Collections.emptyList())
                .forEach(dsb -> dsb.encodeData());
        return this;
    }

    /**
     * A helper function that automatically decodes the digital signature
     * collection bearer's Base64 data into the required strings.
     *
     * @return the updated digital signature collection bearer
     */
    default DigitalSignatureCollectionBearer decodeData() {
        Optional.of(this)
                .map(DigitalSignatureCollectionBearer::getDigitalSignatureCollection)
                .orElse(Collections.emptyList())
                .forEach(dsb -> dsb.decodeData());
        return this;
    }

    /**
     * A helper function that automatically encrypts the digital signature
     * collection bearer's data payloads using the SECOM encryption provider.
     *
     * @param encryptionProvider    The SECOM encryption provider
     * @return the updated digital signature collection bearer
     */
    default DigitalSignatureCollectionBearer encryptData(SecomEncryptionProvider encryptionProvider) {
        Optional.of(this)
                .map(DigitalSignatureCollectionBearer::getDigitalSignatureCollection)
                .orElse(Collections.emptyList())
                .forEach(dsb -> dsb.encryptData(encryptionProvider));
        return this;
    }

    /**
     * A helper function that automatically decrypts the digital signature
     * collection bearer's data payload using the SECOM encryption provider.
     *
     * @param encryptionProvider    The SECOM encryption provider
     * @return the updated digital signature collection bearer
     */
    default DigitalSignatureCollectionBearer decryptData(SecomEncryptionProvider encryptionProvider) {
        Optional.of(this)
                .map(DigitalSignatureCollectionBearer::getDigitalSignatureCollection)
                .orElse(Collections.emptyList())
                .forEach(dsb -> dsb.decryptData(encryptionProvider));
        return this;
    }

    /**
     * A helper function that automatically compresses the digital signature
     * collection bearer's data payload using the SECOM compression provider.
     *
     * @param compressionProvider   The SECOM compression provider
     * @return the updated digital signature collection bearer
     */
    default DigitalSignatureCollectionBearer compressData(SecomCompressionProvider compressionProvider) {
        Optional.of(this)
                .map(DigitalSignatureCollectionBearer::getDigitalSignatureCollection)
                .orElse(Collections.emptyList())
                .forEach(dsb -> dsb.compressData(compressionProvider));
        return this;
    }

    /**
     * A helper function that automatically decompresses the digital signature
     * collection bearer's data payload using the SECOM compression provider.
     *
     * @param compressionProvider   The SECOM compression provider
     * @return the updated digital signature collection bearer
     */
    default DigitalSignatureCollectionBearer decompressData(SecomCompressionProvider compressionProvider) {
        Optional.of(this)
                .map(DigitalSignatureCollectionBearer::getDigitalSignatureCollection)
                .orElse(Collections.emptyList())
                .forEach(dsb -> dsb.decompressData(compressionProvider));
        return this;
    }

    /**
     * This function performs the actual signing operation of the digital
     * signature collection bearer. In this, the data of each digital signature
     * bearer in the collection to be signed is accessed and translated onto
     * a byte array. Then the provided SECOM signature provider will be used
     * to perform the signing wich will be used to generate and attach the
     * SECOM exchange metadata.
     *
     * @param certificateProvider   The SECOM certificate provider to be used
     * @param signatureProvider     The SECOM signature provider to be used.
     * @return the updated digital signature collection bearer
     */
    @JsonIgnore
    default DigitalSignatureCollectionBearer signData(SecomCertificateProvider certificateProvider, SecomSignatureProvider signatureProvider) {
        Optional.of(this)
                .map(DigitalSignatureCollectionBearer::getDigitalSignatureCollection)
                .orElse(Collections.emptyList())
                .forEach(dsb -> dsb.signData(certificateProvider, signatureProvider));
        return this;
    }

}
