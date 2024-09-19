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

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

/**
 * The SECOM Search Filter Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SearchFilterObject {

    // Class Variables
    private SearchParameters query;
    @Schema(description = "The search geometry", type = "WKT", example = "POLYGON ((0.65 51.42, 0.65 52.26, 2.68 52.26, 2.68 51.42, 0.65 51.42))")
    @Pattern(regexp = "(^([A-Z]+\\s*\\(\\(?\\s*(-?\\d+(\\.\\d+)?)\\s+-?\\d+(\\.\\d+)?(?:\\s+-?\\d+(\\.\\d+)?)?\\s*(,\\s*(-?\\d+(\\.\\d+)?)\\s+-?\\d+(\\.\\d+)?(?:\\s+-?\\d+(\\.\\d+)?)?\\s*)*\\)\\)?\\s*)+$|^\\s*(\\{.*\\}|\\w+)\\s*$)")
    private String geometry;
    @Pattern(regexp = "^[a-zA-Z0-9 _:()+\\-,.*?\"]*$")
    private String freetext;
    @Min(value = 0L, message = "The page value must be positive")
    private Integer page;
    @Min(value = 0L, message = "The page size value must be positive")
    private Integer pageSize;
    private boolean includeXml;

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

    /**
     * Gets page.
     *
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * Gets page size.
     *
     * @return the page size
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Sets page size.
     *
     * @param pageSize the page size
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Is include xml boolean.
     *
     * @return the boolean
     */
    public boolean isIncludeXml() {
        return includeXml;
    }

    /**
     * Sets include xml.
     *
     * @param includeXml the include xml
     */
    public void setIncludeXml(boolean includeXml) {
        this.includeXml = includeXml;
    }
}
