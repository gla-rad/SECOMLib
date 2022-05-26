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

package org.grad.secom.interfaces;

import org.grad.secom.exceptions.*;
import org.grad.secom.models.UploadLinkRequest;
import org.grad.secom.models.UploadLinkResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;

/**
 * The SECOM Upload Link Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface UploadLinkInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String UPLOAD_LINK_INTERFACE_PATH = "/v1/object/link";

    /**
     * POST /v1/object/link : The REST operation POST /object/link. The
     * interface shall be used for uploading (pushing) a link to data to a
     * consumer.
     *
     * @param uploadLinkRequest  the upload link object
     * @return the upload link response object
     */
    @PostMapping(UPLOAD_LINK_INTERFACE_PATH)
    ResponseEntity<UploadLinkResponse> uploadLink(@RequestBody UploadLinkRequest uploadLinkRequest);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    @ExceptionHandler({SecomGenericException.class, HttpRequestMethodNotSupportedException.class, MalformedURLException.class})
    default ResponseEntity<UploadLinkResponse> handleUploadLinkInterfaceExceptions(Exception ex,
                                                                                   HttpServletRequest request,
                                                                                   HttpServletResponse response) {

        // Create the upload response
        HttpStatus httpStatus;
        UploadLinkResponse uploadLinkResponse = new UploadLinkResponse();

        // Handle according to the exception type
        if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            uploadLinkResponse.setResponseText("Not authorized to upload link");
        } else if(ex instanceof HttpRequestMethodNotSupportedException) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
            uploadLinkResponse.setResponseText("Method not allowed");
        } else if(ex instanceof SecomNotImplementedException) {
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
            uploadLinkResponse.setResponseText("Not implemented");
        } else if(ex instanceof MalformedURLException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            uploadLinkResponse.setResponseText("Link is not a valid URL");
        } else if(ex instanceof MethodArgumentConversionNotSupportedException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            uploadLinkResponse.setResponseText("Unknown data type or version");
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            uploadLinkResponse.setResponseText(ex.getMessage());
        }

        // Return the response
        return ResponseEntity.status(httpStatus)
                .body(uploadLinkResponse);
    }

}
