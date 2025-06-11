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

import org.grad.secom.core.base.DigitalSignatureBearer;
import org.grad.secom.core.base.DigitalSignatureCollectionBearer;

import jakarta.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The SECOM Get Response Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class GetResponseObject implements DigitalSignatureCollectionBearer {

    // Class Variables
    private List<DataResponseObject> dataResponseObject;
    @NotNull
    private PaginationObject pagination;

    /**
     * Gets data response object.
     *
     * @return the data response object
     */
    public List<DataResponseObject> getDataResponseObject() {
        return dataResponseObject;
    }

    /**
     * Sets data response object.
     *
     * @param dataResponseObject the data response object
     */
    public void setDataResponseObject(List<DataResponseObject> dataResponseObject) {
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
     * Gets digital signature collection.
     *
     * @return the digital signature collection
     */
    @Override
    public Collection<DigitalSignatureBearer> getDigitalSignatureCollection() {
        return Optional.of(this)
                .map(GetResponseObject::getDataResponseObject)
                .orElse(Collections.emptyList())
                .stream()
                .map(DigitalSignatureBearer.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Sets digital signature collection.
     *
     * @param digitalSignatureCollection the digital signature collection
     */
    @Override
    public void setDigitalSignatureCollection(Collection<DigitalSignatureBearer> digitalSignatureCollection) {
        this.dataResponseObject = new ArrayList(digitalSignatureCollection);
    }
}
