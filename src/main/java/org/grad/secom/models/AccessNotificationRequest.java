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

import org.grad.secom.models.enums.DecisionReasonEnum;

import javax.validation.constraints.NotNull;

/**
 * The SECOM Access Notification Request Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class AccessNotificationRequest {

    // Class Variables
    @NotNull
    private Boolean decision;
    @NotNull
    private String decisionReason;
    @NotNull
    private DecisionReasonEnum decisionReasonEnum;
    @NotNull
    private String transactionIdentifier;

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
     * Gets decision reason.
     *
     * @return the decision reason
     */
    public String getDecisionReason() {
        return decisionReason;
    }

    /**
     * Sets decision reason.
     *
     * @param decisionReason the decision reason
     */
    public void setDecisionReason(String decisionReason) {
        this.decisionReason = decisionReason;
    }

    /**
     * Gets decision reason enum.
     *
     * @return the decision reason enum
     */
    public DecisionReasonEnum getDecisionReasonEnum() {
        return decisionReasonEnum;
    }

    /**
     * Sets decision reason enum.
     *
     * @param decisionReasonEnum the decision reason enum
     */
    public void setDecisionReasonEnum(DecisionReasonEnum decisionReasonEnum) {
        this.decisionReasonEnum = decisionReasonEnum;
    }

    /**
     * Gets transaction identifier.
     *
     * @return the transaction identifier
     */
    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    /**
     * Sets transaction identifier.
     *
     * @param transactionIdentifier the transaction identifier
     */
    public void setTransactionIdentifier(String transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }
}
