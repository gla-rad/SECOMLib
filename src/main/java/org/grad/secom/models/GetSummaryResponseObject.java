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


import java.util.List;

/**
 * The SECOM Get Summary Response Object
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class GetSummaryResponseObject {

    // Class Variables
    List<InformationSummaryObject> informationSummaryObject;
    PaginationObject pagination;

    /**
     * Gets information summary object.
     *
     * @return the information summary object
     */
    public List<InformationSummaryObject> getInformationSummaryObject() {
        return informationSummaryObject;
    }

    /**
     * Sets information summary object.
     *
     * @param informationSummaryObject the information summary object
     */
    public void setInformationSummaryObject(List<InformationSummaryObject> informationSummaryObject) {
        this.informationSummaryObject = informationSummaryObject;
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
