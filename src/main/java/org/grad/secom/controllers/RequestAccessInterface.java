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

import org.grad.secom.models.RequestAccess;
import org.grad.secom.models.S100ProductSpecification;
import org.grad.secom.models.enums.DataTypeEnum;
import org.grad.secom.models.enums.ReasonEnum;
import org.grad.secom.models.RequestAccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * The SECOM Request Access Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface RequestAccessInterface {

    /**
     * POST /v1/access/request : Access to the service instance information can
     * be requested through the Request Access interface.
     *
     * @param requestAccess the request access object
     * @return the request access response object
     */
    ResponseEntity<RequestAccessResponse> requestAccess(@RequestBody RequestAccess requestAccess);

}
