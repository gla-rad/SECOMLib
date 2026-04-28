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

import com.fasterxml.jackson.databind.JsonMappingException;
import org.grad.secomv2.core.base.SecomConstants;
import org.grad.secomv2.core.exceptions.SecomInvalidCertificateException;
import org.grad.secomv2.core.exceptions.SecomSchemaValidationException;
import org.grad.secomv2.core.exceptions.SecomSignatureVerificationException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.UploadObject;
import org.grad.secomv2.core.models.UploadResponseObject;
import org.grad.secomv2.core.models.enums.SECOM_ResponseCodeEnum;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
 * The SECOM Upload Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */

@RequestMapping("/api/secom/")
public interface UploadServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String UPLOAD_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/object";

    /**
     * POST /v2/object : The interface shall be used for uploading (pushing)
     * data to a consumer. The operation expects one single data object and
     * its metadata.
     *
     * @param uploadObject  the upload object
     * @return the upload response object
     */
    @PostMapping(path = UPLOAD_INTERFACE_PATH,
                consumes = { MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<UploadResponseObject> upload(@Valid @RequestBody UploadObject uploadObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handleUploadInterfaceExceptions(Exception ex,
                                                                  HttpServletRequest request) {

        // Create the upload response
        HttpStatus httpStatus;
        UploadResponseObject uploadResponseObject = new UploadResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JsonMappingException
                || ex instanceof HttpClientErrorException.NotFound) {
            uploadResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.MISSING_REQUIRED_DATA_FOR_SERVICE);
            uploadResponseObject.setMessage("Missing required data for the service");
            httpStatus = HttpStatus.BAD_REQUEST;

        } else if(ex instanceof SecomSignatureVerificationException) {
            uploadResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.FAILED_SIGNATURE_VERIFICATION);
            uploadResponseObject.setMessage("Failed signature verification");
            httpStatus = HttpStatus.BAD_REQUEST;

        } else if(ex instanceof SecomInvalidCertificateException) {
            uploadResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.INVALID_CERTIFICATE);
            uploadResponseObject.setMessage("Invalid Certificate");
            httpStatus = HttpStatus.BAD_REQUEST;

        } else if(ex instanceof SecomSchemaValidationException) {
            uploadResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.SCHEMA_VALIDATION_ERROR);
            uploadResponseObject.setMessage("Schema validation error");
            httpStatus = HttpStatus.BAD_REQUEST;

        } else {
            httpStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            uploadResponseObject.setSECOM_ResponseCode(null);
            uploadResponseObject.setMessage(httpStatus.getReasonPhrase());

        }

        return ResponseEntity
                .status(httpStatus)
                .body(uploadResponseObject);
    }

}
