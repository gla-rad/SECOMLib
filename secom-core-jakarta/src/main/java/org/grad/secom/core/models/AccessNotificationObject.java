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

package org.grad.secom.core.models;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * The SECOM Access Notification Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class AccessNotificationObject {

    // Class Variables
    @NotNull
    private Boolean decision;
    @NotNull
    private String reason;
    @NotNull
    private UUID transactionIdentifier;

    /**
     * Gets decision.
     *
     * @return the decision
     */
    public Boolean getDecision() {
        return decision;
    }

    /**
     * Sets decision.
     *
     * @param decision the decision
     */
    public void setDecision(Boolean decision) {
        this.decision = decision;
    }

    /**
     * Gets reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets reason.
     *
     * @param reason the reason
     */
    public void setReason(String reason) {
        this.reason = reason;
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
}
