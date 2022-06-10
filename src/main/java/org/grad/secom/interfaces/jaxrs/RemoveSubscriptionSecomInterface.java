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

package org.grad.secom.interfaces.jaxrs;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.grad.secom.exceptions.SecomNotAuthorisedException;
import org.grad.secom.exceptions.SecomNotFoundException;
import org.grad.secom.exceptions.SecomValidationException;
import org.grad.secom.models.RemoveSubscriptionObject;
import org.grad.secom.models.RemoveSubscriptionResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * The SECOM Remove Subscription Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface RemoveSubscriptionSecomInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String REMOVE_SUBSCRIPTION_INTERFACE_PATH = "/v1/subscription";

    /**
     * DELETE /v1/subscription : Subscription(s) can be removed either
     * internally by information owner, or externally by the consumer. This
     * interface shall be used by the consumer to request removal of
     * subscription.
     *
     * @param removeSubscriptionObject the remove subscription object
     * @return the remove subscription response object
     */
    @Path(REMOVE_SUBSCRIPTION_INTERFACE_PATH)
    @DELETE
    @Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    @Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
    RemoveSubscriptionResponseObject removeSubscription(@Valid RemoveSubscriptionObject removeSubscriptionObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleRemoveSubscriptionInterfaceExceptions(Exception ex,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) {
        // Create the remove subscription response
        Response.Status responseStatus;
        RemoveSubscriptionResponseObject removeSubscriptionResponseObject = new RemoveSubscriptionResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException || ex instanceof ValidationException || ex instanceof InvalidFormatException) {
            responseStatus = Response.Status.BAD_REQUEST;
            removeSubscriptionResponseObject.setResponseText("Bad Request");
        } else if(ex instanceof SecomNotAuthorisedException) {
            responseStatus = Response.Status.FORBIDDEN;
            removeSubscriptionResponseObject.setResponseText("Not authorized to remove subscription");
        } else if(ex instanceof SecomNotFoundException) {
            responseStatus = Response.Status.FORBIDDEN;
            removeSubscriptionResponseObject.setResponseText("Subscriber identifier not found");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            removeSubscriptionResponseObject.setResponseText(responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(removeSubscriptionResponseObject)
                .build();
    }

}
