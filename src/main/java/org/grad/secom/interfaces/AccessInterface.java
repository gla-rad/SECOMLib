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
import org.grad.secom.models.AccessRequestObject;
import org.grad.secom.models.AccessResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

/**
 * The SECOM Access Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface AccessInterface extends GenericInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String REMOVE_SUBSCRIPTION_INTERFACE_PATH = "/v1/access";

    /**
     * POST /v1/access : Access to the service instance information can be
     * requested through the Request Access interface.
     *
     * @param accessRequestObject the request access object
     * @return the request access response object
     */
    @PostMapping(REMOVE_SUBSCRIPTION_INTERFACE_PATH)
    ResponseEntity<AccessResponseObject> requestAccess(@RequestBody AccessRequestObject accessRequestObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    @ExceptionHandler({
            SecomGenericException.class,
            ValidationException.class,
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentTypeMismatchException.class
    })
    default ResponseEntity<Object> handleAccessInterfaceExceptions(Exception ex,
                                                                   HttpServletRequest request,
                                                                   HttpServletResponse response) {
        // Create the access response
        HttpStatus httpStatus;
        AccessResponseObject accessResponseObject = new AccessResponseObject();

        // Handle according to the exception type
        if(ex instanceof ValidationException || ex instanceof MethodArgumentTypeMismatchException || ex instanceof SecomNotFoundException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            accessResponseObject.setResponseText("Bad Request");
        } else if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            accessResponseObject.setResponseText("Not authorized to requested information");
        } else {
            httpStatus = this.handleCommonExceptionResponseCode(ex);
            accessResponseObject.setResponseText(httpStatus.getReasonPhrase());
        }

        // And send the error response back
        return ResponseEntity.status(httpStatus)
                .body(accessResponseObject);
    }

}
