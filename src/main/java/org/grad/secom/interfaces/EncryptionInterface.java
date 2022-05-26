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
import org.grad.secom.exceptions.SecomNotImplementedException;
import org.grad.secom.models.EncryptionKeyResponse;
import org.grad.secom.models.EncryptionKeyRequest;
import org.grad.secom.models.StatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The SECOM Encryption Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface EncryptionInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String CAPABILITY_INTERFACE_PATH = "/v1/capability";

    /**
     * POST /v1/encryptionkey : The purpose of the interface is to exchange a
     * temporary secret key.
     *
     * @return the encryption key response object
     */
    @PostMapping(CAPABILITY_INTERFACE_PATH)
    ResponseEntity<EncryptionKeyResponse> uploadEncryptionKey(@RequestBody EncryptionKeyRequest encryptionKeyRequest);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    @ExceptionHandler({SecomGenericException.class, HttpRequestMethodNotSupportedException.class})
    default ResponseEntity<Object> handleEncryptionInterfaceExceptions(Exception ex,
                                                                       HttpServletRequest request,
                                                                       HttpServletResponse response) {
        // Create the upload response
        HttpStatus httpStatus;
        EncryptionKeyResponse encryptionKeyResponse = new EncryptionKeyResponse();

        // Handle according to the exception type
        if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            encryptionKeyResponse.setResponseText("Not authorized to requested information");
        } else if(ex instanceof HttpRequestMethodNotSupportedException) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
            encryptionKeyResponse.setResponseText("Method not allowed");
        } else if(ex instanceof SecomNotImplementedException) {
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
            encryptionKeyResponse.setResponseText("Not implemented");
        }  else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            encryptionKeyResponse.setResponseText(ex.getMessage());
        }

        // Otherwise, send a generic internal server error
        return ResponseEntity.status(httpStatus)
                .body(encryptionKeyResponse);
    }

}
