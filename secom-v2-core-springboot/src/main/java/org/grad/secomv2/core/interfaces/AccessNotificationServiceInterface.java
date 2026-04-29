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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.grad.secomv2.core.base.SecomConstants;
import org.grad.secomv2.core.exceptions.SecomNotFoundException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.AccessNotificationObject;
import org.grad.secomv2.core.models.AccessNotificationResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.MediaType;


/**
 * The SECOM Access Notification Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@RequestMapping("/api/secom/")
public interface AccessNotificationServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String ACCESS_NOTIFICATION_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION +  "/access/notification";

    /**
     * POST /v2/access/notification : Result from Access Request performed on a
     * service instance shall be sent asynchronous through this client
     * interface.
     *
     * @param accessNotificationObject  the access notification object
     * @return the access notification response object
     */
    @PostMapping(path = ACCESS_NOTIFICATION_INTERFACE_PATH,
                    consumes= { MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.APPLICATION_JSON_VALUE })
        ResponseEntity<AccessNotificationResponseObject> accessNotification(@Valid @RequestBody AccessNotificationObject accessNotificationObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handleAccessNotificationInterfaceExceptions(Exception ex, HttpServletRequest request) {

        // Create the access notification response
        HttpStatus httpStatus;
        AccessNotificationResponseObject accessNotificationResponseObject = new AccessNotificationResponseObject();

        // Handle according to the exception type
        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JacksonException
                || ex instanceof SecomNotFoundException
                || ex instanceof HttpClientErrorException.NotFound) {
            accessNotificationResponseObject.setMessage("Bad Request");
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            httpStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            accessNotificationResponseObject.setMessage(httpStatus.getReasonPhrase());
        }

        return ResponseEntity
                .status(httpStatus)
                .body(accessNotificationResponseObject);

    }

}
