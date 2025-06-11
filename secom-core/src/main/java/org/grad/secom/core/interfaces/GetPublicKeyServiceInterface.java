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


package org.grad.secom.core.interfaces;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.grad.secom.core.models.CapabilityResponseObject;
import org.grad.secom.core.models.PublicKeyResponseObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.grad.secom.core.base.SecomConstants.SECOM_VERSION;

/**
 * The SECOM GET Public Key Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
public interface GetPublicKeyServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String GET_PUBLIC_KEY_INTERFACE_PATH = "/" + SECOM_VERSION + "/publicKey";

    /**
     * GET /v2/publicKey : This operation receives a get request for a public key.
     * If authorized, the key is sent back in the response. It is up to the service
     * provider to apply relevant authorization procedure and access control to information.
     *
     * @return the public key object
     */
    @Path(GET_PUBLIC_KEY_INTERFACE_PATH)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    PublicKeyResponseObject getPublicKey(@QueryParam("dataProtection") Boolean dataProtection,
                                         @QueryParam("certificateThumbprint") @Pattern(regexp = "^[A-Fa-f0-9]{40,64}$") @Parameter(schema = @Schema(description = "Claimed Thumbprint for Signed Key (X.509 Certificate)")) String certificateThumbprint);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleGetPublicKeyExceptions(Exception ex,
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
