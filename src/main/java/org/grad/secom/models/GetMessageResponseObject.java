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

package org.grad.secom.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.IOException;
import java.util.Base64;

/**
 * The SECOM Get Message Response Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class GetMessageResponseObject {

    // Class Variables
    private String payload;
    private ExchangeMetadata exchangeMetadata;
    private PaginationObject pagination;

    /**
     * Instantiates a new Get message response object.
     */
    public GetMessageResponseObject() {

    }

    /**
     * Instantiates a new Get message response object.
     *
     * @param payload the payload
     */
    public GetMessageResponseObject(String payload) {
        this.encodePayload(payload);
    }

    /**
     * Gets payload.
     *
     * @return the payload
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Sets payload.
     *
     * @param payload the payload
     */
    public void setPayload(String payload) {
        this.payload = payload;
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
     * Gets pagination.
     *
     * @return the pagination
     */
    public PaginationObject getPagination() {
        return pagination;
    }

    /**
     * Sets pagination.
     *
     * @param pagination the pagination
     */
    public void setPagination(PaginationObject pagination) {
        this.pagination = pagination;
    }

    /**
     * A helper function that automatically encodes the provided object into
     * a Base64 string.
     *
     * @param payloadObject the object to be encoded and assigned to the payload
     */
    @JsonIgnore
    public void encodePayload(String payloadObject) {
        this.payload = Base64.getEncoder().encodeToString(payloadObject.getBytes());
    }

    /**
     * A helper function that automatically decodes the Base 64 payload into the
     * required string.
     */
    @JsonIgnore
    public String decodePayload() throws IOException {
        return new String(Base64.getDecoder().decode(this.payload));
    }
}
