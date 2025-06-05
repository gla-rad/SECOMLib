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

package org.grad.secom.core.models;


import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * The SECOM Get Summary Response Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class GetSummaryResponseObject {

    // Class Variables
    private List<SummaryObject> summaryObject;
    @NotNull
    private PaginationObject pagination;
    private String responseText;

    /**
     * Instantiates a new Get Summary response object.
     */
    public GetSummaryResponseObject() {
        this.responseText = "";
    }

    /**
     * Gets summary object.
     *
     * @return the summary object
     */
    public List<SummaryObject> getSummaryObject() {
        return summaryObject;
    }

    /**
     * Sets summary object.
     *
     * @param summaryObject the summary object
     */
    public void setSummaryObject(List<SummaryObject> summaryObject) {
        this.summaryObject = summaryObject;
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

    /**
     * Gets response text.
     *
     * @return the response text
     */
    public String getResponseText() {
        return responseText;
    }

    /**
     * Sets response text.
     *
     * @param responseText the response text
     */
    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
