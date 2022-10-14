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

import org.grad.secom.core.models.enums.AckRequestEnum;

import javax.validation.constraints.NotNull;

/**
 * The SECOM Data Response Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class DataResponseObject {

    // Class Variables
    @NotNull
    private String data;
    @NotNull
    private SECOM_ExchangeMetadataObject exchangeMetadata;
    @NotNull
    private AckRequestEnum ackRequest;

    /**
     * Instantiates a new Data response object.
     */
    public DataResponseObject() {
        this.exchangeMetadata = new SECOM_ExchangeMetadataObject();
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Gets exchange metadata.
     *
     * @return the exchange metadata
     */
    public SECOM_ExchangeMetadataObject getExchangeMetadata() {
        return exchangeMetadata;
    }

    /**
     * Sets exchange metadata.
     *
     * @param exchangeMetadata the exchange metadata
     */
    public void setExchangeMetadata(SECOM_ExchangeMetadataObject exchangeMetadata) {
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
