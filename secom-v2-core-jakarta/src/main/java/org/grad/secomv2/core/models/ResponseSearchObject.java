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

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * The SECOM Response Search Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class ResponseSearchObject {

    // Class Variables
    private List<SearchObjectResult> searchServiceResult;
    @NotNull
    private PaginationObject paginationObject;

    /**
     * Gets search service result.
     *
     * @return the search service result
     */
    public List<SearchObjectResult> getSearchServiceResult() {
        return searchServiceResult;
    }

    /**
     * Sets search service result.
     *
     * @param searchServiceResult the search service result
     */
    public void setSearchServiceResult(List<SearchObjectResult> searchServiceResult) {
        this.searchServiceResult = searchServiceResult;
    }

}
