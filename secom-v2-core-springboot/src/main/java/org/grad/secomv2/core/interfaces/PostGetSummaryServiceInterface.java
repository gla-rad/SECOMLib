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
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.grad.secomv2.core.base.SecomConstants;
import org.grad.secomv2.core.exceptions.SecomNotAuthorisedException;
import org.grad.secomv2.core.exceptions.SecomNotFoundException;
import org.grad.secomv2.core.exceptions.SecomSchemaValidationException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.GetSummaryFilterObject;
import org.grad.secomv2.core.models.GetSummaryResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

/**
 * The SECOM POST Get Summary Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */

@RequestMapping("/api/secom/")
public interface PostGetSummaryServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String POST_GET_SUMMARY_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/object/search/summary";

    /**
     * POST /v2/object/search/summary :  A list of information shall be returned from
     * this interface. The summary contains identity, status and short
     * description of each information object. The actual information object
     * shall be retrieved using the Get interface.
     *
     * @param getSummaryFilterObject the get summary filter object
     * @return the summary response object
     */
    @PostMapping(path = POST_GET_SUMMARY_INTERFACE_PATH,
                consumes = { MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<GetSummaryResponseObject> getSummary(@Valid @RequestBody GetSummaryFilterObject getSummaryFilterObject);


    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handleGetSummaryInterfaceExceptions(Exception ex,
                                                                      HttpServletRequest request) {
        // Create the get summary response
        HttpStatus httpStatus;
        GetSummaryResponseObject getSummaryResponseObject = new GetSummaryResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JacksonException
                || ex instanceof HttpClientErrorException.NotFound) {
            httpStatus = HttpStatus.BAD_REQUEST;

        } else if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;

        } else if(ex instanceof SecomNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;

        } else if(ex instanceof SecomSchemaValidationException) {
            httpStatus = HttpStatus.UNPROCESSABLE_CONTENT;

        } else {
            httpStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);

        }
        return ResponseEntity
                .status(httpStatus)
                .body(getSummaryResponseObject);
    }

}
