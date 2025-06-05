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

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.ws.rs.*;
import org.grad.secom.core.exceptions.SecomInvalidCertificateException;
import org.grad.secom.core.exceptions.SecomSchemaValidationException;
import org.grad.secom.core.exceptions.SecomSignatureVerificationException;
import org.grad.secom.core.exceptions.SecomValidationException;
import org.grad.secom.core.models.UploadLinkObject;
import org.grad.secom.core.models.UploadLinkResponseObject;
import org.grad.secom.core.models.enums.SECOM_ResponseCodeEnum;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * The SECOM Upload Link Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface UploadLinkSecomInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String UPLOAD_LINK_INTERFACE_PATH = "/v1/object/link";

    /**
     * POST /v1/object/link : The REST operation POST /object/link. The
     * interface shall be used for uploading (pushing) a link to data to a
     * consumer.
     *
     * @param uploadLinkObject  the upload link object
     * @return the upload link response object
     */
    @Path(UPLOAD_LINK_INTERFACE_PATH)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UploadLinkResponseObject uploadLink(@Valid UploadLinkObject uploadLinkObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleUploadLinkInterfaceExceptions(Exception ex,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {

        // Create the upload link response
        Response.Status responseStatus;
        UploadLinkResponseObject uploadResponseObject = new UploadLinkResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JsonMappingException
                || ex instanceof NotFoundException) {
            responseStatus = Response.Status.BAD_REQUEST;
            uploadResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.MISSING_REQUIRED_DATA_FOR_SERVICE);
            uploadResponseObject.setResponseText("Missing required data for the service");
        } else if(ex instanceof SecomSignatureVerificationException) {
            responseStatus = Response.Status.BAD_REQUEST;
            uploadResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.FAILED_SIGNATURE_VERIFICATION);
            uploadResponseObject.setResponseText("Failed signature verification");
        } else if(ex instanceof SecomInvalidCertificateException) {
            responseStatus = Response.Status.BAD_REQUEST;
            uploadResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.INVALID_CERTIFICATE);
            uploadResponseObject.setResponseText("Invalid Certificate");
        } else if(ex instanceof SecomSchemaValidationException) {
            responseStatus = Response.Status.BAD_REQUEST;
            uploadResponseObject.setSECOM_ResponseCode(SECOM_ResponseCodeEnum.SCHEMA_VALIDATION_ERROR);
            uploadResponseObject.setResponseText("Schema validation error");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            uploadResponseObject.setSECOM_ResponseCode(null);
            uploadResponseObject.setResponseText(responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(uploadResponseObject)
                .build();
    }

}
