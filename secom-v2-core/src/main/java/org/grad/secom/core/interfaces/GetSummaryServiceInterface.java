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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.grad.secom.core.exceptions.SecomNotAuthorisedException;
import org.grad.secom.core.exceptions.SecomNotFoundException;
import org.grad.secom.core.exceptions.SecomValidationException;
import org.grad.secom.core.models.GetSummaryResponseObject;
import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;

import static org.grad.secom.core.base.SecomConstants.SECOM_VERSION;

/**
 * The SECOM Get Summary Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface GetSummaryServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String GET_SUMMARY_INTERFACE_PATH = "/" + SECOM_VERSION + "/object/summary";

    /**
     * GET /v1/object/summary :  A list of information shall be returned from
     * this interface. The summary contains identity, status and short
     * description of each information object. The actual information object
     * shall be retrieved using the Get interface.
     *
     * @param containerType the object data container type
     * @param dataProductType the object data product type
     * @param productVersion the object data product version
     * @param geometry the object geometry
     * @param unlocode the object UNLOCODE
     * @param validFrom the object valid from time
     * @param validTo the object valid to time
     * @param page the page number to be retrieved
     * @param pageSize the maximum page size
     * @return the summary response object
     */
    @Path(GET_SUMMARY_INTERFACE_PATH)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    GetSummaryResponseObject getSummary(@QueryParam("containerType") @Parameter(schema = @Schema(description = "Data Type requested")) ContainerTypeEnum containerType,
                                        @QueryParam("dataProductType") @Parameter(schema = @Schema(description = "Data product type name See: https://registry.iho.int/productspec/list.do (column 'Product ID')")) SECOM_DataProductType dataProductType,
                                        @QueryParam("productVersion") @Parameter(schema = @Schema(description = "S-100 based Product specification version")) String productVersion,
                                        @QueryParam("geometry") @Parameter(schema = @Schema(description = "Geometry condition for geo-located information objects as WKT LineString or Polygon")) String geometry,
                                        @QueryParam("unlocode") @Parameter(schema = @Schema(description = "See UN web page")) @Pattern(regexp = "^[a-zA-Z]{2}[a-zA-Z2-9]{3}") String unlocode,
                                        @QueryParam("validFrom") @Pattern(regexp ="^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$") @Parameter(schema = @Schema(implementation = String.class, description = "Time related to validity period start for information object")) Instant validFrom,
                                        @QueryParam("validTo") @Pattern(regexp ="^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$") @Parameter(schema = @Schema(implementation = String.class, description = "Time related to validity period end for information object")) Instant validTo,
                                        @QueryParam("page") @Min(0) @Parameter(schema = @Schema(implementation = Integer.class, description = "Requested pagination page. Must be a positive integer >= 0..", defaultValue = "1")) Integer page,
                                        @QueryParam("pageSize") @Min(0) @Parameter(schema = @Schema(implementation = Integer.class, description = "Requested pagination page size. Must be a positive integer >= 0.", defaultValue = "100")) Integer pageSize);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @param response the response for the request
     * @return the handler response according to the SECOM standard
     */
    static Response handleGetSummaryInterfaceExceptions(Exception ex,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        // Create the get summary response
        Response.Status responseStatus;
        GetSummaryResponseObject getSummaryResponseObject = new GetSummaryResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JsonMappingException
                || ex instanceof NotFoundException) {
            responseStatus = Response.Status.BAD_REQUEST;
        } else if(ex instanceof SecomNotAuthorisedException) {
            responseStatus = Response.Status.FORBIDDEN;
        } else if(ex instanceof SecomNotFoundException) {
            responseStatus = Response.Status.NOT_FOUND;
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(getSummaryResponseObject)
                .build();
    }

}
