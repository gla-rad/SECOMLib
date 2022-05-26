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
import org.grad.secom.models.UploadRequest;
import org.grad.secom.models.UploadResponse;
import org.grad.secom.models.enums.ResponseCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The SECOM Upload Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface UploadInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String UPLOAD_INTERFACE_PATH = "/v1/object";

    /**
     * POST /v1/object : The interface shall be used for uploading (pushing)
     * data to a consumer. The operation expects one single data object and
     * its metadata.
     *
     * @param uploadRequest  the upload object
     * @return the upload response object
     */
    @PostMapping(UPLOAD_INTERFACE_PATH)
    ResponseEntity<UploadResponse> upload(@RequestBody UploadRequest uploadRequest);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    @ExceptionHandler({SecomGenericException.class, MethodArgumentConversionNotSupportedException.class})
    default ResponseEntity<UploadResponse> handleUploadInterfaceExceptions(Exception ex,
                                                                           HttpServletRequest request,
                                                                           HttpServletResponse response) {

        // Create the upload response
        HttpStatus httpStatus;
        UploadResponse uploadResponse = new UploadResponse();

        // Handle according to the exception type
        if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            uploadResponse.setResponseCode(ResponseCodeEnum.NO_ERROR);
            uploadResponse.setResponseText("Not authorized to upload");
        } else if(ex instanceof HttpRequestMethodNotSupportedException) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
            uploadResponse.setResponseCode(ResponseCodeEnum.NO_ERROR);
            uploadResponse.setResponseText("Method not allowed");
        } else if(ex instanceof SecomNotImplementedException) {
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
            uploadResponse.setResponseCode(ResponseCodeEnum.NO_ERROR);
            uploadResponse.setResponseText("Not implemented");
        } else if(ex instanceof SecomXmlValidationException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            uploadResponse.setResponseCode(ResponseCodeEnum.XML_SCHEMA_VALIDATION_ERROR);
            uploadResponse.setResponseText("Human readable XML Schema validation error");
        } else if(ex instanceof SecomNotFoundException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            uploadResponse.setResponseCode(ResponseCodeEnum.MISSING_REQUIRED_DATA_FOR_SERVICE);
            uploadResponse.setResponseText("Human readable data that is missing");
        } else if(ex instanceof MethodArgumentConversionNotSupportedException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            uploadResponse.setResponseCode(ResponseCodeEnum.UNKNOWN_DATA_TYPE_OR_VERSION);
            uploadResponse.setResponseText("Unknown data type or version");
        } else if(ex instanceof SecomSignatureVerificationException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            uploadResponse.setResponseCode(ResponseCodeEnum.FAILED_SIGNATURE_VERIFICATION);
            uploadResponse.setResponseText("Failed signature verification");
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            uploadResponse.setResponseText(ex.getMessage());
        }

        // Return the response
        return ResponseEntity.status(httpStatus)
                .body(uploadResponse);
    }

}
