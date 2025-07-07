/*
 * Copyright (c) 2025 GLA Research and Development Directorate
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The SECOM Capability Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
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
    @Path(CAPABILITY_INTERFACE_PATH)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    CapabilityResponseObject capability();

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleCapabilityInterfaceExceptions(Exception ex,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        // Create the capability response
        Response.Status responseStatus;
        CapabilityResponseObject capabilityResponseObject = new CapabilityResponseObject();

        // Handle according to the exception type
        responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);

        // And send the error response back
        return Response.status(responseStatus)
                .entity(capabilityResponseObject)
                .build();
    }

}
