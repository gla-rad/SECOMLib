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

package org.grad.secom.controllers;

import org.grad.secom.models.enums.DataTypeEnum;
import org.grad.secom.models.GetSummaryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * The SECOM Get Summary Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface GetSummaryInterface {


    /**
     * GET /v1/object/summary : Returns the service instance summary as
     * specified by the SECOM standard.
     *
     * @param dataType the object data type
     * @param productSpecification the object product specification
     * @param geometry the object geometry
     * @param areaName the object area name
     * @param unlocode the object entries UNLOCODE
     * @param fromTime the object from time
     * @param toTime the object to time
     * @param pageable the pageable information
     * @return the SECOM summary information
     */
    ResponseEntity<GetSummaryResponse> getSummary(@RequestParam("dataType") Optional<DataTypeEnum> dataType,
                                                  @RequestParam("productSpecification") Optional<String> productSpecification,
                                                  @RequestParam("geometry") Optional<String> geometry,
                                                  @RequestParam("areaName") Optional<String> areaName,
                                                  @RequestParam("unlocode") Optional<String> unlocode,
                                                  @RequestParam("fromTime") Optional<String> fromTime,
                                                  @RequestParam("toTime") Optional<String> toTime,
                                                  Pageable pageable);

}
