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
import java.util.UUID;

/**
 * The SECOM Search Result Class.
 *
 * @author Jakob Svenningsen (email: jakob@dmc.international)
 */
public class SearchResult {

    @Schema(description = "The unique transaction ID of the search", requiredMode = Schema.RequiredMode.REQUIRED,
            pattern = "^[{(]?[0-9a-fA-F]{8}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{12}[)}]?$",
            example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull
    private UUID transactionId;

    // Class Variables
    private List<ServiceInstanceObject> serviceInstance;

     /**
     * Gets search service result.
     *
     * @return the search service result
     */
    public List<ServiceInstanceObject> getServiceInstance() {
        return serviceInstance;
    }

    /**
     * Sets search service result.
     *
     * @param serviceInstance the search service result
     */
    public void setServiceInstance(List<ServiceInstanceObject> serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    /**
     * Get the transaction id
     *
     * @return the transaction id
     */
    public UUID getTransactionId() { return transactionId; }

    /**
     * Set the transaction id
     *
     * @param transactionId the transaction id
     */
    public void setTransactionId(UUID transactionId) { this.transactionId = transactionId; }


}
