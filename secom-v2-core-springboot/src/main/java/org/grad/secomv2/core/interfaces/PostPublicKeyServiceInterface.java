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
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.grad.secomv2.core.exceptions.SecomInvalidCertificateException;
import org.grad.secomv2.core.exceptions.SecomSchemaValidationException;
import org.grad.secomv2.core.exceptions.SecomSignatureVerificationException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.CapabilityResponseObject;
import org.grad.secomv2.core.models.PublicKeyRequestObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.grad.secomv2.core.models.PublicKeyResponseObject;
import org.grad.secomv2.core.models.UploadLinkResponseObject;
import org.grad.secomv2.core.models.enums.SECOM_ResponseCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import static org.grad.secomv2.core.interfaces.GetPublicKeyServiceInterface.GET_PUBLIC_KEY_INTERFACE_PATH;

/**
 * The SECOM POST Public Key Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@RequestMapping("/api/secom/")
public interface PostPublicKeyServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String POST_PUBLIC_KEY_INTERFACE_PATH = GET_PUBLIC_KEY_INTERFACE_PATH;

    /**
     * POST /v2/publicKey : The purpose of this interface is to receive a public key.
     * If authorized, the key is sent back in the response. It is up to the service
     * provider to apply relevant authorization procedure and access control to information.
     *
     * @return the public key object
     */
    @PostMapping(path = POST_PUBLIC_KEY_INTERFACE_PATH,
                consumes = { MediaType.APPLICATION_JSON_VALUE },
                produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<PublicKeyResponseObject> postPublicKey(@Valid @RequestBody PublicKeyRequestObject publicKeyRequestObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handlePostPublicKeyInterfaceExceptions(Exception ex,
                                                           HttpServletRequest request) {
        // Create the upload link response
        HttpStatus httpStatus;
        PublicKeyResponseObject publicKeyResponseObject = new PublicKeyResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JacksonException
                || ex instanceof HttpClientErrorException.NotFound) {
            publicKeyResponseObject.setMessage("Missing required data for the service");
            httpStatus = HttpStatus.BAD_REQUEST;

        } else if(ex instanceof SecomSignatureVerificationException) {
            publicKeyResponseObject.setMessage("Failed signature verification");
            httpStatus = HttpStatus.BAD_REQUEST;

        } else if(ex instanceof SecomInvalidCertificateException) {
            publicKeyResponseObject.setMessage("Invalid Certificate");
            httpStatus = HttpStatus.BAD_REQUEST;

        } else if(ex instanceof SecomSchemaValidationException) {
            publicKeyResponseObject.setMessage("Schema validation error");
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            httpStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            publicKeyResponseObject.setMessage(httpStatus.getReasonPhrase());
        }

        return ResponseEntity
                .status(httpStatus)
                .body(publicKeyResponseObject);

    }

}
