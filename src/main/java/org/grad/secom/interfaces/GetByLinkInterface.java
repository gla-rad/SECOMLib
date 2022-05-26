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

package org.grad.secom.interfaces;

import org.grad.secom.exceptions.SecomGenericException;
import org.grad.secom.exceptions.SecomNotAuthorisedException;
import org.grad.secom.exceptions.SecomNotFoundException;
import org.grad.secom.exceptions.SecomNotImplementedException;
import org.grad.secom.models.GetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The SECOM Get By Link Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface GetByLinkInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String GET_BY_LINK_INTERFACE_PATH = "/v1/object/link";

    /**
     * GET /v1/object/link : The Get By Link interface is used for pulling
     * information from a data storage handled by the information owner. The
     * link to the data storage can be exchanged with Upload Link interface.
     * The owner of the information (provider) is responsible for relevant
     * authentication and authorization procedure before returning information.
     *
     * @param transactionIdentifier the transaction identifier
     * @return the object in an "application/zip" encoding
     */
    @GetMapping(GET_BY_LINK_INTERFACE_PATH)
    ResponseEntity<StreamingResponseBody> getByLink(@RequestParam(value = "transactionIdentifier") String transactionIdentifier);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    @ExceptionHandler({SecomGenericException.class, HttpRequestMethodNotSupportedException.class})
    default ResponseEntity<Object> handleGetByLinkInterfaceExceptions(Exception ex,
                                                                      HttpServletRequest request,
                                                                      HttpServletResponse response) {
        // Create the upload response
        HttpStatus httpStatus = null;
        String responseText = null;

        // Handle according to the exception type
        if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            responseText = "Not authorized to requested information";
        } else if(ex instanceof HttpRequestMethodNotSupportedException) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
            responseText = "Method not allowed";
        } else if(ex instanceof SecomNotImplementedException) {
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
            responseText = "Not implemented";
        }  else if (ex instanceof SecomNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            responseText = "Information not found";
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            responseText = ex.getMessage();
        }

        // Otherwise, send a generic internal server error
        return ResponseEntity.status(httpStatus)
                .body(responseText);
    }

}
