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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.grad.secomv2.core.base.SecomConstants;
import org.grad.secomv2.core.exceptions.SecomInvalidCertificateException;
import org.grad.secomv2.core.exceptions.SecomNotAuthorisedException;
import org.grad.secomv2.core.exceptions.SecomNotFoundException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.GetByLinkObject;
import org.grad.secomv2.core.models.GetByLinkResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

/**
 * The SECOM POST Get By Link Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@RequestMapping("/api/secom/")
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
    @PostMapping(path = POST_GET_BY_LINK_INTERFACE_PATH,
                consumes = { MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<GetByLinkResponseObject> getByLink(@Valid @RequestBody GetByLinkObject getByLinkObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handleGetByLinkInterfaceExceptions(Exception ex,
                                                                     HttpServletRequest request) {

        // Create the return objects
        HttpStatus httpStatus;
        String message;

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JacksonException
                || ex instanceof HttpClientErrorException.NotFound) {
            httpStatus = HttpStatus.BAD_REQUEST;
            message = "Bad Request";

        } else if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            message = "Not authorized to requested information";

        } else if(ex instanceof SecomInvalidCertificateException) {
            httpStatus = HttpStatus.FORBIDDEN;
            message = "Invalid certificate";

        }  else if(ex instanceof SecomNotFoundException) {
            httpStatus = HttpStatus.FORBIDDEN;
            message = String.format("Information with %s not found", ((SecomNotFoundException) ex).getIdentifier());

        } else {
            httpStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            message = httpStatus.getReasonPhrase();
        }

        return ResponseEntity
                .status(httpStatus)
                .body(message);

    }

}
