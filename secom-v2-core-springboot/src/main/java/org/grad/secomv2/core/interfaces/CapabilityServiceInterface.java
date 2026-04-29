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

import org.grad.secomv2.core.base.SecomConstants;
import org.grad.secomv2.core.models.CapabilityResponseObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The SECOM Capability Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@RequestMapping("/api/secom/")
public interface CapabilityServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String CAPABILITY_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/capability";

    /**
     * GET /v2/capability : The purpose of the interface is to provide a dynamic
     * method to ask a service instance at runtime what interfaces are
     * accessible, and what payload formats and version are valid.
     *
     * @return the capability response object
     */
    @GetMapping(path = CAPABILITY_INTERFACE_PATH,
                produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<CapabilityResponseObject> capability();

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handleCapabilityInterfaceExceptions(Exception ex,
                                                              HttpServletRequest request) {
        // Create the capability response
        CapabilityResponseObject capabilityResponseObject = new CapabilityResponseObject();

        // Handle according to the exception type
        HttpStatus httpStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);

        // And send the error response back
        return ResponseEntity
                .status(httpStatus)
                .body(capabilityResponseObject);
    }

}
