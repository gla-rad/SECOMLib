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
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.EncryptionKeyObject;
import org.grad.secomv2.core.models.EncryptionKeyResponseObject;

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
 * The SECOM Encryption Key Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@RequestMapping("/api/secom/")
public interface EncryptionKeyServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String ENCRYPTION_KEY_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/encryptionkey/upload";

    /**
     * POST /v2/encryptionkey/upload : The purpose of the interface is to exchange a
     * temporary secret key. This operation is used to generate and return an
     * encrypted secret key to a consumer.
     *
     * @return the encryption key response object
     */
    @PostMapping(path = ENCRYPTION_KEY_INTERFACE_PATH,
                consumes = { MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<EncryptionKeyResponseObject> encryptionKey(@Valid @RequestBody EncryptionKeyObject encryptionKeyObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handleEncryptionInterfaceExceptions(Exception ex,
                                                                      HttpServletRequest request) {

        // Create the encryption key response
        HttpStatus httpStatus;
        EncryptionKeyResponseObject encryptionKeyResponseObject = new EncryptionKeyResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JsonMappingException
                || ex instanceof HttpClientErrorException.NotFound) {
            encryptionKeyResponseObject.setMessage("Bad Request");
            httpStatus = HttpStatus.BAD_REQUEST;

        } else {
            httpStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            encryptionKeyResponseObject.setMessage(httpStatus.getReasonPhrase());

        }

        return ResponseEntity
                .status(httpStatus)
                .body(encryptionKeyResponseObject);
    }

}
