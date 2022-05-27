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
import org.grad.secom.models.EncryptionKeyNotificationObject;
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
import javax.validation.ValidationException;

/**
 * The SECOM Encryption Key Notify Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface EncryptionKeyNotifyInterface extends GenericInterface {

    /**
     * The Interface Notify Endpoint Path.
     */
    public static final String ENCRYPTION_KEY_NOTIFY_INTERFACE_PATH = "/v1/encryptionkey/notify";

    /**
     * POST /v1/encryptionkey/notify : The purpose of the interface is to
     * exchange a temporary secret key. This operation enables a consumer to
     * request an encrypted secret key from a producer by providing a reference
     * to the encrypted data and a public certificate for symmetric key
     * derivation used to protect the temporary encryption key during transfer.
     *
     * @return the encryption key response object
     */
    @PostMapping(ENCRYPTION_KEY_NOTIFY_INTERFACE_PATH)
    ResponseEntity<EncryptionKeyResponseObject> encryptionKeyNotify(@RequestBody EncryptionKeyNotificationObject encryptionKeyNotificationObject);

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
       if (ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            encryptionKeyResponseObject.setResponseText("Not authorized");
        } else {
            httpStatus = this.handleCommonExceptionResponseCode(ex);
            encryptionKeyResponseObject.setResponseText(httpStatus.getReasonPhrase());
        }

        // And send the error response back
        return ResponseEntity.status(httpStatus)
                .body(encryptionKeyResponseObject);
    }

}
