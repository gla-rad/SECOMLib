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
import org.grad.secom.core.base.DigitalSignatureBearer;
import org.grad.secom.core.base.ByteArrayDeSerializer;
import org.grad.secom.core.base.ByteArraySerializer;
import org.grad.secom.core.models.enums.AckRequestEnum;
import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * The SECOM Envelope Upload Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeUploadObject extends AbstractEnvelope implements DigitalSignatureBearer {

    // Class Variables
    @JsonProperty
    @Schema(type = "string", format = "byte")
    @JsonSerialize(using = ByteArraySerializer.class)
    @JsonDeserialize(using = ByteArrayDeSerializer.class)
    @NotNull
    private byte[] data;
    @NotNull
    private ContainerTypeEnum containerType;
    @NotNull
    private SECOM_DataProductType dataProductType;
    @JsonProperty
    @NotNull
    private SECOM_ExchangeMetadataObject exchangeMetadata;
    private Boolean fromSubscription;
    private AckRequestEnum ackRequest;
    @NotNull
    private UUID transactionIdentifier;

    /**
     * Instantiates a new Envelope upload object.
     */
    public EnvelopeUploadObject() {
        this.exchangeMetadata = new SECOM_ExchangeMetadataObject();
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
    public SECOM_ExchangeMetadataObject getExchangeMetadata() {
        return exchangeMetadata;
    }

    /**
     * Sets exchange metadata.
     *
     * @param exchangeMetadata the exchange metadata
     */
    @Override
    public void setExchangeMetadata(SECOM_ExchangeMetadataObject exchangeMetadata) {
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
                ackRequest,
                transactionIdentifier,
                envelopeSignatureCertificate,
                envelopeRootCertificateThumbprint,
                envelopeSignatureTime
        };
    }

}
