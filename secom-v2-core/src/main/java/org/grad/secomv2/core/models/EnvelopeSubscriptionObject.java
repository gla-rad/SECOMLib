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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.grad.secomv2.core.base.SecomInstantDeserializer;
import org.grad.secomv2.core.base.SecomInstantSerializer;
import org.grad.secomv2.core.models.enums.ContainerTypeEnum;
import org.grad.secomv2.core.models.enums.SECOM_DataProductType;

import javax.validation.constraints.Pattern;
import java.time.Instant;
import java.util.UUID;

/**
 * The SECOM Envelope Subscription Request Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeSubscriptionObject {

    // Class Variables
    private ContainerTypeEnum containerType;
    @Schema(description = "Data product type name requested, e.g. S-124, S-421")
    private SECOM_DataProductType dataProductType;
    @Schema(description = "Reference to data")
    private UUID dataReference;
    @Schema(description = "S-100 based Product type version requested, e.g. 1.0.0")
    private String productVersion;
    @Schema(type = "string", description = "Geometry condition for geolocated information objects", example = "POLYGON ((0.65 51.42, 0.65 52.26, 2.68 52.26, 2.68 51.42, 0.65 51.42))")
    @Pattern(regexp = "^([A-Z]+\\s*\\(\\(?\\s*(-?\\d+(\\.\\d+)?)\\s+-?\\d+(\\.\\d+)?(?:\\s+-?\\d+(\\.\\d+)?)?\\s*(,\\s*(-?\\d+(\\.\\d+)?)\\s+-?\\d+(\\.\\d+)?(?:\\s+-?\\d+(\\.\\d+)?)?\\s*)*\\)\\)?\\s*)+$")
    private String geometry;
    @Schema(description = "Code of defined object", type = "string", example = "GBHRW")
    @Pattern(regexp = "[A-Z]{5}")
    private String unlocode;
    @Schema(format = "date-time", description = "Start time of subscription Must be in UTC format: yyyy-MM-ddTHH:mm:ssZ.", type = "string",example = "1985-04-12T10:15:30Z", pattern =  "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(Z|\\+\\d{4})?")
    @JsonSerialize(using = SecomInstantSerializer.class)
    @JsonDeserialize(using = SecomInstantDeserializer.class)
    private Instant subscriptionPeriodStart;
    @Schema(format = "date-time", description = "End time of subscription Must be in UTC format: yyyy-MM-ddTHH:mm:ssZ.", type = "string",example = "1985-04-12T10:15:30Z", pattern =  "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(Z|\\+\\d{4})?")
    @JsonSerialize(using = SecomInstantSerializer.class)
    @JsonDeserialize(using = SecomInstantDeserializer.class)
    private Instant subscriptionPeriodEnd;
    @Schema(type = "string", format = "uri", description = "URL to the requestor\r\nEndpoint where to send an acknowledgement.\r\nIf not availalble, the endpoint where to send an acknowledgement need to be available in service registry lookup.", example = "https://example.com")
    @Pattern(regexp = "^(https?|ftp):\\/\\/[^\\s/$.?#].[^\\s]*$")
    private String callbackEndpoint;
    @Schema(description = "Flag for sending an initial set of data within requested subscription and then start the subscription.\r\nIf false, only data updates from the start of the subscription will be sent.")
    private Boolean pushAll;

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
     * Gets subscription period start.
     *
     * @return the subscription period start
     */
    public Instant getSubscriptionPeriodStart() {
        return subscriptionPeriodStart;
    }

    /**
     * Sets subscription period start.
     *
     * @param subscriptionPeriodStart the subscription period start
     */
    public void setSubscriptionPeriodStart(Instant subscriptionPeriodStart) {
        this.subscriptionPeriodStart = subscriptionPeriodStart;
    }

    /**
     * Gets subscription period end.
     *
     * @return the subscription period end
     */
    public Instant getSubscriptionPeriodEnd() {
        return subscriptionPeriodEnd;
    }

    /**
     * Sets subscription period end.
     *
     * @param subscriptionPeriodEnd the subscription period end
     */
    public void setSubscriptionPeriodEnd(Instant subscriptionPeriodEnd) {
        this.subscriptionPeriodEnd = subscriptionPeriodEnd;
    }

    /**
     * Gets the callback endpoint
     *
     * @return callbackEndPoint the callback endpoint
     */
    public String getCallbackEndpoint() { return callbackEndpoint; }

    /**
     * Sets the callback endpoint
     *
     * @param callbackEndpoint the callback endpoint
     */
    public void setCallbackEndpoint(String callbackEndpoint) { this.callbackEndpoint = callbackEndpoint; }

    /**
     * Gets push all
     *
     * @return push all
     */
    public Boolean getPushAll() { return pushAll; }

    /**
     * Sets push all
     *
     * @param pushAll push all
     */
    public void setPushAll(Boolean pushAll) { this.pushAll = pushAll; }

}
