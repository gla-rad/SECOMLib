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
import java.util.List;

/**
 * The SECOM Get Summary Response Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class GetSummaryResponse {

    // Class Variables
    List<Summary> summary;
    @NotNull
    PaginationObject pagination;

    /**
     * Gets summary.
     *
     * @return the summary
     */
    public List<Summary> getSummary() {
        return summary;
    }

    /**
     * Sets summary.
     *
     * @param summary the summary
     */
    public void setSummary(List<Summary> summary) {
        this.summary = summary;
    }

    /**
     * Gets pagination.
     *
     * @return the pagination
     */
    public PaginationObject getPagination() {
        return pagination;
    }

    /**
     * Sets pagination.
     *
     * @param pagination the pagination
     */
    public void setPagination(PaginationObject pagination) {
        this.pagination = pagination;
    }
}
