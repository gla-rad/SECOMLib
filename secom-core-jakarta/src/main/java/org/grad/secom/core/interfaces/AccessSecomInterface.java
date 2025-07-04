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
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.ws.rs.*;
import org.grad.secom.core.base.SecomConstants;
import org.grad.secom.core.exceptions.SecomNotAuthorisedException;
import org.grad.secom.core.exceptions.SecomNotFoundException;
import org.grad.secom.core.exceptions.SecomValidationException;
import org.grad.secom.core.models.AccessRequestObject;
import org.grad.secom.core.models.AccessResponseObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * The SECOM Access Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface AccessSecomInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String ACCESS_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/access";

    /**
     * POST /v1/access : Access to the service instance information can be
     * requested through the Request Access interface.
     *
     * @param accessRequestObject the request access object
     * @return the request access response object
     */
    @Path(ACCESS_INTERFACE_PATH)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    AccessResponseObject requestAccess(@Valid AccessRequestObject accessRequestObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleAccessInterfaceExceptions(Exception ex,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {
        // Create the access response
        Response.Status responseStatus;
        AccessResponseObject accessResponseObject = new AccessResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JsonMappingException
                || ex instanceof SecomNotFoundException
                || ex instanceof NotFoundException) {
            responseStatus = Response.Status.BAD_REQUEST;
            accessResponseObject.setResponseText("Bad Request");
        } else if(ex instanceof SecomNotAuthorisedException) {
            responseStatus = Response.Status.FORBIDDEN;
            accessResponseObject.setResponseText("Not authorized to requested information");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            accessResponseObject.setResponseText(responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(accessResponseObject)
                .build();
    }

}
