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

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.grad.secom.core.base.SecomConstants;
import org.grad.secom.core.exceptions.SecomInvalidCertificateException;
import org.grad.secom.core.exceptions.SecomSchemaValidationException;
import org.grad.secom.core.exceptions.SecomSignatureVerificationException;
import org.grad.secom.core.exceptions.SecomValidationException;
import org.grad.secom.core.models.UploadObject;
import org.grad.secom.core.models.UploadResponseObject;
import org.grad.secom.core.models.enums.SECOM_ResponseCodeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The SECOM Upload Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface UploadSecomInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String UPLOAD_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/object";

    /**
     * POST /v1/object : The interface shall be used for uploading (pushing)
     * data to a consumer. The operation expects one single data object and
     * its metadata.
     *
     * @param uploadObject  the upload object
     * @return the upload response object
     */
    @Path(UPLOAD_INTERFACE_PATH)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UploadResponseObject upload(@Valid UploadObject uploadObject);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleUploadInterfaceExceptions(Exception ex,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {

        // Create the upload response
        Response.Status responseStatus;
        UploadResponseObject uploadResponseObject = new UploadResponseObject();

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
