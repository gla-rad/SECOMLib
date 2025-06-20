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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import org.grad.secomv2.core.base.SecomByteArrayDeSerializer;
import org.grad.secomv2.core.base.SecomByteArraySerializer;
import org.grad.secomv2.core.base.DigitalSignatureBearer;
import org.grad.secomv2.core.models.enums.AckRequestEnum;
import org.grad.secomv2.core.models.enums.ContainerTypeEnum;
import org.grad.secomv2.core.models.enums.SECOM_DataProductType;

import jakarta.validation.constraints.NotNull;

import java.net.URL;
import java.util.UUID;

/**
 * The SECOM Envelope Upload Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeUploadObject extends AbstractEnvelope implements DigitalSignatureBearer {

    // Class Variables
    @JsonProperty
    @Schema(type = "string", format = "byte", description = "The payload XML (e.g. S100_ExchangeSet, S100_DataSet), ZIP or binary The data can be open, protected and/or compressed.")
    @JsonSerialize(using = SecomByteArraySerializer.class)
    @JsonDeserialize(using = SecomByteArrayDeSerializer.class)
    @NotNull
    private byte[] data;
    @NotNull
    private ContainerTypeEnum containerType;
    @NotNull
    @Schema(description = "Data product type name requested, e.g. S-124, S-421")
    private SECOM_DataProductType dataProductType;
    @JsonProperty
    @NotNull
    private ExchangeMetadata exchangeMetadata;
    @Schema(description = "Flag to indicate whether the data has been uploaded within an active subscription or not.")
    private Boolean fromSubscription;
    @Schema(type= "string", description = "Subscription identifier if the object is uploaded within subscription.", example = "550e8400-e29b-41d4-a716-446655440000")
    @Pattern(regexp = "^[{(]?[0-9a-fA-F]{8}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{12}[)}]?$")
    private UUID subscriptionIdentifier;
    @NotNull
    private AckRequestEnum ackRequest;
    @NotNull
    @Schema(type = "string", description = "URL to the requestor\r\nEndpoint where to send an acknowledgement.\r\nIf not availalble, the endpoint where to send an acknowledgement need to be available in service registry lookup.", example = "https://example.com")
    @Pattern(regexp = "^(https?|ftp):\\/\\/[^\\s/$.?#].[^\\s]*$")
    private URL callbackEndpoint;
    @NotNull
    @Schema(type= "string", description = "Transaction identifier to be used in acknowledgement", example = "550e8400-e29b-41d4-a716-446655440000")
    @Pattern(regexp = "^[{(]?[0-9a-fA-F]{8}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{4}[-]?[0-9a-fA-F]{12}[)}]?$")
    private UUID transactionIdentifier;

    /**
     * Instantiates a new Envelope upload object.
     */
    public EnvelopeUploadObject() {
        this.exchangeMetadata = new ExchangeMetadata();
    }

    /**
     * Get data.
     *
     * @return the data
     */
    @Override
    public byte[] getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    @Override
    public void setData(byte[] data) {
        this.data = data;
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
    @Override
    public ExchangeMetadata getExchangeMetadata() {
        return exchangeMetadata;
    }

    /**
     * Sets exchange metadata.
     *
     * @param exchangeMetadata the exchange metadata
     */
    @Override
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
     * This method should be implemented by all envelop objects to allow the
     * generation of the signature CSV attribute array
     *
     * @return the generated signature CSV attribute array
     */
    @Override
    public Object[] getAttributeArray() {
        return new Object[] {
                data,
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
                envelopeSignatureTime
        };
    }

}
