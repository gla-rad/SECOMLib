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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.grad.secom.core.base.InstantDeserializer;
import org.grad.secom.core.base.InstantSerializer;
import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;

import java.time.Instant;
import java.util.UUID;

/**
 * The SECOM Summary Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SummaryObject {

    // Class Variables
    @NotNull
    private UUID dataReference;
    @NotNull
    private Boolean dataProtection;
    @NotNull
    private Boolean dataCompression;
    @NotNull
    private ContainerTypeEnum containerType;
    @NotNull
    private SECOM_DataProductType dataProductType;
    private String info_identifier;
    private String info_name;
    private String info_status;
    private String info_description;
    @Schema(description = "The last modified date-time", type = "string", example = "19850412T101530", pattern = "(\\d{8})T(\\d{6})(Z|\\+\\d{4})?")
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant info_lastModifiedDate;
    private String info_productVersion;
    private Long info_size;

    /**
     * Gets data reference.
     *
     * @return the data reference
     */
    public UUID getDataReference() {
        return dataReference;
    }

    /**
     * Sets data reference.
     *
     * @param dataReference the data reference
     */
    public void setDataReference(UUID dataReference) {
        this.dataReference = dataReference;
    }

    /**
     * Gets data protection.
     *
     * @return the data protection
     */
    public Boolean getDataProtection() {
        return dataProtection;
    }

    /**
     * Sets data protection.
     *
     * @param dataProtection the data protection
     */
    public void setDataProtection(Boolean dataProtection) {
        this.dataProtection = dataProtection;
    }

    /**
     * Gets data compression.
     *
     * @return the data compression
     */
    public Boolean getDataCompression() {
        return dataCompression;
    }

    /**
     * Sets data compression.
     *
     * @param dataCompression the data compression
     */
    public void setDataCompression(Boolean dataCompression) {
        this.dataCompression = dataCompression;
    }

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
     * Gets info identifier.
     *
     * @return the info identifier
     */
    public String getInfo_identifier() {
        return info_identifier;
    }

    /**
     * Sets info identifier.
     *
     * @param info_identifier the info identifier
     */
    public void setInfo_identifier(String info_identifier) {
        this.info_identifier = info_identifier;
    }

    /**
     * Gets info name.
     *
     * @return the info name
     */
    public String getInfo_name() {
        return info_name;
    }

    /**
     * Sets info name.
     *
     * @param info_name the info name
     */
    public void setInfo_name(String info_name) {
        this.info_name = info_name;
    }

    /**
     * Gets info status.
     *
     * @return the info status
     */
    public String getInfo_status() {
        return info_status;
    }

    /**
     * Sets info status.
     *
     * @param info_status the info status
     */
    public void setInfo_status(String info_status) {
        this.info_status = info_status;
    }

    /**
     * Gets info description.
     *
     * @return the info description
     */
    public String getInfo_description() {
        return info_description;
    }

    /**
     * Sets info description.
     *
     * @param info_description the info description
     */
    public void setInfo_description(String info_description) {
        this.info_description = info_description;
    }

    /**
     * Gets info last modified date.
     *
     * @return the info last modified date
     */
    public Instant getInfo_lastModifiedDate() {
        return info_lastModifiedDate;
    }

    /**
     * Sets info last modified date.
     *
     * @param info_lastModifiedDate the info last modified date
     */
    public void setInfo_lastModifiedDate( Instant info_lastModifiedDate) {
        this.info_lastModifiedDate = info_lastModifiedDate;
    }

    /**
     * Gets info product version.
     *
     * @return the info product version
     */
    public String getInfo_productVersion() {
        return info_productVersion;
    }

    /**
     * Sets info product version.
     *
     * @param info_productVersion the info product version
     */
    public void setInfo_productVersion(String info_productVersion) {
        this.info_productVersion = info_productVersion;
    }

    /**
     * Gets info size.
     *
     * @return the info size
     */
    public Long getInfo_size() {
        return info_size;
    }

    /**
     * Sets info size.
     *
     * @param info_size the info size
     */
    public void setInfo_size(Long info_size) {
        this.info_size = info_size;
    }
}
