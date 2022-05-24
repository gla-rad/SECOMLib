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

package org.grad.secom.models;

import javax.validation.constraints.NotNull;

/**
 * The SECOM Data Response Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class DataResponse {

    // Class Variables
    @NotNull
    private String payload;
    @NotNull
    private ServiceExchangeMetadata serviceExchangeMetadata;

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * Gets service exchange metadata.
     *
     * @return the service exchange metadata
     */
    public ServiceExchangeMetadata getServiceExchangeMetadata() {
        return serviceExchangeMetadata;
    }

    /**
     * Sets service exchange metadata.
     *
     * @param serviceExchangeMetadata the service exchange metadata
     */
    public void setServiceExchangeMetadata(ServiceExchangeMetadata serviceExchangeMetadata) {
        this.serviceExchangeMetadata = serviceExchangeMetadata;
    }
}
