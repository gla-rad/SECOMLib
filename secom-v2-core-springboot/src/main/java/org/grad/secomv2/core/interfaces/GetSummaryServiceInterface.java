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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.grad.secomv2.core.base.SecomConstants;
import org.grad.secomv2.core.base.SecomV2Param;
import org.grad.secomv2.core.exceptions.SecomNotAuthorisedException;
import org.grad.secomv2.core.exceptions.SecomNotFoundException;
import org.grad.secomv2.core.exceptions.SecomSchemaValidationException;
import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.grad.secomv2.core.models.GetSummaryResponseObject;
import org.grad.secomv2.core.models.enums.ContainerTypeEnum;
import org.grad.secomv2.core.models.enums.SECOM_DataProductType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;

/**
 * The SECOM Get Summary Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@RequestMapping("/api/secom/")
public interface GetSummaryServiceInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String GET_SUMMARY_INTERFACE_PATH = "/" + SecomConstants.SECOM_VERSION + "/object/summary";

    /**
     * GET /v2/object/summary :  A list of information shall be returned from
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
    @GetMapping(path = GET_SUMMARY_INTERFACE_PATH,
                produces = { MediaType.APPLICATION_JSON_VALUE })
    ResponseEntity<GetSummaryResponseObject> getSummary(@RequestParam("containerType") @Parameter(schema = @Schema(description = "Data Type requested")) @SecomV2Param ContainerTypeEnum containerType,
                                        @RequestParam("dataProductType") @Parameter(schema = @Schema(description = "Data product type name See: https://registry.iho.int/productspec/list.do (column 'Product ID')")) @SecomV2Param SECOM_DataProductType dataProductType,
                                        @RequestParam("productVersion") @Parameter(schema = @Schema(description = "S-100 based Product specification version")) String productVersion,
                                        @RequestParam("geometry") @Parameter(schema = @Schema(description = "Geometry condition for geo-located information objects as WKT LineString or Polygon")) String geometry,
                                        @RequestParam("unlocode") @Parameter(schema = @Schema(description = "See UN web page")) @Pattern(regexp = "^[a-zA-Z]{2}[a-zA-Z2-9]{3}") String unlocode,
                                        @RequestParam("validFrom") @Parameter(schema = @Schema(implementation = String.class, description = "Time related to validity period start for information object")) @SecomV2Param Instant validFrom,
                                        @RequestParam("validTo") @Parameter(schema = @Schema(implementation = String.class, description = "Time related to validity period end for information object")) @SecomV2Param Instant validTo,
                                        @RequestParam("page") @Min(1) @Parameter(schema = @Schema(implementation = Integer.class, description = "Requested pagination page. Must be a positive integer >= 1..", defaultValue = "1")) Integer page,
                                        @RequestParam("pageSize") @Min(0) @Parameter(schema = @Schema(implementation = Integer.class, description = "Requested pagination page size. Must be a positive integer >= 0.", defaultValue = "100")) Integer pageSize);

    /**
     * The exception handler implementation for the interface.
     *
     * @param ex the exception that was raised
     * @param request the request that cause the exception
     * @return the handler response according to the SECOM standard
     */
    static ResponseEntity<Object> handleGetSummaryInterfaceExceptions(Exception ex,
                                                                      HttpServletRequest request) {
        // Create the get summary response
        HttpStatus httpStatus;
        GetSummaryResponseObject getSummaryResponseObject = new GetSummaryResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException
                || ex.getCause() instanceof SecomValidationException
                || ex instanceof ValidationException
                || ex instanceof JacksonException
                || ex instanceof HttpClientErrorException.NotFound) {
            httpStatus = HttpStatus.BAD_REQUEST;

        } else if(ex instanceof SecomNotAuthorisedException) {
            httpStatus = HttpStatus.FORBIDDEN;

        } else if(ex instanceof SecomNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;

        } else if(ex instanceof SecomSchemaValidationException) {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_CONTENT)
                    .body(getSummaryResponseObject);
        } else {
            httpStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);

        }
        return ResponseEntity
                .status(httpStatus)
                .body(getSummaryResponseObject);
    }

}
