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

package org.grad.secom.interfaces;

import org.grad.secom.models.RemoveSubscriptionRequest;
import org.grad.secom.models.RemoveSubscriptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The SECOM Remove Subscription Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface RemoveSubscriptionInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String REMOVE_SUBSCRIPTION_INTERFACE_PATH = "/v1/subscription";

    /**
     * DELETE /v1/subscription : Subscription(s) can be removed either
     * internally by information owner, or externally by the consumer. This
     * interface shall be used by the consumer to request removal of
     * subscription.
     *
     * @param removeSubscriptionRequest the remove subscription object
     * @return the remove subscription response object
     */
    @DeleteMapping(REMOVE_SUBSCRIPTION_INTERFACE_PATH)
    ResponseEntity<RemoveSubscriptionResponse> removeSubscription(@RequestBody RemoveSubscriptionRequest removeSubscriptionRequest);

}
