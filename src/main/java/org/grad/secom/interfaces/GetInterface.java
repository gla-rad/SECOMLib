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
import org.grad.secom.models.GetResponse;
import org.grad.secom.models.enums.AreaNameEnum;
import org.grad.secom.models.enums.DataTypeEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The SECOM Get Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface GetInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String GET_INTERFACE_PATH = "/v1/object";

    /**
     * GET /v1/object : The Get interface is used for pulling information from a
     * service provider. The owner of the information (provider) is responsible
     * for the authorization procedure before returning information.
     *
     * @param dataReference the object data reference
     * @param dataType the object data type
     * @param productSpecification the object product specification
     * @param geometry the object geometry
     * @param areaName the object area name
     * @param unlocode the object UNLOCODE
     * @param fromTime the object from time
     * @param toTime the object to time
     * @param pageable the pageable information
     * @return the object information
     */
    @GetMapping(GET_INTERFACE_PATH)
    ResponseEntity<GetResponse> get(@RequestParam(value = "dataReference", required = false) String dataReference,
                                    @RequestParam(value = "dataType", required = false) DataTypeEnum dataType,
                                    @RequestParam(value = "productSpecification", required = false) String productSpecification,
                                    @RequestParam(value = "geometry", required = false) String geometry,
                                    @RequestParam(value = "areaName", required = false) @Pattern(regexp = "(\\d+(,\\d+)*)?") List<AreaNameEnum> areaName,
                                    @RequestParam(value = "unlocode", required = false) @Pattern(regexp = "[a-z]{5}") String unlocode,
                                    @RequestParam(value = "fromTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromTime,
                                    @RequestParam(value = "toTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toTime,
                                    Pageable pageable);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    @ExceptionHandler({SecomGenericException.class, HttpRequestMethodNotSupportedException.class, MethodArgumentTypeMismatchException.class})
    default ResponseEntity<Object> handleGetInterfaceExceptions(Exception ex,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) {
        // Create the upload response
        HttpStatus httpStatus;
        GetResponse getResponse = new GetResponse();

        // Handle according to the exception type
        if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            getResponse.setResponseText("Not authorized to requested information");
        } else if(ex instanceof HttpRequestMethodNotSupportedException) {
            httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
            getResponse.setResponseText("Method not allowed");
        } else if(ex instanceof SecomNotImplementedException) {
            httpStatus = HttpStatus.NOT_IMPLEMENTED;
            getResponse.setResponseText("Not implemented");
        }  else if (ex instanceof SecomNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            getResponse.setResponseText(String.format("Information with %s not found", ((SecomNotFoundException) ex).getIdentifier()));
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException typeMismatchException = (MethodArgumentTypeMismatchException) ex;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            getResponse.setResponseText(String.format("Parameter %s=%s incorrect format", typeMismatchException.getName(), typeMismatchException.getValue()));
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            getResponse.setResponseText(ex.getMessage());
        }

        // Otherwise, send a generic internal server error
        return ResponseEntity.status(httpStatus)
                .body(getResponse);
    }

}
