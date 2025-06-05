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

package org.grad.secom.core.interfaces;

import org.grad.secom.core.exceptions.SecomNotAuthorisedException;
import org.grad.secom.core.models.EncryptionKeyResponseObject;
import org.grad.secom.core.models.EncryptionKeyNotificationObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * The SECOM Encryption Key Notify Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface EncryptionKeyNotifySecomInterface extends GenericSecomInterface {

    /**
     * The Interface Notify Endpoint Path.
     */
    String ENCRYPTION_KEY_NOTIFY_INTERFACE_PATH = "/v1/encryptionkey/notify";

    /**
     * POST /v1/encryptionkey/notify : The purpose of the interface is to
     * exchange a temporary secret key. This operation enables a consumer to
     * request an encrypted secret key from a producer by providing a reference
     * to the encrypted data and a public certificate for symmetric key
     * derivation used to protect the temporary encryption key during transfer.
     *
     * @return the encryption key response object
     */
    @Path(ENCRYPTION_KEY_NOTIFY_INTERFACE_PATH)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    EncryptionKeyResponseObject encryptionKeyNotify(@Valid EncryptionKeyNotificationObject encryptionKeyNotificationObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleEncryptionKeyNotifyInterfaceExceptions(Exception ex,
                                                                 HttpServletRequest request,
                                                                 HttpServletResponse response) {

        // Create the encryption key response
        Response.Status responseStatus;
        EncryptionKeyResponseObject encryptionKeyResponseObject = new EncryptionKeyResponseObject();

        // Handle according to the exception type
       if (ex instanceof SecomNotAuthorisedException) {
            responseStatus = Response.Status.BAD_REQUEST;
            encryptionKeyResponseObject.setResponseText("Not authorized");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            encryptionKeyResponseObject.setResponseText(responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(encryptionKeyResponseObject)
                .build();
    }

}
