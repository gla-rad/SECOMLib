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
    private String callbackEndpoint;
    private Boolean includeXml;
    @Min(value = 0L, message = "The page value must be positive")
    private Integer page;
    @Min(value = 0L, message = "The page size value must be positive")
    private Integer pageSize;

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
     * Get include xml
     *
     * @return include xml
     */
    public Boolean getIncludeXml() { return includeXml; }

    /**
     * Set include xml
     *
     * @param includeXml, whether xml should be included
     */
    public void setIncludeXml(Boolean includeXml) { this.includeXml = includeXml; }

    /**
     * Get the current page number
     *
     * @return the current page number
     */
    public Integer getPage() { return page; }

    /**
     * Set the current page number
     *
     * @param page the current page number
     */
    public void setPage(Integer page) { this.page = page; }

    /**
     * Get the page size
     *
     * @return the page size
     */
    public Integer getPageSize() { return pageSize; }

    /**
     * Set the page size
     *
     * @param pageSize the page size
     */
    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }

    /**
     * Get the callback endpoint
     *
     * @return the callback endpoint
     */
    public String getCallbackEndpoint() { return callbackEndpoint; }

    /**
     * Set the callback endpoint
     *
     * @param callbackEndpoint the callback endpoint
     */
    public void setCallbackEndpoint(String callbackEndpoint) { this.callbackEndpoint = callbackEndpoint; }
}
