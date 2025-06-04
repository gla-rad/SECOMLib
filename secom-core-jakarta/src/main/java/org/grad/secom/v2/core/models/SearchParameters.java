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

package org.grad.secom.v2.core.models;

import io.swagger.v3.oas.annotations.media.Schema;
import org.grad.secom.v2.core.models.enums.SECOM_DataProductType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.net.URI;

/**
 * The SECOM Search Parameters Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SearchParameters {

    // Class Variables
    private String name;
    private String status;
    private String version;
    private String keywords;
    private String description;
    private SECOM_DataProductType dataProductType;
    @Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$")
    private String specificationId;
    @Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$")
    private String designId;
    @Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$")
    private String instanceId;
    @Pattern(regexp = "^(MID\\d{6}|0MID\\d{5}|00MID\\{4})")
    private String mmsi;
    @Pattern(regexp = "^\\d{7}(?:\\d{2})?$")
    private String imo;
    private String serviceType;
    @Schema(description = "The search area as UNLOCODE", type = "string", example = "GBHRW")
    @Pattern(regexp = "^[a-zA-Z]{2}[a-zA-Z2-9]{3}")
    private String unlocode;
    private URI endpointUri;
    @Min(value = 0L, message = "The page value must be positive")
    private Integer page;
    @Min(value = 0L, message = "The page size value must be positive")
    private Integer pageSize;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets keywords.
     *
     * @return the keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * Sets keywords.
     *
     * @param keywords the keywords
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Gets specification id.
     *
     * @return the specification id
     */
    public String getSpecificationId() {
        return specificationId;
    }

    /**
     * Sets specification id.
     *
     * @param specificationId the specification id
     */
    public void setSpecificationId(String specificationId) {
        this.specificationId = specificationId;
    }

    /**
     * Gets design id.
     *
     * @return the design id
     */
    public String getDesignId() {
        return designId;
    }

    /**
     * Sets design id.
     *
     * @param designId the design id
     */
    public void setDesignId(String designId) {
        this.designId = designId;
    }

    /**
     * Gets instance id.
     *
     * @return the instance id
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * Sets instance id.
     *
     * @param instanceId the instance id
     */
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * Gets mmsi.
     *
     * @return the mmsi
     */
    public String getMmsi() {
        return mmsi;
    }

    /**
     * Sets mmsi.
     *
     * @param mmsi the mmsi
     */
    public void setMmsi(String mmsi) {
        this.mmsi = mmsi;
    }

    /**
     * Gets imo.
     *
     * @return the imo
     */
    public String getImo() {
        return imo;
    }

    /**
     * Sets imo.
     *
     * @param imo the imo
     */
    public void setImo(String imo) {
        this.imo = imo;
    }

    /**
     * Gets service type.
     *
     * @return the service type
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Sets service type.
     *
     * @param serviceType the service type
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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
     * Gets endpoint uri.
     *
     * @return the endpoint uri
     */
    public URI getEndpointUri() {
        return endpointUri;
    }

    /**
     * Sets endpoint uri.
     *
     * @param endpointUri the endpoint uri
     */
    public void setEndpointUri(URI endpointUri) {
        this.endpointUri = endpointUri;
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
}
