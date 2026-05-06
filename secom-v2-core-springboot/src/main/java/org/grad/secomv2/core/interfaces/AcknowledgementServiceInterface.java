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
import org.grad.secomv2.core.exceptions.*;
import org.grad.secomv2.core.models.AcknowledgementObject;
import org.grad.secomv2.core.models.AcknowledgementResponseObject;
import org.grad.secomv2.core.models.enums.SECOM_ResponseCodeEnum;

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
 * The SECOM Acknowledgement Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@RequestMapping("/api/secom/")
public interface AcknowledgementServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String ACKNOWLEDGMENT_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/acknowledgement";

    /**
     * POST /v2/acknowledgement : During upload of information, an
     * acknowledgement can be requested which is expected to be received when
     * the uploaded message has been delivered to the end system (technical
     * acknowledgement), and an acknowledgement when the message has been opened
     * (read) by the end user (operational acknowledgement). The acknowledgement
     * contains a reference to object delivered.
     *
     * @param acknowledgementObject  the acknowledgement object
     * @return the acknowledgement response object
     */
    @PostMapping(path = ACKNOWLEDGMENT_INTERFACE_PATH,
                consumes = { MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<AcknowledgementResponseObject> acknowledgment(@Valid @RequestBody AcknowledgementObject acknowledgementObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handleAcknowledgementInterfaceExceptions(Exception ex,
                                                                   HttpServletRequest request) {
        // Create the acknowledgment response
        HttpStatus httpStatus;
        AcknowledgementResponseObject acknowledgementResponseObject = new AcknowledgementResponseObject();

        // Handle according to the exception type
        if (ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JacksonException
                || ex instanceof SecomNotFoundException
                || ex instanceof HttpClientErrorException.NotFound) {
            httpStatus = HttpStatus.BAD_REQUEST;
            acknowledgementResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.MISSING_REQUIRED_DATA_FOR_SERVICE);
            acknowledgementResponseObject.setMessage("Missing required data for the service");
        } else if (ex instanceof SecomSignatureVerificationException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            acknowledgementResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.FAILED_SIGNATURE_VERIFICATION);
            acknowledgementResponseObject.setMessage("Failed signature verification");
        } else if (ex instanceof SecomInvalidCertificateException) {
            httpStatus = HttpStatus.FORBIDDEN;
            acknowledgementResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.INVALID_CERTIFICATE);
            acknowledgementResponseObject.setMessage("Invalid Certificate");
        } else if (ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            acknowledgementResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.INVALID_CERTIFICATE);
            acknowledgementResponseObject.setMessage("Not authorized to upload ACK");
        } else {
            httpStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);

            // Divert from the common practice a little
            httpStatus = httpStatus == HttpStatus.INTERNAL_SERVER_ERROR ?
                    HttpStatus.BAD_REQUEST : httpStatus;
            var responseMessage = httpStatus == HttpStatus.FORBIDDEN ?
                    "Not authorized to upload ACK" : httpStatus.getReasonPhrase();

            // And populate the acknowledgement response object
            acknowledgementResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.SCHEMA_VALIDATION_ERROR);
            acknowledgementResponseObject.setMessage(responseMessage);
        }

        return ResponseEntity
                .status(httpStatus)
                .body(acknowledgementResponseObject);

    }

}
