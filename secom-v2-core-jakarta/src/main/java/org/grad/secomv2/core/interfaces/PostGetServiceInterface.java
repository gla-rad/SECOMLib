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
import org.grad.secomv2.core.exceptions.SecomNotAuthorisedException;
import org.grad.secomv2.core.exceptions.SecomNotFoundException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.GetFilterObject;
import org.grad.secomv2.core.models.GetResponseObject;

/**
 * The SECOM Get Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Junyeon Won (email: junyeon.won@aivenautics.com)
 */
public interface PostGetServiceInterface extends GenericSecomInterface{
    /**
     * The Interface Endpoint Path.
     */
    String POST_GET_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/object/search";

    /**
     * POST /v2/object/search : The Get interface is used for pulling information from a
     * service provider. The owner of the information (provider) is responsible
     * for the authorization procedure before returning information.
     *
     * @param getFilterObject the get filter object
     * @return the object information
     */
    @Path(POST_GET_INTERFACE_PATH)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    GetResponseObject get(@Valid GetFilterObject getFilterObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleGerInterfaceException(Exception ex,
                                                HttpServletRequest request,
                                                HttpServletResponse response) {
        // Create the get response
        int responseStatusCode;
        GetResponseObject getResponseObject = new GetResponseObject();

        // Handle according to the exception type
        if(ex instanceof ValidationException
                || ex instanceof JsonMappingException
                || ex instanceof NotFoundException) {
            responseStatusCode = Response.Status.BAD_REQUEST.getStatusCode();
        } else if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException) {
            // 422 (Unprocessable Entity) is used when the request is syntactically valid
            // but cannot be processed due to domain-specific validation (SECOM rules).
            // Note: 422 is not defined in jakarta.ws.rs.Response.Status, so it is set explicitly.
            responseStatusCode = 422;
        }
        else if(ex instanceof SecomNotAuthorisedException) {
            responseStatusCode = Response.Status.FORBIDDEN.getStatusCode();
        } else if(ex instanceof SecomNotFoundException) {
            responseStatusCode = Response.Status.NOT_FOUND.getStatusCode();
        } else {
            responseStatusCode = GenericSecomInterface.handleCommonExceptionResponseCode(ex).getStatusCode();
        }

        // And send the error response back
        return Response.status(responseStatusCode)
                .entity(getResponseObject)
                .build();
    }

}
