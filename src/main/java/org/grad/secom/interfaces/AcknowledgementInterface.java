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

import org.grad.secom.exceptions.*;
import org.grad.secom.models.AcknowledgementObject;
import org.grad.secom.models.AcknowledgementResponseObject;
import org.grad.secom.models.enums.SECOM_ResponseCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;

/**
 * The SECOM Acknowledgement Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface AcknowledgementInterface extends GenericInterface {

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
     * @param acknowledgementObject  the acknowledgement object
     * @return the acknowledgement response object
     */
    @PostMapping(ACKNOWLEDGMENT_INTERFACE_PATH)
    ResponseEntity<AcknowledgementResponseObject> acknowledgment(@Valid @RequestBody AcknowledgementObject acknowledgementObject);

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
    default ResponseEntity<AcknowledgementResponseObject> handleAcknowledgementInterfaceExceptions(Exception ex,
                                                                                                   HttpServletRequest request,
                                                                                                   HttpServletResponse response) {
        // Create the acknowledgment response
        HttpStatus httpStatus;
        AcknowledgementResponseObject acknowledgementResponseObject = new AcknowledgementResponseObject();

        // Handle according to the exception type
        if (ex instanceof SecomNotFoundException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            acknowledgementResponseObject.setSECOM_ResponseCode(null);
            acknowledgementResponseObject.setResponseText("Bad Request");
        } else if (ex instanceof ValidationException || ex instanceof MethodArgumentTypeMismatchException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            acknowledgementResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.MISSING_REQUIRED_DATA_FOR_SERVICE);
            acknowledgementResponseObject.setResponseText("Missing required data for the service");
        } else if (ex instanceof SecomSignatureVerificationException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            acknowledgementResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.FAILED_SIGNATURE_VERIFICATION);
            acknowledgementResponseObject.setResponseText("Failed signature verification");
        } else if (ex instanceof SecomInvalidCertificateException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            acknowledgementResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.INVALID_CERTIFICATE);
            acknowledgementResponseObject.setResponseText("Invalid Certificate");
        } else if (ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            acknowledgementResponseObject.setResponseText("Not authorized to upload ACK");
        } else {
            httpStatus = this.handleCommonExceptionResponseCode(ex);
            acknowledgementResponseObject.setSECOM_ResponseCode(null);

            // Divert from the common practice a little
            httpStatus = httpStatus == HttpStatus.INTERNAL_SERVER_ERROR ? HttpStatus.BAD_REQUEST : httpStatus;
            acknowledgementResponseObject.setResponseText(httpStatus == HttpStatus.FORBIDDEN ?
                    "Not authorized to upload ACK" : httpStatus.getReasonPhrase());
        }

        // And send the error response back
        return ResponseEntity.status(httpStatus)
                .body(acknowledgementResponseObject);
    }

}
