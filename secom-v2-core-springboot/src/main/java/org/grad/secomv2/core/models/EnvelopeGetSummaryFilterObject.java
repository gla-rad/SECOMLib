/*
 * Copyright (c) 2026 GLA Research and Development Directorate
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

import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import org.grad.secomv2.core.base.SecomInstantDeserializer;
import org.grad.secomv2.core.base.SecomInstantSerializer;
import org.grad.secomv2.core.models.enums.ContainerTypeEnum;
import org.grad.secomv2.core.models.enums.SECOM_DataProductType;

import java.time.Instant;

/**
 * The SECOM Envelope Get Summary Filter Object Class
 *
 * @author Changyun Lee (email: changyun.lee@aivenautics.com)
 */
public class EnvelopeGetSummaryFilterObject extends AbstractEnvelope {

    // Class Variables
    @Schema(description = "Container type requested")
    private ContainerTypeEnum containerType;
    @Schema(description = "Data product type name requested", example = "S-124")
    private SECOM_DataProductType dataProductType;
    @Schema(description = "S-100 based Product type version requested", example = "2.0.0")
    private String productVersion;
    @Schema(description = "Geometry condition for geo-located information objects", type = "string", example = "POLYGON ((0.65 51.42, 0.65 52.26, 2.68 52.26, 2.68 51.42, 0.65 51.42))")
    @Pattern(regexp = "^([A-Z]+\\s*\\(\\(?\\s*(-?\\d+(\\.\\d+)?)\\s+-?\\d+(\\.\\d+)?(?:\\s+-?\\d+(\\.\\d+)?)?\\s*(,\\s*(-?\\d+(\\.\\d+)?)\\s+-?\\d+(\\.\\d+)?(?:\\s+-?\\d+(\\.\\d+)?)?\\s*)*\\)\\)?\\s*)+$")
    private String geometry;
    @Schema(description = "Code of defined object See: https://unece.org/trade/cefact/unlocode-code-list-country-and-territory", type = "string", example = "GBHRW")
    @Pattern(regexp = "[A-Z]{5}")
    private String unlocode;
    @Schema(description = "Time related to validity period start for information object", type = "string", example = "1985-04-12T10:15:30Z", pattern =  "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(Z|\\+\\d{4})?")
    @JsonSerialize(using = SecomInstantSerializer.class)
    @JsonDeserialize(using = SecomInstantDeserializer.class)
    private Instant validFrom;
    @Schema(description = "Time related to validity period end for information object", type = "string", example = "1985-04-12T10:15:30Z", pattern =  "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(Z|\\+\\d{4})?")
    @JsonSerialize(using = SecomInstantSerializer.class)
    @JsonDeserialize(using = SecomInstantDeserializer.class)
    private Instant validTo;
    @Schema(description = "Requested pagination page. Must be a positive integer >= 1..", defaultValue = "1")
    private Integer page;
    @Schema(description = "Requested pagination page size. Must be a positive integer >= 0.", defaultValue = "100")
    private Integer pageSize;

    /**
     * Gets container type.
     *
     * @return the container type
     */
    public ContainerTypeEnum getContainerType() {
        return containerType;
    }

    /**
     * Sets container type.
     *
     * @param containerType the container type
     */
    public void setContainerType(ContainerTypeEnum containerType) {
        this.containerType = containerType;
    }

    /**
     * Gets data product type.
     *
     * @return the data product type
     */
    public SECOM_DataProductType getDataProductType() {
        return dataProductType;
    }

    /**
     * Sets data product type.
     *
     * @param dataProductType the data product type
     */
    public void setDataProductType(SECOM_DataProductType dataProductType) {
        this.dataProductType = dataProductType;
    }

    /**
     * Gets product version.
     *
     * @return the product version
     */
    public String getProductVersion() {
        return productVersion;
    }

    /**
     * Sets product version.
     *
     * @param productVersion the product version
     */
    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
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
     * Gets unlocode.
     *
     * @return the unlocode
     */
    public String getUnlocode() {
        return unlocode;
    }

    /**
     * Sets unlocode.
     *
     * @param unlocode the unlocode
     */
    public void setUnlocode(String unlocode) {
        this.unlocode = unlocode;
    }

    /**
     * Gets valid from.
     *
     * @return the valid from
     */
    public Instant getValidFrom() {
        return validFrom;
    }

    /**
     * Sets valid from.
     *
     * @param validFrom the valid from
     */
    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * Gets valid to.
     *
     * @return the valid to
     */
    public Instant getValidTo() {
        return validTo;
    }

    /**
     * Sets valid to.
     *
     * @param validTo the valid to
     */
    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
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
     * This method should be implemented by all envelop objects to allow the
     * generation of the signature CSV attribute array
     *
     * @return the generated signature CSV attribute array
     */
    @Override
    public Object[] getAttributeArray() {
        return new Object[] {
                containerType,
                dataProductType,
                productVersion,
                geometry,
                unlocode,
                validFrom,
                validTo,
                page,
                pageSize,
                envelopeSignatureCertificate,
                envelopeRootCertificateThumbprint,
                envelopeSignatureTime
        };
    }
}
