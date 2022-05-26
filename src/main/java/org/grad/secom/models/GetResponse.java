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

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Base64;

/**
 * The SECOM Get Response Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class GetResponse {

    // Class Variables
    private DataResponse data;
    @NotNull
    private Pagination pagination;
    private String responseText;

    /**
     * Instantiates a new Get message response object.
     */
    public GetResponse() {

    }

    /**
     * Instantiates a new Get message response object.
     *
     * @param payload the payload
     */
    public GetResponse(String payload) {
        this.encodePayload(payload);
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public DataResponse getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(DataResponse data) {
        this.data = data;
    }

    /**
     * Gets pagination.
     *
     * @return the pagination
     */
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * Sets pagination.
     *
     * @param pagination the pagination
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    /**
     * Gets response text.
     *
     * @return the response text
     */
    public String getResponseText() {
        return responseText;
    }

    /**
     * Sets response text.
     *
     * @param responseText the response text
     */
    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    /**
     * A helper function that automatically encodes the provided object into
     * a Base64 string.
     *
     * @param payloadObject the object to be encoded and assigned to the payload
     */
    @JsonIgnore
    public void encodePayload(String payloadObject) {
        if(this.data == null) {
            this.data = new DataResponse();
        }
        this.data.setPayload(Base64.getEncoder().encodeToString(payloadObject.getBytes()));
    }

    /**
     * A helper function that automatically decodes the Base 64 payload into the
     * required string.
     *
     * @return the string
     * @throws IOException the io exception
     */
    @JsonIgnore
    public String decodePayload() throws IOException {
        if(this.data == null) {
            return null;
        }
        return new String(Base64.getDecoder().decode(this.data.getPayload()));
    }
}
