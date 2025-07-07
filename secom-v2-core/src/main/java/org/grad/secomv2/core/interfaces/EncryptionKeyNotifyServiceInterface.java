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
import org.grad.secomv2.core.exceptions.SecomNotAuthorisedException;
import org.grad.secomv2.core.models.EncryptionKeyResponseObject;
import org.grad.secomv2.core.models.EncryptionKeyNotificationObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The SECOM Encryption Key Notify Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface EncryptionKeyNotifyServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Notify Endpoint Path.
     */
    String ENCRYPTION_KEY_NOTIFY_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/encryptionkey/notify";

    /**
     * POST /v2/encryptionkey/notify : The purpose of the interface is to
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
            encryptionKeyResponseObject.setMessage("Not authorized");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            encryptionKeyResponseObject.setMessage(responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(encryptionKeyResponseObject)
                .build();
    }

}
