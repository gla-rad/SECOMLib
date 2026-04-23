/*
 * Copyright (c) 2026 Digital Maritime Consultancy - A member of Team Aivenautics
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
import org.grad.secomv2.core.base.SecomConstants;
import org.grad.secomv2.core.exceptions.SecomNotFoundException;
import org.grad.secomv2.core.exceptions.SecomSignatureVerificationException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.grad.secomv2.core.models.SearchResult;

/**
 The SECOM Retrieve Result Interface Definition.
 *
 * @author Jakob Svenningsen (email: jakob@dmc.international)
 */
public interface RetrieveResultServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String RETRIEVE_RESULT_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/retrieveResult";

    /**
     * POST /v2/retrieveResult : The purpose of this interface is pull results of a
     * search transaction for which more results may arrive asynchronously. The search
     * transaction is identified by the transactionId field in the response to the iniitial
     * searchService request.
     *
     * @param retrieveResultObject    The search filter object
     * @return the result object
     */
    @Path(RETRIEVE_RESULT_INTERFACE_PATH)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    SearchResult retrieveResult(@Valid RetrieveResultObject retrieveResultObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleRetrieveResultInterfaceExceptions(Exception ex,
                                                            HttpServletRequest request,
                                                            HttpServletResponse response) {

        // Create the encryption key response
        jakarta.ws.rs.core.Response.Status responseStatus;
        EncryptionKeyResponseObject encryptionKeyResponseObject = new EncryptionKeyResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JsonMappingException
                || ex instanceof NotFoundException
                || ex instanceof SecomSignatureVerificationException
        ) {
            responseStatus = Response.Status.BAD_REQUEST;
            encryptionKeyResponseObject.setMessage("Bad Request");
        } else if(ex instanceof SecomNotFoundException) {
            responseStatus = Response.Status.NOT_FOUND;
            encryptionKeyResponseObject.setMessage("Information not found");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            encryptionKeyResponseObject.setMessage(responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(encryptionKeyResponseObject)
                .build();
    }
}
