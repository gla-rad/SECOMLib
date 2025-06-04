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

package org.grad.secom.v2.core.interfaces;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.grad.secom.v2.core.exceptions.SecomNotAuthorisedException;
import org.grad.secom.v2.core.exceptions.SecomValidationException;
import org.grad.secom.v2.core.models.SubscriptionResponseObject;
import org.grad.secom.v2.core.models.SubscriptionRequestObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The SECOM Subscription Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface SubscriptionServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String SUBSCRIPTION_INTERFACE_PATH = "/v2/subscription";

    /**
     * POST /v1/subscription : Request subscription on information, either
     * specific information according to parameters, or the information
     * accessible upon decision by the information provider.
     *
     * @param subscriptionRequestObject the subscription object
     * @return the subscription response object
     */
    @Path(SUBSCRIPTION_INTERFACE_PATH)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    SubscriptionResponseObject subscription(@Valid SubscriptionRequestObject subscriptionRequestObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleSubscriptionInterfaceExceptions(Exception ex,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) {
        // Create the subscription response
        Response.Status responseStatus;
        SubscriptionResponseObject subscriptionResponseObject = new SubscriptionResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JsonMappingException
                || ex instanceof NotFoundException) {
            responseStatus = Response.Status.BAD_REQUEST;
            subscriptionResponseObject.setMessage("Bad Request");
        } else if(ex instanceof SecomNotAuthorisedException) {
            responseStatus = Response.Status.FORBIDDEN;
            subscriptionResponseObject.setMessage("Not authorized to requested information");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            subscriptionResponseObject.setMessage(responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(subscriptionResponseObject)
                .build();
    }

}
