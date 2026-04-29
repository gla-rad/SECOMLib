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
import org.grad.secomv2.core.exceptions.SecomNotAuthorisedException;
import org.grad.secomv2.core.exceptions.SecomNotFoundException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.AccessRequestObject;
import org.grad.secomv2.core.models.AccessResponseObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

/**
 * The SECOM Access Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@RequestMapping("/api/secom/")
public interface AccessServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String ACCESS_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/access";

    /**
     * POST /v2/access : Access to the service instance information can be
     * requested through the Request Access interface.
     *
     * @param accessRequestObject the request access object
     * @return the request access response object
     */
    @PostMapping(path = ACCESS_INTERFACE_PATH,
                consumes = { MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<AccessResponseObject> requestAccess(@Valid @RequestBody AccessRequestObject accessRequestObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handleAccessInterfaceExceptions(Exception ex,
                                                          HttpServletRequest request) {
        // Create the access response
        HttpStatus httpStatus;
        AccessResponseObject accessResponseObject = new AccessResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JacksonException
                || ex instanceof SecomNotFoundException
                || ex instanceof HttpClientErrorException.NotFound) {
            accessResponseObject.setMessage("Bad Request");
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if(ex instanceof SecomNotAuthorisedException) {
            accessResponseObject.setMessage("Not authorized to requested information");
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            httpStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            accessResponseObject.setMessage(httpStatus.getReasonPhrase());
        }

        return ResponseEntity
                .status(httpStatus)
                .body(accessResponseObject);

    }

}
