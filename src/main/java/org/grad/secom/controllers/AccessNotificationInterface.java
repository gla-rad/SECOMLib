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

package org.grad.secom.controllers;

import org.grad.secom.models.AccessNotificationResponse;
import org.grad.secom.models.enums.DecisionReasonEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * The SECOM Access Notification Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface AccessNotificationInterface {

    /**
     * GET /v1/access/notification : Result from Access Request performed on a
     * service instance shall be sent asynchronous through this client
     * interface.
     *
     * @param decision access request decision, yes or no
     * @param decisionReason human-readable reason for decision
     * @param decisionReasonEnum machine-readable for decision
     * @param transactionIdentifier transaction identifier corresponding to request access
     * @return the object information
     */
    ResponseEntity<AccessNotificationResponse> getObject(@RequestParam("decision") Optional<Boolean> decision,
                                                         @RequestParam("decisionReason") Optional<String> decisionReason,
                                                         @RequestParam("decisionReasonEnum") Optional<DecisionReasonEnum> decisionReasonEnum,
                                                         @RequestParam("transactionIdentifier") Optional<String> transactionIdentifier);

}
