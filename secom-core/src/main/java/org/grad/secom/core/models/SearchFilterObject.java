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

import javax.validation.constraints.Pattern;

/**
 * The SECOM Search Filter Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SearchFilterObject {

    // Class Variables
    private SearchParameters query;
    @Pattern(regexp = "(^([A-Z]+\\s*\\(\\(?\\s*(-?\\d+(\\.\\d+)?)\\s+-?\\d+(\\.\\d+)?(?:\\s+-?\\d+(\\.\\d+)?)?\\s*(,\\s*(-?\\d+(\\.\\d+)?)\\s+-?\\d+(\\.\\d+)?(?:\\s+-?\\d+(\\.\\d+)?)?\\s*)*\\)\\)?\\s*)+$|^\\s*(\\{.*\\}|\\w+)\\s*$)")
    private String geometry;
    @Pattern(regexp = "^[a-zA-Z0-9 _:()+\\-,.*?\"]*$")
    private String freetext;

    /**
     * Gets query.
     *
     * @return the query
     */
    public SearchParameters getQuery() {
        return query;
    }

    /**
     * Sets query.
     *
     * @param query the query
     */
    public void setQuery(SearchParameters query) {
        this.query = query;
    }

    /**
     * Gets geometry.
     *
     * @return the geometry
     */
    public String getGeometry() {
        return geometry;
    }

    /**
     * Sets geometry.
     *
     * @param geometry the geometry
     */
    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    /**
     * Gets freetext.
     *
     * @return the freetext
     */
    public String getFreetext() {
        return freetext;
    }

    /**
     * Sets freetext.
     *
     * @param freetext the freetext
     */
    public void setFreetext(String freetext) {
        this.freetext = freetext;
    }
}
