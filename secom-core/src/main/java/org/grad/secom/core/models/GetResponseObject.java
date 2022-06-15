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

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Base64;

/**
 * The SECOM Get Response Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class GetResponseObject {

    // Class Variables
    private DataResponseObject dataResponseObject;
    @NotNull
    private PaginationObject pagination;
    private String responseText;

    /**
     * Gets data response object.
     *
     * @return the data response object
     */
    public DataResponseObject getDataResponseObject() {
        return dataResponseObject;
    }

    /**
     * Sets data response object.
     *
     * @param dataResponseObject the data response object
     */
    public void setDataResponseObject(DataResponseObject dataResponseObject) {
        this.dataResponseObject = dataResponseObject;
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
     * A helper function that automatically encodes the provided data into
     * a Base64 string.
     *
     * @param payloadObject the object to be encoded and assigned to the data
     */
    @JsonIgnore
    public void encodeData(String payloadObject, SECOM_ExchangeMetadata exchangeMetadata) {
        if(this.dataResponseObject == null) {
            this.dataResponseObject = new DataResponseObject();
        }
        this.dataResponseObject.setData(Base64.getEncoder().encodeToString(payloadObject.getBytes()));
        this.dataResponseObject.setExchangeMetadata(exchangeMetadata);
    }

    /**
     * A helper function that automatically decodes the Base 64 data into the
     * required string.
     *
     * @return the decoded data string
     * @throws IOException the io exception
     */
    @JsonIgnore
    public String decodeData() throws IOException {
        if(this.dataResponseObject == null) {
            return null;
        }
        return new String(Base64.getDecoder().decode(this.dataResponseObject.getData()));
    }
}
