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
import org.grad.secomv2.core.models.enums.SECOM_DataProductType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.UUID;

/**
 * The SECOM Service Instance Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class ServiceInstanceObject {

    // SECOM-standard Fields
    @Schema(description = "The unique transaction ID of the search", requiredMode = Schema.RequiredMode.REQUIRED,
            pattern = "^[{(]?[0-9a-fA-F]{8}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{12}[)}]?$",
            example = "550e8400-e29b-41d4-a716-446655440000")
    @NotNull
    private UUID transactionId;
    @NotNull
    @Pattern(regexp = "^urn:mrn:[a-z0-9][a-z0-9-]{0,31}:[a-z0-9()+,\\-.:=@;$_!*'%/?#]+$")
    private String instanceId;
    @NotNull
    private String version;
    @NotNull
    private String name;
    @NotNull
    private String status;
    @NotNull
    private String description;
    private SECOM_DataProductType[] dataProductType;
    @NotNull
    private String organizationId;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9+.\\-]*:(//[^/\\s?#]*)?([^\\s?#]*)(\\?[^#\\s]*)?(#\\S*)?$")
    private String endpointUri;
    private String[] endpointType;
    private String[] keywords;
    private String unlocode;
    private String implementsDesign;
    @NotNull
    @Pattern(regexp = "^(https?|ftp)://([a-zA-Z0-9\\-._~%!$&'()*+,;=]+@)?([a-zA-Z0-9\\-._~]+)(:\\d+)?(/[^\\s]*)?$")
    private String apiDoc;
    @NotNull
    private String[] coverageArea;
    private String instanceAsXml;
    private String imo;
    private String mmsi;
    private List<String> certificates;
    private String[] sourceMSRs;
    private String[] unsupportedParams;

    /**
     * Get the transaction id
     *
     * @return the transaction id
     */
    public UUID getTransactionId() { return transactionId; }

    /**
     * Set the transaction identifier
     *
     * @param transactionId the transaction identifier
     */
    public void setTransactionId(UUID transactionId) { this.transactionId = transactionId; }

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
    public SECOM_DataProductType[] getDataProductType() {
        return dataProductType;
    }

    /**
     * Sets data product type.
     *
     * @param dataProductType the data product type
     */
    public void setDataProductType(SECOM_DataProductType[] dataProductType) {
        this.dataProductType = dataProductType;
    }

    /**
     * Gets organization id.
     *
     * @return the organization id
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Sets organization id.
     *
     * @param organizationId the organization id
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * Gets endpoint uri.
     *
     * @return the endpoint uri
     */
    public String getEndpointUri() {
        return endpointUri;
    }

    /**
     * Sets endpoint uri.
     *
     * @param endpointUri the endpoint uri
     */
    public void setEndpointUri(String endpointUri) {
        this.endpointUri = endpointUri;
    }

    /**
     * Gets endpoint type.
     *
     * @return the endpoint type
     */
    public String[] getEndpointType() {
        return endpointType;
    }

    /**
     * Sets endpoint type.
     *
     * @param endpointType the endpoint type
     */
    public void setEndpointType(String[] endpointType) {
        this.endpointType = endpointType;
    }

    /**
     * Gets keywords.
     *
     * @return the keywords
     */
    public String[] getKeywords() {
        return keywords;
    }

    /**
     * Sets keywords.
     *
     * @param keywords the keywords
     */
    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
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
     * Get implements design
     *
     * @return implementsDesign
     */
    public String getImplementsDesign() {
        return implementsDesign;
    }

    /**
     * Sets implements design
     *
     * @param implementsDesign the design
     */
    public void setImplementsDesign(String implementsDesign) {
        this.implementsDesign = implementsDesign;
    }

    /**
     * Get api doc
     *
     * @return apiDoc
     */
    public String getApiDoc() {
        return apiDoc;
    }

    /**
     * Sets api doc
     *
     * @param apiDoc the api doc
     */
    public void setApiDoc(String apiDoc) {
        this.apiDoc = apiDoc;
    }

    /**
     * Gets coverage area
     *
     * @return coverageArea
     */
    public String[] getCoverageArea() {
        return coverageArea;
    }

    /**
     * Sets coverage area
     *
     * @param coverageArea the coverage area
     */
    public void setCoverageArea(String[] coverageArea) {
        this.coverageArea = coverageArea;
    }

    /**
     * Gets instance as xml.
     *
     * @return the instance as xml
     */
    public String getInstanceAsXml() {
        return instanceAsXml;
    }

    /**
     * Sets instance as xml.
     *
     * @param instanceAsXml the instance as xml
     */
    public void setInstanceAsXml(String instanceAsXml) {
        this.instanceAsXml = instanceAsXml;
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
     * Gets certificates
     *
     * @return certificates
     */
    public List<String> getCertificates() {
        return certificates;
    }

    /**
     * Sets the certificates
     *
     * @param certificates the certificates
     */
    public void setCertificates(List<String> certificates) {
        this.certificates = certificates;
    }

    /**
     * Gets source MSRs.
     *
     * @return the source MSRs
     */
    public String[] getSourceMSRs() {
        return sourceMSRs;
    }

    /**
     * Sets source MSRs.
     *
     * @param sourceMSRs the source msr
     */
    public void setSourceMSRs(String[] sourceMSRs) {
        this.sourceMSRs = sourceMSRs;
    }

    /**
     * Gets unsupported params
     *
     * @return unsupportedParams
     */
    public String[] getUnsupportedParams() {
        return unsupportedParams;
    }

    /**
     * Set unsupportedParams
     *
     * @param unsupportedParams the unsupported params array
     */
    public void setUnsupportedParams(String[] unsupportedParams) {
        this.unsupportedParams = unsupportedParams;
    }

}
