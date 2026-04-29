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

package org.grad.secomv2.core.interfaces;

import tools.jackson.core.JacksonException;
import org.grad.secomv2.core.base.SecomConstants;
import org.grad.secomv2.core.exceptions.SecomNotAuthorisedException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.SubscriptionRequestObject;
import org.grad.secomv2.core.models.SubscriptionResponseObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

/**
 * The SECOM Subscription Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */

@RequestMapping("/api/secom/")
public interface SubscriptionServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String SUBSCRIPTION_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/subscription";

    /**
     * POST /v2/subscription : Request subscription on information, either
     * specific information according to parameters, or the information
     * accessible upon decision by the information provider.
     *
     * @param subscriptionRequestObject the subscription object
     * @return the subscription response object
     */
    @PostMapping(path = SUBSCRIPTION_INTERFACE_PATH,
                consumes = {MediaType.APPLICATION_JSON_VALUE},
                produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<SubscriptionResponseObject> subscription(@Valid @RequestBody SubscriptionRequestObject subscriptionRequestObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handleSubscriptionInterfaceExceptions(Exception ex,
                                                                        HttpServletRequest request) {
        // Create the subscription response
        HttpStatus httpStatus;
        SubscriptionResponseObject subscriptionResponseObject = new SubscriptionResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JacksonException
                || ex instanceof HttpClientErrorException.NotFound) {
            httpStatus = HttpStatus.BAD_REQUEST;
            subscriptionResponseObject.setMessage("Bad Request");
        } else if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            subscriptionResponseObject.setMessage("Not authorized to requested information");
        } else {
            httpStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            subscriptionResponseObject.setMessage(httpStatus.getReasonPhrase());
        }

        // And send the error response back
        return ResponseEntity
                .status(httpStatus)
                .body(subscriptionResponseObject);
    }

}
