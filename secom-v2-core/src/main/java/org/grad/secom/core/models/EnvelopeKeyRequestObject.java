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

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * The SECOM Envelope Key Request Object.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeKeyRequestObject extends AbstractEnvelope {

    // Class Variables
    @NotNull
    @Schema(description = "Information identifier retrieved by using reference from response in Get Summary request (UUID)")
    private UUID dataReference;
    @NotNull
    @Schema(description = "Public certificate of data consumer for deriving shared symmetric key")
    private String publicCertificate;

    /**
     * Gets data reference.
     *
     * @return the data reference
     */
    public UUID getDataReference() {
        return dataReference;
    }

    /**
     * Sets data reference.
     *
     * @param dataReference the data reference
     */
    public void setDataReference(UUID dataReference) {
        this.dataReference = dataReference;
    }

    /**
     * Gets public certificate.
     *
     * @return the public certificate
     */
    public String getPublicCertificate() {
        return publicCertificate;
    }

    /**
     * Sets public certificate.
     *
     * @param publicCertificate the public certificate
     */
    public void setPublicCertificate(String publicCertificate) {
        this.publicCertificate = publicCertificate;
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
                dataReference,
                envelopeSignatureCertificate,
                envelopeRootCertificateThumbprint,
                envelopeSignatureTime
        };
    }
}
