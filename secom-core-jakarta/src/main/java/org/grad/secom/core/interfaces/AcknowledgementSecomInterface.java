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

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.grad.secom.core.exceptions.*;
import org.grad.secom.core.models.AcknowledgementObject;
import org.grad.secom.core.models.AcknowledgementResponseObject;
import org.grad.secom.core.models.enums.SECOM_ResponseCodeEnum;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * The SECOM Acknowledgement Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface AcknowledgementSecomInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String ACKNOWLEDGMENT_INTERFACE_PATH = "/v1/acknowledgement";

    /**
     * POST /v1/acknowledgement : During upload of information, an
     * acknowledgement can be requested which is expected to be received when
     * the uploaded message has been delivered to the end system (technical
     * acknowledgement), and an acknowledgement when the message has been opened
     * (read) by the end user (operational acknowledgement). The acknowledgement
     * contains a reference to object delivered.
     *
     * @param acknowledgementObject  the acknowledgement object
     * @return the acknowledgement response object
     */
    @Path(ACKNOWLEDGMENT_INTERFACE_PATH)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    AcknowledgementResponseObject acknowledgment(@Valid AcknowledgementObject acknowledgementObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleAcknowledgementInterfaceExceptions(Exception ex,
                                                             HttpServletRequest request,
                                                             HttpServletResponse response) {
        // Create the acknowledgment response
        Response.Status responseStatus;
        AcknowledgementResponseObject acknowledgementResponseObject = new AcknowledgementResponseObject();

        // Handle according to the exception type
        if (ex instanceof SecomNotFoundException) {
            responseStatus = Response.Status.BAD_REQUEST;
            acknowledgementResponseObject.setSECOM_ResponseCode(null);
            acknowledgementResponseObject.setResponseText("Bad Request");
        } else if (ex instanceof SecomValidationException || ex.getCause() instanceof SecomValidationException || ex instanceof ValidationException || ex instanceof InvalidFormatException) {
            responseStatus = Response.Status.BAD_REQUEST;
            acknowledgementResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.MISSING_REQUIRED_DATA_FOR_SERVICE);
            acknowledgementResponseObject.setResponseText("Missing required data for the service");
        } else if (ex instanceof SecomSignatureVerificationException) {
            responseStatus = Response.Status.BAD_REQUEST;
            acknowledgementResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.FAILED_SIGNATURE_VERIFICATION);
            acknowledgementResponseObject.setResponseText("Failed signature verification");
        } else if (ex instanceof SecomInvalidCertificateException) {
            responseStatus = Response.Status.BAD_REQUEST;
            acknowledgementResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.INVALID_CERTIFICATE);
            acknowledgementResponseObject.setResponseText("Invalid Certificate");
        } else if (ex instanceof SecomNotAuthorisedException) {
            responseStatus = Response.Status.FORBIDDEN;
            acknowledgementResponseObject.setResponseText("Not authorized to upload ACK");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            acknowledgementResponseObject.setSECOM_ResponseCode(null);

            // Divert from the common practice a little
            responseStatus = responseStatus == Response.Status.INTERNAL_SERVER_ERROR ? Response.Status.BAD_REQUEST : responseStatus;
            acknowledgementResponseObject.setResponseText(responseStatus == Response.Status.FORBIDDEN ?
                    "Not authorized to upload ACK" : responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(acknowledgementResponseObject)
                .build();
    }

}
