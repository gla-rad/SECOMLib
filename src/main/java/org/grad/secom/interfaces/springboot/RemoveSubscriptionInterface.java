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

import org.grad.secom.exceptions.*;
import org.grad.secom.models.RemoveSubscriptionObject;
import org.grad.secom.models.RemoveSubscriptionResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;

/**
 * The SECOM Remove Subscription Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface RemoveSubscriptionInterface extends GenericInterface {

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
     * @param removeSubscriptionObject the remove subscription object
     * @return the remove subscription response object
     */
    @DeleteMapping(REMOVE_SUBSCRIPTION_INTERFACE_PATH)
    ResponseEntity<RemoveSubscriptionResponseObject> removeSubscription(@RequestBody @Valid RemoveSubscriptionObject removeSubscriptionObject);

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
    default ResponseEntity<Object> handleRemoveSubscriptionInterfaceExceptions(Exception ex,
                                                                               HttpServletRequest request,
                                                                               HttpServletResponse response) {
        // Create the remove subscription response
        HttpStatus httpStatus;
        RemoveSubscriptionResponseObject removeSubscriptionResponseObject = new RemoveSubscriptionResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException || ex instanceof ValidationException || ex instanceof MethodArgumentTypeMismatchException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            removeSubscriptionResponseObject.setResponseText("Bad Request");
        } else if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            removeSubscriptionResponseObject.setResponseText("Not authorized to remove subscription");
        } else if(ex instanceof SecomNotFoundException) {
            httpStatus = HttpStatus.FORBIDDEN;
            removeSubscriptionResponseObject.setResponseText("Subscriber identifier not found");
        } else {
            httpStatus = this.handleCommonExceptionResponseCode(ex);
            removeSubscriptionResponseObject.setResponseText(httpStatus.getReasonPhrase());
        }

        // And send the error response back
        return ResponseEntity.status(httpStatus)
                .body(removeSubscriptionResponseObject);
    }

}
