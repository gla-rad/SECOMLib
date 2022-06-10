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
import org.grad.secom.exceptions.SecomNotFoundException;
import org.grad.secom.exceptions.SecomValidationException;
import org.grad.secom.models.AccessNotificationObject;
import org.grad.secom.models.AccessNotificationResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The SECOM Access Notification Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface AccessNotificationSecomInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String ACCESS_NOTIFICATION_INTERFACE_PATH = "/v1/access/notification";

    /**
     * POST /v1/access/notification : Result from Access Request performed on a
     * service instance shall be sent asynchronous through this client
     * interface.
     *
     * @param accessNotificationObject  the access notification object
     * @return the access notification response object
     */
    @Path(ACCESS_NOTIFICATION_INTERFACE_PATH)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    AccessNotificationResponseObject accessNotification(@Valid AccessNotificationObject accessNotificationObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleAccessNotificationInterfaceExceptions(Exception ex,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) {
        // Create the access notification response
        Response.Status responseStatus;
        AccessNotificationResponseObject accessNotificationResponseObject = new AccessNotificationResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException || ex instanceof ValidationException || ex instanceof InvalidFormatException || ex instanceof SecomNotFoundException) {
            responseStatus = Response.Status.BAD_REQUEST;
            accessNotificationResponseObject.setResponseText("Bad Request");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            accessNotificationResponseObject.setResponseText(responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(accessNotificationResponseObject)
                .build();
    }

}
