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

import org.grad.secom.models.SearchFilterObject;
import org.grad.secom.models.SearchObjectResult;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * The SECOM Dervice Discovery Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface DiscoveryServiceInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String DISCOVERY_SERVICE_INTERFACE_PATH = "/v1/searchService";

    /**
     * POST /v1/searchService : The purpose of this interface is to search for
     * service instances to consume.
     *
     * @param searchFilterObject    The search filter object
     * @return the result list of the search
     */
    @PostMapping(value = DISCOVERY_SERVICE_INTERFACE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<SearchObjectResult>> search(@RequestBody @Valid SearchFilterObject searchFilterObject);

}
