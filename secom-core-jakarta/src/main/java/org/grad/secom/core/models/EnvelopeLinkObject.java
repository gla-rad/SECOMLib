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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import org.grad.secom.core.base.GenericExchangeMetadataBearer;
import org.grad.secom.core.base.InstantDeserializer;
import org.grad.secom.core.base.InstantSerializer;
import org.grad.secom.core.models.enums.AckRequestEnum;
import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;

import jakarta.validation.constraints.NotNull;

import java.net.URL;
import java.time.Instant;
import java.util.UUID;

/**
 * The SECOM Envelope Link Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeLinkObject extends AbstractEnvelope implements GenericExchangeMetadataBearer {

    // Class Variables
    @NotNull
    private ContainerTypeEnum containerType;
    @NotNull
    @Schema(description = "Data product type name requested, e.g. S-124, S-421")
    private SECOM_DataProductType dataProductType;
    @JsonProperty
    @NotNull
    private ExchangeMetadata exchangeMetadata;
    @NotNull
    @Schema(description = "Flag to indicate whether the data has been uploaded within an active subscription or not.")
    private Boolean fromSubscription;
    @Schema(type = "string", description = "Subscription identifier if the object is uploaded within subscription.", example = "550e8400-e29b-41d4-a716-446655440000")
    @Pattern(regexp = "^[{(]?[0-9a-fA-F]{8}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{12}[)}]?$")
    private UUID subscriptionIdentifier;
    @NotNull
    private AckRequestEnum ackRequest;
    @NotNull
    @Schema(type = "string", description = "URL to the requestor\r\nEndpoint where to send an acknowledgement.\r\nIf not availalble, the endpoint where to send an acknowledgement need to be available in service registry lookup.", example = "https://example.com")
    @Pattern(regexp = "^(https?|ftp):\\/\\/[^\\s/$.?#].[^\\s]*$")
    private URL callbackEndpoint;
    @NotNull
    @Schema(type = "string", description = "Transaction identifier to be used in acknowledgement")
    @Pattern(regexp = "^[{(]?[0-9a-fA-F]{8}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{12}[)}]?$")
    private UUID transactionIdentifier;
    @NotNull
    @Schema(description = "Approximated maximum size of the data file in kBytes to be downloaded.")
    private Integer size;
    @NotNull
    @Schema(type = "string", description = "DateTime when data will be deleted on server. The data need to be fetched before this time. Must be in UTC format: yyyy-MM-ddTHH:mm:ssZ.", example = "2025-04-28T14:30:00Z")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$")
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant timeToLive;

    /**
     * Instantiates a new Envelope link object.
     */
    public EnvelopeLinkObject() {
        this.exchangeMetadata = new ExchangeMetadata();
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
     * Gets exchange metadata.
     *
     * @return the exchange metadata
     */
    public ExchangeMetadata getExchangeMetadata() {
        return exchangeMetadata;
    }

    /**
     * Sets exchange metadata.
     *
     * @param exchangeMetadata the exchange metadata
     */
    public void setExchangeMetadata(ExchangeMetadata exchangeMetadata) {
        this.exchangeMetadata = exchangeMetadata;
    }

    /**
     * Gets from subscription.
     *
     * @return the from subscription
     */
    public Boolean getFromSubscription() {
        return fromSubscription;
    }

    /**
     * Sets from subscription.
     *
     * @param fromSubscription the from subscription
     */
    public void setFromSubscription(Boolean fromSubscription) {
        this.fromSubscription = fromSubscription;
    }

    /**
     * Get subscription identifier
     *
     * @return subscriptionIdentifier
     */
    public UUID getSubscriptionIdentifier() { return subscriptionIdentifier; }

    /**
     *  Sets subscription identifier
     *
     * @param subscriptionIdentifier the subscription identifier
     */
    public void setSubscriptionIdentifier(UUID subscriptionIdentifier) { this.subscriptionIdentifier = subscriptionIdentifier; }


    /**
     * Gets ack request.
     *
     * @return the ack request
     */
    public AckRequestEnum getAckRequest() {
        return ackRequest;
    }


    /**
     * Sets ack request.
     *
     * @param ackRequest the ack request
     */
    public void setAckRequest(AckRequestEnum ackRequest) {
        this.ackRequest = ackRequest;
    }


    /**
     *  Gets the callback endpoint
     *
     * @return the callback endpoint
     */
    public URL getCallbackEndpoint() { return callbackEndpoint; }

    /**
     * Sets the callback endpoint
     *
     * @param callbackEndpoint the callback endpoint
     */
    public void setCallbackEndpoint(URL callbackEndpoint) { this.callbackEndpoint = callbackEndpoint; }


    /**
     * Gets transaction identifier.
     *
     * @return the transaction identifier
     */
    public UUID getTransactionIdentifier() {
        return transactionIdentifier;
    }

    /**
     * Sets transaction identifier.
     *
     * @param transactionIdentifier the transaction identifier
     */
    public void setTransactionIdentifier(UUID transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Gets time to live.
     *
     * @return the time to live
     */
    public Instant getTimeToLive() {
        return timeToLive;
    }

    /**
     * Sets time to live.
     *
     * @param timeToLive the time to live
     */
    public void setTimeToLive(Instant timeToLive) {
        this.timeToLive = timeToLive;
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
                exchangeMetadata,
                fromSubscription,
                subscriptionIdentifier,
                ackRequest,
                callbackEndpoint,
                transactionIdentifier,
                envelopeSignatureCertificate,
                envelopeRootCertificateThumbprint,
                size,
                timeToLive,
                envelopeSignatureTime
        };
    }
}
