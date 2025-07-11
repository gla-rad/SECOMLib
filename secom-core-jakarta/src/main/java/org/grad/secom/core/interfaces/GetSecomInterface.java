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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.ws.rs.*;
import org.grad.secom.core.base.SecomConstants;
import org.grad.secom.core.base.SecomV1Param;
import org.grad.secom.core.exceptions.SecomNotAuthorisedException;
import org.grad.secom.core.exceptions.SecomNotFoundException;
import org.grad.secom.core.exceptions.SecomValidationException;
import org.grad.secom.core.models.GetResponseObject;
import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.Instant;
import java.util.UUID;

/**
 * The SECOM Get Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface GetSecomInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String GET_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/object";

    /**
     * GET /v1/object : The Get interface is used for pulling information from a
     * service provider. The owner of the information (provider) is responsible
     * for the authorization procedure before returning information.
     *
     * @param dataReference the object data reference
     * @param containerType the object data container type
     * @param dataProductType the object data product type
     * @param productVersion the object data product version
     * @param geometry the object geometry
     * @param unlocode the object UNLOCODE
     * @param validFrom the object valid from time
     * @param validTo the object valid to time
     * @param page the page number to be retrieved
     * @param pageSize the maximum page size
     * @return the object information
     */
    @Path(GET_INTERFACE_PATH)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    GetResponseObject get(@QueryParam("dataReference") UUID dataReference,
                          @QueryParam("containerType") @SecomV1Param ContainerTypeEnum containerType,
                          @QueryParam("dataProductType") @SecomV1Param SECOM_DataProductType dataProductType,
                          @QueryParam("productVersion") String productVersion,
                          @QueryParam("geometry") String geometry,
                          @QueryParam("unlocode") @Pattern(regexp = "[A-Z]{5}") String unlocode,
                          @QueryParam("validFrom") @Parameter(example = "20200101T123000", schema = @Schema(implementation = String.class, pattern = "(\\d{8})T(\\d{6})(Z|\\+\\d{4})?")) @SecomV1Param Instant validFrom,
                          @QueryParam("validTo") @Parameter(example = "20200101T123000", schema = @Schema(implementation = String.class, pattern = "(\\d{8})T(\\d{6})(Z|\\+\\d{4})?")) @SecomV1Param Instant validTo,
                          @QueryParam("page") @Min(0) Integer page,
                          @QueryParam("pageSize") @Min(0) Integer pageSize);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleGetInterfaceExceptions(Exception ex,
                                                 HttpServletRequest request,
                                                  HttpServletResponse response) {
        // Create the get response
        Response.Status responseStatus;
        GetResponseObject getResponseObject = new GetResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JsonMappingException
                || ex instanceof NotFoundException) {
            responseStatus = Response.Status.BAD_REQUEST;
            getResponseObject.setResponseText("Bad Request");
        } else if(ex instanceof SecomNotAuthorisedException) {
            responseStatus = Response.Status.FORBIDDEN;
            getResponseObject.setResponseText("Not authorized to requested information");
        } else if(ex instanceof SecomNotFoundException) {
            responseStatus = Response.Status.NOT_FOUND;
            getResponseObject.setResponseText("Information not found");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            getResponseObject.setResponseText(responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(getResponseObject)
                .build();
    }

}
