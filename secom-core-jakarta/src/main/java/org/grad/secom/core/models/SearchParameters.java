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
import org.grad.secom.core.models.enums.SECOM_DataProductType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The SECOM Search Parameters Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SearchParameters {

    // Class Variables
    private List<String> names;
    private List<String> statuses;
    private List<String> versions;
    private List<String> keywords;
    private List<String> descriptions;
    private List<SECOM_DataProductType> dataProductTypes;
    @Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$")
    private List<String> specificationIds;
    @Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$")
    private List<String> designIds;
    @Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$")
    private List<String> instanceIds;
    @Pattern(regexp = "^(MID\\d{6}|0MID\\d{5}|00MID\\{4})")
    private List<String> mmsis;
    @Pattern(regexp = "^\\d{7}(?:\\d{2})?$")
    private List<String> imos;
    private List<String> serviceTypes;
    @Schema(description = "The search area as UNLOCODE", type = "string", example = "GBHRW")
    @Pattern(regexp = "^[a-zA-Z]{2}[a-zA-Z2-9]{3}")
    private List<String> unlocodes;
    private List<URI> endpointUris;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return Optional.ofNullable(this.getNames())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.setNames(Collections.singletonList(name));
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return Optional.ofNullable(this.getStatuses())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.setStatuses(Collections.singletonList(status));
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public String getVersion() {
        return Optional.ofNullable(this.getVersions())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(String version) {
        this.setVersions(Collections.singletonList(version));
    }

    /**
     * Gets keyword.
     *
     * @return the keywords
     */
    public String getKeyword() {
        return Optional.ofNullable(this.getKeywords())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets keyword.
     *
     * @param keyword the keyword
     */
    public void setKeyword(String keyword) {
        this.setKeywords(Collections.singletonList(keyword));
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return Optional.ofNullable(this.getDescriptions())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.setDescriptions(Collections.singletonList(description));
    }

    /**
     * Gets data product type.
     *
     * @return the data product type
     */
    public SECOM_DataProductType getDataProductType() {
        return Optional.ofNullable(this.getDataProductTypes())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets data product type.
     *
     * @param dataProductType the data product type
     */
    public void setDataProductType(SECOM_DataProductType dataProductType) {
        this.setDataProductTypes(Collections.singletonList(dataProductType));
    }

    /**
     * Gets specification id.
     *
     * @return the specification id
     */
    public String getSpecificationId() {
        return Optional.ofNullable(this.getSpecificationIds())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets specification id.
     *
     * @param specificationId the specification id
     */
    public void setSpecificationId(String specificationId) {
        this.setSpecificationIds(Collections.singletonList(specificationId));
    }

    /**
     * Gets design id.
     *
     * @return the design id
     */
    public String getDesignId() {
        return Optional.ofNullable(this.getDesignIds())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets design id.
     *
     * @param designId the design id
     */
    public void setDesignId(String designId) {
        this.setDesignIds(Collections.singletonList(designId));
    }

    /**
     * Gets instance id.
     *
     * @return the instance id
     */
    public String getInstanceId() {
        return Optional.ofNullable(this.getInstanceIds())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets instance id.
     *
     * @param instanceId the instance id
     */
    public void setInstanceId(String instanceId) {
        this.setInstanceIds(Collections.singletonList(instanceId));
    }

    /**
     * Gets mmsi.
     *
     * @return the mmsi
     */
    public String getMmsi() {
        return Optional.ofNullable(this.getMmsis())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets mmsi.
     *
     * @param mmsi the mmsi
     */
    public void setMmsi(String mmsi) {
        this.setMmsis(Collections.singletonList(mmsi));
    }

    /**
     * Gets imo.
     *
     * @return the imo
     */
    public String getImo() {
        return Optional.ofNullable(this.getImos())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets imo.
     *
     * @param imo the imo
     */
    public void setImo(String imo) {
        this.setImos(Collections.singletonList(imo));
    }

    /**
     * Gets service type.
     *
     * @return the service type
     */
    public String getServiceType() {
        return Optional.ofNullable(this.getServiceTypes())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets service type.
     *
     * @param serviceType the service type
     */
    public void setServiceType(String serviceType) {
        this.setServiceTypes(Collections.singletonList(serviceType));
    }

    /**
     * Gets unlocode.
     *
     * @return the unlocode
     */
    public String getUnlocode() {
        return Optional.ofNullable(this.getUnlocodes())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets unlocode.
     *
     * @param unlocode the unlocode
     */
    public void setUnlocode(String unlocode) {
        this.setUnlocodes(Collections.singletonList(unlocode));
    }

    /**
     * Gets endpoint uri.
     *
     * @return the endpoint uri
     */
    public URI getEndpointUri() {
        return Optional.ofNullable(this.getEndpointUris())
                .map(List::getFirst)
                .orElse(null);
    }

    /**
     * Sets endpoint uri.
     *
     * @param endpointUri the endpoint uri
     */
    public void setEndpointUri(URI endpointUri) {
        this.setEndpointUris(Collections.singletonList(endpointUri));
    }

    /**
     * Gets names.
     *
     * @return the names
     */
    public List<String> getNames() {
        return names;
    }

    /**
     * Sets names.
     *
     * @param names the names
     */
    public void setNames(List<String> names) {
        this.names = names;
    }

    /**
     * Gets statuses.
     *
     * @return the statuses
     */
    public List<String> getStatuses() {
        return statuses;
    }

    /**
     * Sets statuses.
     *
     * @param statuses the statuses
     */
    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }

    /**
     * Gets versions.
     *
     * @return the versions
     */
    public List<String> getVersions() {
        return versions;
    }

    /**
     * Sets versions.
     *
     * @param versions the versions
     */
    public void setVersions(List<String> versions) {
        this.versions = versions;
    }

    /**
     * Gets keywords.
     *
     * @return the keywords
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * Sets keywords.
     *
     * @param keywords the keywords
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Gets descriptions.
     *
     * @return the descriptions
     */
    public List<String> getDescriptions() {
        return descriptions;
    }

    /**
     * Sets descriptions.
     *
     * @param descriptions the descriptions
     */
    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    /**
     * Gets data product types.
     *
     * @return the data product types
     */
    public List<SECOM_DataProductType> getDataProductTypes() {
        return dataProductTypes;
    }

    /**
     * Sets data product types.
     *
     * @param dataProductTypes the data product types
     */
    public void setDataProductTypes(List<SECOM_DataProductType> dataProductTypes) {
        this.dataProductTypes = dataProductTypes;
    }

    /**
     * Gets specification ids.
     *
     * @return the specification ids
     */
    public @Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$") List<String> getSpecificationIds() {
        return specificationIds;
    }

    /**
     * Sets specification ids.
     *
     * @param specificationIds the specification ids
     */
    public void setSpecificationIds(@Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$") List<String> specificationIds) {
        this.specificationIds = specificationIds;
    }

    /**
     * Gets design ids.
     *
     * @return the design ids
     */
    public @Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$") List<String> getDesignIds() {
        return designIds;
    }

    /**
     * Sets design ids.
     *
     * @param designIds the design ids
     */
    public void setDesignIds(@Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$") List<String> designIds) {
        this.designIds = designIds;
    }

    /**
     * Gets instance ids.
     *
     * @return the instance ids
     */
    public @Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$") List<String> getInstanceIds() {
        return instanceIds;
    }

    /**
     * Sets instance ids.
     *
     * @param instanceIds the instance ids
     */
    public void setInstanceIds(@Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$") List<String> instanceIds) {
        this.instanceIds = instanceIds;
    }

    /**
     * Gets mmsis.
     *
     * @return the mmsis
     */
    public @Pattern(regexp = "^(MID\\d{6}|0MID\\d{5}|00MID\\{4})") List<String> getMmsis() {
        return mmsis;
    }

    /**
     * Sets mmsis.
     *
     * @param mmsis the mmsis
     */
    public void setMmsis(@Pattern(regexp = "^(MID\\d{6}|0MID\\d{5}|00MID\\{4})") List<String> mmsis) {
        this.mmsis = mmsis;
    }

    /**
     * Gets imos.
     *
     * @return the imos
     */
    public @Pattern(regexp = "^\\d{7}(?:\\d{2})?$") List<String> getImos() {
        return imos;
    }

    /**
     * Sets imos.
     *
     * @param imos the imos
     */
    public void setImos(@Pattern(regexp = "^\\d{7}(?:\\d{2})?$") List<String> imos) {
        this.imos = imos;
    }

    /**
     * Gets service types.
     *
     * @return the service types
     */
    public List<String> getServiceTypes() {
        return serviceTypes;
    }

    /**
     * Sets service types.
     *
     * @param serviceTypes the service types
     */
    public void setServiceTypes(List<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    /**
     * Gets unlocodes.
     *
     * @return the unlocodes
     */
    public @Pattern(regexp = "^[a-zA-Z]{2}[a-zA-Z2-9]{3}") List<String> getUnlocodes() {
        return unlocodes;
    }

    /**
     * Sets unlocodes.
     *
     * @param unlocodes the unlocodes
     */
    public void setUnlocodes(@Pattern(regexp = "^[a-zA-Z]{2}[a-zA-Z2-9]{3}") List<String> unlocodes) {
        this.unlocodes = unlocodes;
    }

    /**
     * Gets endpoint uris.
     *
     * @return the endpoint uris
     */
    public List<URI> getEndpointUris() {
        return endpointUris;
    }

    /**
     * Sets endpoint uris.
     *
     * @param endpointUris the endpoint uris
     */
    public void setEndpointUris(List<URI> endpointUris) {
        this.endpointUris = endpointUris;
    }
}
