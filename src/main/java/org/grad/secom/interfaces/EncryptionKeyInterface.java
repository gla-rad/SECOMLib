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
import org.grad.secom.exceptions.SecomValidationException;
import org.grad.secom.models.EncryptionKeyObject;
import org.grad.secom.models.EncryptionKeyResponseObject;
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
 * The SECOM Encryption Key Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface EncryptionKeyInterface extends GenericInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String ENCRYPTION_KEY_INTERFACE_PATH = "/v1/encryptionkey";

    /**
     * POST /v1/encryptionkey : The purpose of the interface is to exchange a
     * temporary secret key. This operation is used to upload (push) an
     * encrypted secret key to a consumer.
     *
     * @return the encryption key response object
     */
    @PostMapping(ENCRYPTION_KEY_INTERFACE_PATH)
    ResponseEntity<EncryptionKeyResponseObject> encryptionKey(@Valid @RequestBody EncryptionKeyObject encryptionKeyObject);

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
    default ResponseEntity<Object> handleEncryptionInterfaceExceptions(Exception ex,
                                                                       HttpServletRequest request,
                                                                       HttpServletResponse response) {

        // Create the encryption key response
        HttpStatus httpStatus;
        EncryptionKeyResponseObject encryptionKeyResponseObject = new EncryptionKeyResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException || ex instanceof ValidationException || ex instanceof MethodArgumentTypeMismatchException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            encryptionKeyResponseObject.setResponseText("Bad Request");
        } else {
            httpStatus = this.handleCommonExceptionResponseCode(ex);
            encryptionKeyResponseObject.setResponseText(httpStatus.getReasonPhrase());
        }

        // And send the error response back
        return ResponseEntity.status(httpStatus)
                .body(encryptionKeyResponseObject);
    }

}
