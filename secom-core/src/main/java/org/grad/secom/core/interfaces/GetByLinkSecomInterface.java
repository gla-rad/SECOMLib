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

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.grad.secom.core.exceptions.SecomInvalidCertificateException;
import org.grad.secom.core.exceptions.SecomNotAuthorisedException;
import org.grad.secom.core.exceptions.SecomNotFoundException;
import org.grad.secom.core.exceptions.SecomValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * The SECOM Get By Link Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface GetByLinkSecomInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String GET_BY_LINK_INTERFACE_PATH = "/v1/object/link";

    /**
     * GET /v1/object/link : The Get By Link interface is used for pulling
     * information from a data storage handled by the information owner. The
     * link to the data storage can be exchanged with Upload Link interface.
     * The owner of the information (provider) is responsible for relevant
     * authentication and authorization procedure before returning information.
     *
     * @param transactionIdentifier the transaction identifier
     * @return the object in an "application/octet-stream" encoding
     */
    @Path(GET_BY_LINK_INTERFACE_PATH)
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    Object getByLink(@QueryParam("transactionIdentifier") UUID transactionIdentifier);

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
        if(ex instanceof SecomValidationException || ex.getCause() instanceof SecomValidationException || ex instanceof ValidationException || ex instanceof InvalidFormatException) {
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
