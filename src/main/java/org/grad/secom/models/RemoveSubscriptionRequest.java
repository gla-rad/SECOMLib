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

import javax.validation.constraints.NotNull;

/**
 * The SECOM Remove Subscription Request Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class RemoveSubscriptionRequest {

    // Class Variables
    @NotNull
    private String subscriptionIdentifier;

    /**
     * Gets subscription identifier.
     *
     * @return the subscription identifier
     */
    public String getSubscriptionIdentifier() {
        return subscriptionIdentifier;
    }

    /**
     * Sets subscription identifier.
     *
     * @param subscriptionIdentifier the subscription identifier
     */
    public void setSubscriptionIdentifier(String subscriptionIdentifier) {
        this.subscriptionIdentifier = subscriptionIdentifier;
    }
}
