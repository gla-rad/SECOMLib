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

package org.grad.secom.interfaces.springboot;

import org.grad.secom.exceptions.SecomGenericException;
import org.grad.secom.exceptions.SecomValidationException;
import org.grad.secom.models.SubscriptionNotificationObject;
import org.grad.secom.models.SubscriptionNotificationResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;

/**
 * The SECOM Subscription Notification Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface SubscriptionNotificationInterface extends GenericInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String SUBSCRIPTION_NOTIFICATION_INTERFACE_PATH = "/v1/subscription/notification";

    /**
     * POST /v1/subscription/notification : The interface receives notifications
     * when a subscription is created or removed by the information provider.
     *
     * @param subscriptionNotificationObject the subscription notification request object
     * @return the subscription notification response object
     */
    @PostMapping(SUBSCRIPTION_NOTIFICATION_INTERFACE_PATH)
    ResponseEntity<SubscriptionNotificationResponseObject> subscriptionNotification(@RequestBody @Valid SubscriptionNotificationObject subscriptionNotificationObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    @ExceptionHandler({
            SecomGenericException.class,
            ValidationException.class,
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentTypeMismatchException.class
    })
    default ResponseEntity<Object> handleSubscriptionNotificationInterfaceExceptions(Exception ex,
                                                                                     HttpServletRequest request,
                                                                                     HttpServletResponse response) {
        // Create the subscription notification response
        HttpStatus httpStatus;
        SubscriptionNotificationResponseObject subscriptionNotificationResponseObject = new SubscriptionNotificationResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException || ex instanceof ValidationException || ex instanceof MethodArgumentTypeMismatchException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            subscriptionNotificationResponseObject.setResponseText("Bad Request");
        } else {
            httpStatus = this.handleCommonExceptionResponseCode(ex);
            subscriptionNotificationResponseObject.setResponseText(httpStatus.getReasonPhrase());
        }

        // And send the error response back
        return ResponseEntity.status(httpStatus)
                .body(subscriptionNotificationResponseObject);
    }

}
