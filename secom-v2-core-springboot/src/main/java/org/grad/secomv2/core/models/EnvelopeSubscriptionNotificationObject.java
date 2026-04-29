/*
 * Copyright (c) 2026 GLA Research and Development Directorate
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

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.grad.secomv2.core.models.enums.SubscriptionEventEnum;

import java.util.UUID;

/**
 * The SECOM Envelope Subscription Notification Object Class.
 *
 * @author Junyeon Won (email: junyeon.won@aivenautics.com)
 */
public class EnvelopeSubscriptionNotificationObject extends AbstractEnvelope {
    // Class Variables
    @NotNull
    @Schema(type = "string", example = "550e8400-e29b-41d4-a716-446655440000")
    @Pattern(regexp = "^[{(]?[0-9a-fA-F]{8}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{12}[)}]?$")
    private UUID subscriptionIdentifier;
    @NotNull
    private SubscriptionEventEnum eventEnum;

    /**
     * Gets subscription identifier.
     *
     * @return the subscription identifier
     */
    public UUID getSubscriptionIdentifier() {
        return subscriptionIdentifier;
    }

    /**
     * Sets subscription identifier.
     *
     * @param subscriptionIdentifier the subscription identifier
     */
    public void setSubscriptionIdentifier(UUID subscriptionIdentifier) {
        this.subscriptionIdentifier = subscriptionIdentifier;
    }

    /**
     * Gets event enum.
     *
     * @return the event enum
     */
    public SubscriptionEventEnum getEventEnum() {
        return eventEnum;
    }

    /**
     * Sets event enum.
     *
     * @param eventEnum the event enum
     */
    public void setEventEnum(SubscriptionEventEnum eventEnum) {
        this.eventEnum = eventEnum;
    }

    /**
     * This method should be implemented by all envelop objects to allow the
     * generation of the signature CSV attribute array
     *
     * @return the generated signature CSV attribute array
     */
    @Override
    public Object[] getAttributeArray() {
        return new Object[]{
                subscriptionIdentifier,
                eventEnum,
                envelopeSignatureCertificate,
                envelopeRootCertificateThumbprint,
                envelopeSignatureTime,
                // TODO: This is not included in CD3 but it makes sense to include in the code
                envelopeSignatureReference
        };
    }
}
