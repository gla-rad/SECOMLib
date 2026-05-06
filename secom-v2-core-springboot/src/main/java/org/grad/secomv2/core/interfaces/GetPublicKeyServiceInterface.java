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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ValidationException;
import org.grad.secomv2.core.base.SecomConstants;
import org.grad.secomv2.core.exceptions.SecomNotAuthorisedException;
import org.grad.secomv2.core.exceptions.SecomNotFoundException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.CapabilityResponseObject;
import org.grad.secomv2.core.models.PublicKeyResponseObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;


/**
 * The SECOM GET Public Key Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@RequestMapping("/api/secom/")
public interface GetPublicKeyServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String GET_PUBLIC_KEY_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/publicKey";

    /**
     * GET /v2/publicKey : This operation receives a get request for a public key.
     * If authorized, the key is sent back in the response. It is up to the service
     * provider to apply relevant authorization procedure and access control to information.
     *
     * @return the public key object
     */
    @GetMapping(path = GET_PUBLIC_KEY_INTERFACE_PATH,
                produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<PublicKeyResponseObject> getPublicKey(@RequestParam("dataProtection") Boolean dataProtection,
                                         @RequestParam("certificateThumbprint") @Pattern(regexp = "^[A-Fa-f0-9]{40,64}$") @Parameter(schema = @Schema(description = "Claimed Thumbprint for Signed Key (X.509 Certificate)")) String certificateThumbprint);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handleGetPublicKeyExceptions(Exception ex,
                                                               HttpServletRequest request) {
        // Create the capability response
        HttpStatus httpStatus;
        PublicKeyResponseObject publicKeyResponseObject = new PublicKeyResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JacksonException
                || ex instanceof HttpClientErrorException.NotFound) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
        } else if(ex instanceof SecomNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            httpStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
        }

        return ResponseEntity
                .status(httpStatus)
                .body(publicKeyResponseObject);

    }

}
