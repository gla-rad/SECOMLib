/*
 * Copyright (c) 2026 AIVeNautics
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

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.grad.secomv2.core.base.SecomConstants;
import org.grad.secomv2.core.exceptions.SecomInvalidCertificateException;
import org.grad.secomv2.core.exceptions.SecomNotAuthorisedException;
import org.grad.secomv2.core.exceptions.SecomNotFoundException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.GetByLinkObject;
import org.grad.secomv2.core.models.GetByLinkResponseObject;

public interface PostGetByLinkServiceInterface extends GenericSecomInterface {
    /**
     * The Interface Endpoint Path.
     */
    String POST_GET_BY_LINK_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/object/search/link";

    /**
     * POST /v2/object/search/link : The POST Get By Link interface is used for pulling
     * information from a data storage handled by the information owner. The
     * link to the data storage can be exchanged with Upload Link interface.
     * The owner of the information (provider) is responsible for relevant
     * authentication and authorization procedure before returning information.
     *
     * @param getByLinkObject the get by link object
     * @return get by link response object
     */
    @Path(POST_GET_BY_LINK_INTERFACE_PATH)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    GetByLinkResponseObject getByLink(@Valid GetByLinkObject getByLinkObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleGetByLinkInterfaceExceptions(Exception ex,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) {
        // Create the get by link response
        Response.Status responseStatus = null;
        String responseText = null;

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JsonMappingException
                || ex instanceof NotFoundException) {
            responseStatus = Response.Status.BAD_REQUEST;
            responseText = "Bad Request";
        } else if(ex instanceof SecomNotAuthorisedException) {
            responseStatus = Response.Status.FORBIDDEN;
            responseText = "Not authorized to requested information";
        } else if(ex instanceof SecomInvalidCertificateException) {
            responseStatus = Response.Status.FORBIDDEN;
            responseText = "Invalid Certificate";
        }  else if(ex instanceof SecomNotFoundException) {
            responseStatus = Response.Status.NOT_FOUND;
            responseText = String.format("Information with %s not found", ((SecomNotFoundException) ex).getIdentifier());
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            responseText = responseStatus.getReasonPhrase();
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(responseText)
                .build();
    }

}
