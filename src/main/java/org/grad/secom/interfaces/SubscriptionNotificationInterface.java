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

import org.grad.secom.models.SubscriptionNotificationRequest;
import org.grad.secom.models.SubscriptionNotificationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The SECOM Subscription Notification Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface SubscriptionNotificationInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String SUBSCRIPTION_NOTIFICATION_INTERFACE_PATH = "/v1/subscription/notification";

    /**
     * POST /v1/subscription/notification : The interface receives notifications
     * when a subscription is created or removed by the information provider.
     *
     * @param subscriptionNotificationRequest the subscription notification request object
     * @return the subscription notification response object
     */
    @PostMapping(SUBSCRIPTION_NOTIFICATION_INTERFACE_PATH)
    ResponseEntity<SubscriptionNotificationResponse> subscription(@RequestBody SubscriptionNotificationRequest subscriptionNotificationRequest);

}
