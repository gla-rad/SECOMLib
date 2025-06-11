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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.grad.secom.core.models.ExchangeMetadata;

import java.util.Optional;

/**
 * The SECOM Exchange Metadata Bearer Interface.
 *
 * This interface forces the implementing classes to provide a way of accessing
 * and updating the SECOM Exchange Metadata contained in an object.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface GenericExchangeMetadataBearer {

    /**
     * Allows the data bearer object to access the SECOM exchange metadata
     * that will contain amongst other info, the signature information.
     *
     * @return
     */
    @JsonIgnore
    ExchangeMetadata getExchangeMetadata();

    /**
     * Allows the exchange metadata bearer object to update the SECOM exchange
     * metadata that will contain amongst other info, the signature
     * information.
     *
     * @param exchangeMetadata  the SECOM Exchange metadata
     */
    @JsonIgnore
    void setExchangeMetadata(ExchangeMetadata exchangeMetadata);

    /**
     * This is a helper function to initialise the populate the SECOM exchange
     * metadata object with the appropriate values, depending on the current
     * configuration and available resources.
     *
     * @param signatureProvider     The SECOM signature provider, if it exists
     * @return the update generic exchange metadata bearer
     */
    default GenericExchangeMetadataBearer prepareMetadata(SecomSignatureProvider signatureProvider) {
        // Get the current (or new) SECOM exchange metadata
        final ExchangeMetadata metadata = Optional.of(this)
                .map(GenericExchangeMetadataBearer::getExchangeMetadata)
                .orElseGet(ExchangeMetadata::new);

        // Populate the known values
        metadata.setProtectionScheme(signatureProvider != null ? SecomConstants.SECOM_PROTECTION_SCHEME : metadata.getProtectionScheme());
        metadata.setDigitalSignatureReference(signatureProvider != null ? signatureProvider.getSignatureAlgorithm() : metadata.getDigitalSignatureReference());
        metadata.setDataProtection(Optional.of(metadata)
                .map(ExchangeMetadata::getDataProtection)
                .orElse(Boolean.FALSE));
        metadata.setCompressionFlag(Optional.of(metadata)
                .map(ExchangeMetadata::getCompressionFlag)
                .orElse(Boolean.FALSE));

        // Set the new updated version
        this.setExchangeMetadata(metadata);

        // Return the same object for further processing
        return this;
    }
}
