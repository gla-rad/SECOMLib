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

import org.grad.secom.models.S100ProductSpecification;
import org.grad.secom.models.SubscriptionResponse;
import org.grad.secom.models.enums.AreaNameEnum;
import org.grad.secom.models.enums.DataTypeEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * The SECOM Subscription Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface SubscriptionInterface {

    /**
     * POST /v1/subscription : Request subscription on information, either
     * specific information according to parameters, or the information
     * accessible upon decision by the information provider.
     *
     * @param dataType the datatype requested
     * @param productSpecification the S-100-based product type requested
     * @param geometry geometry criteria as WKT LineString or Polygon
     * @param areaName name of defined area
     * @param unlocode according to UN/LOCODE codelist
     * @return the subscription response object
     */
    ResponseEntity<SubscriptionResponse> subscription(@RequestParam("dataType") Optional<DataTypeEnum> dataType,
                                                      @RequestParam("productSpecification") Optional<S100ProductSpecification> productSpecification,
                                                      @RequestParam("geometry") Optional<String> geometry,
                                                      @RequestParam("areaName") Optional<AreaNameEnum> areaName,
                                                      @RequestParam("unlocode") Optional<String> unlocode);

}
