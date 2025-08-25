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

package org.grad.secomv2.core.models;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The SECOM Search SearhResult Class.
 *
 * @author Jakob Svenningsen (email: jakob@dmc.international)
 */
public class SearchResult {

    // Class Variables
    @Schema(description = "The unique transaction ID of the search", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String transactionId;

    private List<SearchObjectResult> services;

    /**
     * Gets search transaction ID.
     *
     * @return the search transaction ID
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets search transaction ID.
     *
     * @param transactionId the search transaction ID
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Gets search service result.
     *
     * @return the search service result
     */
    public List<SearchObjectResult> getServices() {
        return services;
    }

    /**
     * Sets search service result.
     *
     * @param services the search service result
     */
    public void setServices(List<SearchObjectResult> services) {
        this.services = services;
    }
}
