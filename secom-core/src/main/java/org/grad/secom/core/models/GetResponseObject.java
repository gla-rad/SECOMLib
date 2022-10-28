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
import org.grad.secom.core.base.DigitalSignatureBearer;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

/**
 * The SECOM Get Response Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class GetResponseObject implements DigitalSignatureBearer {

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
     * This function allows access to the data payload of the data bearer.
     *
     * @return the data payload of the data bearer
     */
    @Override
    public String getData() {
        return Optional.of(this)
                .map(GetResponseObject::getDataResponseObject)
                .map(DataResponseObject::getData)
                .orElse(null);
    }

    /**
     * This function allows updating the data payload of the data bearer.
     *
     * @param data the data payload of the data bearer
     */
    @Override
    public void setData(String data) {
        Optional.of(this)
                .map(GetResponseObject::getDataResponseObject)
                .ifPresent(dro -> dro.setData(data));
    }

    /**
     * Allows the get response object to access the SECOM exchange
     * metadata that will contain the signature information.
     *
     * @return the signature information of the data signature bearer
     */
    @Override
    public SECOM_ExchangeMetadataObject getExchangeMetadata() {
        return Optional.of(this)
                .map(GetResponseObject::getDataResponseObject)
                .map(DataResponseObject::getExchangeMetadata)
                .orElse(null);
    }

    /**
     * Allows the get response bearer object to update the SECOM exchange
     * metadata that will contain amongst other info, the signature
     * information.
     *
     * @param exchangeMetadata  the SECOM Exchange metadata
     */
    @Override
    public void setExchangeMetadata(SECOM_ExchangeMetadataObject exchangeMetadata) {
        Optional.of(this)
                .map(GetResponseObject::getDataResponseObject)
                .ifPresent(dro -> dro.setExchangeMetadata(exchangeMetadata));
    }

}
