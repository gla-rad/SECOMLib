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

import org.grad.secom.core.models.enums.SubscriptionEventEnum;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * The SECOM Subscription Notification Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SubscriptionNotificationObject {

    // Class Variables
    @NotNull
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
}
