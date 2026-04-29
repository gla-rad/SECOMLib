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
import org.grad.secomv2.core.exceptions.SecomNotFoundException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.grad.secomv2.core.models.SearchResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

/**
 The SECOM Retrieve Result Interface Definition.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */

@RequestMapping("/api/secom/")
public interface RetrieveResultServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String RETRIEVE_RESULT_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/retrieveResult";

    /**
     * POST /v2/retrieveResult : The purpose of this interface is pull results of a
     * search transaction for which more results may arrive asynchronously. The search
     * transaction is identified by the transactionId field in the response to the initial
     * searchService request.
     *
     * @param retrieveResultObject    The search filter object
     * @return the result object
     */
    @PostMapping(path = RETRIEVE_RESULT_INTERFACE_PATH,
                consumes = { MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<SearchResult> retrieveResult(@Valid @RequestBody RetrieveResultObject retrieveResultObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handleRetrieveResultInterfaceExceptions(Exception ex,
                                                                          HttpServletRequest request) {

        // Create the encryption key response
        HttpStatus responseStatus;
        EncryptionKeyResponseObject encryptionKeyResponseObject = new EncryptionKeyResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JacksonException
                || ex instanceof HttpClientErrorException.NotFound) {
            responseStatus = HttpStatus.BAD_REQUEST;
            encryptionKeyResponseObject.setMessage("Bad Request");
        } else if(ex instanceof SecomNotFoundException) {
            responseStatus = HttpStatus.NOT_FOUND;
            encryptionKeyResponseObject.setMessage("Information not found");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            encryptionKeyResponseObject.setMessage(responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return ResponseEntity
                .status(responseStatus)
                .body(encryptionKeyResponseObject);

    }
}
