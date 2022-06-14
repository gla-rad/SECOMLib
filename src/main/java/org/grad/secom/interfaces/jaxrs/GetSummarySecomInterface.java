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

package org.grad.secom.interfaces.jaxrs;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.grad.secom.exceptions.SecomNotAuthorisedException;
import org.grad.secom.exceptions.SecomNotFoundException;
import org.grad.secom.exceptions.SecomValidationException;
import org.grad.secom.models.GetSummaryResponseObject;
import org.grad.secom.models.enums.ContainerTypeEnum;
import org.grad.secom.models.enums.SECOM_DataProductType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

/**
 * The SECOM Get Summary Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface GetSummarySecomInterface extends GenericSecomInterface {

    /**
     * The Interface Endpoint Path.
     */
    String GET_SUMMARY_INTERFACE_PATH = "/v1/object/summary";

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
    GetSummaryResponseObject getSummary(@QueryParam("containerType") ContainerTypeEnum containerType,
                                        @QueryParam("dataProductType") SECOM_DataProductType dataProductType,
                                        @QueryParam("productVersion") String productVersion,
                                        @QueryParam("geometry") String geometry,
                                        @QueryParam("unlocode") @Pattern(regexp = "[A-Z]{5}") String unlocode,
                                        @QueryParam("validFrom") LocalDateTime validFrom,
                                        @QueryParam("validTo") LocalDateTime validTo,
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
    static Response handleGetSummaryInterfaceExceptions(Exception ex,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {
        // Create the get summary response
        Response.Status responseStatus;
        GetSummaryResponseObject getSummaryResponseObject = new GetSummaryResponseObject();

        // Handle according to the exception type
        if(ex instanceof SecomValidationException || ex instanceof ValidationException || ex instanceof InvalidFormatException) {
            responseStatus = Response.Status.BAD_REQUEST;
            getSummaryResponseObject.setResponseText("Bad Request");
        } else if(ex instanceof SecomNotAuthorisedException) {
            responseStatus = Response.Status.FORBIDDEN;
            getSummaryResponseObject.setResponseText("Not authorized to requested information");
        } else if(ex instanceof SecomNotFoundException) {
            responseStatus = Response.Status.NOT_FOUND;
            getSummaryResponseObject.setResponseText("Information not found");
        } else {
            responseStatus = GenericSecomInterface.handleCommonExceptionResponseCode(ex);
            getSummaryResponseObject.setResponseText(responseStatus.getReasonPhrase());
        }

        // And send the error response back
        return Response.status(responseStatus)
                .entity(getSummaryResponseObject)
                .build();
    }

}
