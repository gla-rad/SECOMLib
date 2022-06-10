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

package org.grad.secom.interfaces.springboot;

import org.grad.secom.exceptions.*;
import org.grad.secom.models.UploadLinkObject;
import org.grad.secom.models.UploadLinkResponseObject;
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
 * The SECOM Upload Link Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface UploadLinkInterface extends GenericInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String UPLOAD_LINK_INTERFACE_PATH = "/v1/object/link";

    /**
     * POST /v1/object/link : The REST operation POST /object/link. The
     * interface shall be used for uploading (pushing) a link to data to a
     * consumer.
     *
     * @param uploadLinkObject  the upload link object
     * @return the upload link response object
     */
    @PostMapping(UPLOAD_LINK_INTERFACE_PATH)
    ResponseEntity<UploadLinkResponseObject> uploadLink(@RequestBody @Valid UploadLinkObject uploadLinkObject);

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
    default ResponseEntity<UploadLinkResponseObject> handleUploadLinkInterfaceExceptions(Exception ex,
                                                                                         HttpServletRequest request,
                                                                                         HttpServletResponse response) {

        // Create the upload link response
        HttpStatus httpStatus;
        UploadLinkResponseObject uploadResponseObject = new UploadLinkResponseObject();

        // Handle according to the exception type
        if(ex instanceof  SecomValidationException || ex instanceof ValidationException || ex instanceof MethodArgumentTypeMismatchException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            uploadResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.MISSING_REQUIRED_DATA_FOR_SERVICE);
            uploadResponseObject.setResponseText("Missing required data for the service");
        } else if(ex instanceof SecomSignatureVerificationException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            uploadResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.FAILED_SIGNATURE_VERIFICATION);
            uploadResponseObject.setResponseText("Failed signature verification");
        } else if(ex instanceof SecomInvalidCertificateException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            uploadResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.INVALID_CERTIFICATE);
            uploadResponseObject.setResponseText("Invalid Certificate");
        } else if(ex instanceof SecomSchemaValidationException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            uploadResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.SCHEMA_VALIDATION_ERROR);
            uploadResponseObject.setResponseText("Schema validation error");
        } else {
            httpStatus = this.handleCommonExceptionResponseCode(ex);
            uploadResponseObject.setSECOM_ResponseCode(null);
            uploadResponseObject.setResponseText(httpStatus.getReasonPhrase());
        }

        // And send the error response back
        return ResponseEntity.status(httpStatus)
                .body(uploadResponseObject);
    }

}
