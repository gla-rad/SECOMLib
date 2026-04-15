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

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * The SECOM Search Filter Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeSearchFilterObject extends AbstractEnvelope {

    // Class Variables
    private SearchParameters query;
    @Schema(description = "The search geometry", type = "WKT", example = "POLYGON ((0.65 51.42, 0.65 52.26, 2.68 52.26, 2.68 51.42, 0.65 51.42))")
    @Pattern(regexp = "(^([A-Z]+\\s*\\(\\(?\\s*(-?\\d+(\\.\\d+)?)\\s+-?\\d+(\\.\\d+)?(?:\\s+-?\\d+(\\.\\d+)?)?\\s*(,\\s*(-?\\d+(\\.\\d+)?)\\s+-?\\d+(\\.\\d+)?(?:\\s+-?\\d+(\\.\\d+)?)?\\s*)*\\)\\)?\\s*)+$|^\\s*(\\{.*\\}|\\w+)\\s*$)")
    private String geometry;
    private Boolean localOnly;

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
     * Get localOnly
     *
     * @return localOnly
     */
    public Boolean getLocalOnly() { return localOnly; }

    /**
     * Set localOnly
     *
     * @param localOnly, whether xml should be included
     */
    public void setLocalOnly(Boolean localOnly) { this.localOnly = localOnly; }


    @Override
    public Object[] getAttributeArray() {
        ArrayList<Object> attributes = new ArrayList<>(List.of(this.query.getAttributeArray()));
        attributes.add(this.geometry);
        attributes.add(this.localOnly);
        attributes.add(envelopeSignatureCertificate);
        attributes.add(envelopeRootCertificateThumbprint);
        attributes.add(envelopeSignatureTime);
        attributes.add(digitalSignatureReference);

        return attributes.toArray();
    }
}
