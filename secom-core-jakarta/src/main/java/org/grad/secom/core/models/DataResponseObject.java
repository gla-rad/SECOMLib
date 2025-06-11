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
import org.grad.secom.core.base.ByteArrayDeSerializer;
import org.grad.secom.core.base.ByteArraySerializer;
import org.grad.secom.core.base.DigitalSignatureBearer;
import org.grad.secom.core.models.enums.AckRequestEnum;

import jakarta.validation.constraints.NotNull;

/**
 * The SECOM Data Response Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class DataResponseObject implements DigitalSignatureBearer {

    // Class Variables
    @JsonProperty
    @NotNull
    @Schema(type = "string", format = "byte")
    @JsonSerialize(using = ByteArraySerializer.class)
    @JsonDeserialize(using = ByteArrayDeSerializer.class)
    private byte[] data;
    @JsonProperty
    @NotNull
    private ExchangeMetadata exchangeMetadata;
    @NotNull
    private AckRequestEnum ackRequest;

    /**
     * Instantiates a new Data response object.
     */
    public DataResponseObject() {
        this.exchangeMetadata = new ExchangeMetadata();
        this.ackRequest = AckRequestEnum.NO_ACK_REQUESTED;
    }

    /**
     * Gets data.
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
}
