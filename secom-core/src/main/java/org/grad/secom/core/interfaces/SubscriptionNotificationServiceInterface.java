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

package org.grad.secom.core.interfaces;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.grad.secom.core.exceptions.SecomValidationException;
import org.grad.secom.core.models.SubscriptionNotificationObject;
import org.grad.secom.core.models.SubscriptionNotificationResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.grad.secom.core.base.SecomConstants.SECOM_VERSION;

/**
 * The SECOM Subscription Notification Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Path("/")
public interface SubscriptionNotificationServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String SUBSCRIPTION_NOTIFICATION_INTERFACE_PATH = "/" + SECOM_VERSION + "/subscription/notification";

    /**
     * POST /v1/subscription/notification : The interface receives notifications
     * when a subscription is created or removed by the information provider.
     *
     * @param subscriptionNotificationObject the subscription notification request object
     * @return the subscription notification response object
     */
    @Path(SUBSCRIPTION_NOTIFICATION_INTERFACE_PATH)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    SubscriptionNotificationResponseObject subscriptionNotification(@Valid SubscriptionNotificationObject subscriptionNotificationObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleSubscriptionNotificationInterfaceExceptions(Exception ex,
                                                                      HttpServletRequest request,
                                                                      HttpServletResponse response) {
        // Create the subscription notification response
        Response.Status responseStatus;
        SubscriptionNotificationResponseObject subscriptionNotificationResponseObject = new SubscriptionNotificationResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JsonMappingException
                || ex instanceof NotFoundException) {
            responseStatus = Response.Status.BAD_REQUEST;
            subscriptionNotificationResponseObject.setMessage("Bad Request");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            subscriptionNotificationResponseObject.setMessage(responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(subscriptionNotificationResponseObject)
                .build();
    }
}
