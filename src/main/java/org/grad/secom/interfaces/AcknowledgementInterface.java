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
import org.grad.secom.models.AcknowledgementRequest;
import org.grad.secom.models.AcknowledgementResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The SECOM Acknowledgement Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface AcknowledgementInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String ACKNOWLEDGMENT_INTERFACE_PATH = "/v1/acknowledgement";

    /**
     * POST /v1/acknowledgement : During upload of information, an
     * acknowledgement can be requested which is expected to be received when
     * the uploaded message has been delivered to the end system (technical
     * acknowledgement), and an acknowledgement when the message has been opened
     * (read) by the end user (operational acknowledgement). The acknowledgement
     * contains a reference to object delivered.
     *
     * @param acknowledgementRequest  the acknowledgement object
     * @return the acknowledgement response object
     */
    @PostMapping(ACKNOWLEDGMENT_INTERFACE_PATH)
    ResponseEntity<AcknowledgementResponse> acknowledgment(@RequestBody AcknowledgementRequest acknowledgementRequest);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    @ExceptionHandler({SecomGenericException.class, HttpRequestMethodNotSupportedException.class})
     default ResponseEntity<AcknowledgementResponse> handleAcknowledgementInterfaceExceptions(Exception ex,
                                                                                              HttpServletRequest request,
                                                                                              HttpServletResponse response) {

        // Create the upload response
        HttpStatus httpStatus;
        AcknowledgementResponse acknowledgementResponse = new AcknowledgementResponse();

        // Handle according to the exception type
        if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            acknowledgementResponse.setResponseText("Not authorized to upload ACK");
        } else if(ex instanceof HttpRequestMethodNotSupportedException) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
            acknowledgementResponse.setResponseText("Method not allowed");
        } else if(ex instanceof SecomNotImplementedException) {
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
            acknowledgementResponse.setResponseText("Not implemented");
        } else if(ex instanceof SecomNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            acknowledgementResponse.setResponseText(String.format("%s not found", ((SecomNotFoundException) ex).getIdentifier()));
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            acknowledgementResponse.setResponseText(ex.getMessage());
        }

        // Return the response
        return ResponseEntity.status(httpStatus)
                .body(acknowledgementResponse);
    }

}
