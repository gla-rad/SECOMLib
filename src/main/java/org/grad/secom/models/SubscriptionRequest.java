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

import org.grad.secom.models.enums.AreaNameEnum;
import org.grad.secom.models.enums.DataTypeEnum;
import javax.validation.constraints.NotNull;

/**
 * The SECOM Subscription Request Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SubscriptionRequest {

    // Class Variables
    private DataTypeEnum dataType;
    private S100ProductSpecification productSpecification;
    private String geometry;
    private AreaNameEnum areaName;
    private String unlocode;

    /**
     * Gets data type.
     *
     * @return the data type
     */
    public DataTypeEnum getDataType() {
        return dataType;
    }

    /**
     * Sets data type.
     *
     * @param dataType the data type
     */
    public void setDataType(DataTypeEnum dataType) {
        this.dataType = dataType;
    }

    /**
     * Gets product specification.
     *
     * @return the product specification
     */
    public S100ProductSpecification getProductSpecification() {
        return productSpecification;
    }

    /**
     * Sets product specification.
     *
     * @param productSpecification the product specification
     */
    public void setProductSpecification(S100ProductSpecification productSpecification) {
        this.productSpecification = productSpecification;
    }

    /**
     * Gets geometry.
     *
     * @return the geometry
     */
    public String getGeometry() {
        return geometry;
    }

    /**
     * Sets geometry.
     *
     * @param geometry the geometry
     */
    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    /**
     * Gets area name.
     *
     * @return the area name
     */
    public AreaNameEnum getAreaName() {
        return areaName;
    }

    /**
     * Sets area name.
     *
     * @param areaName the area name
     */
    public void setAreaName(AreaNameEnum areaName) {
        this.areaName = areaName;
    }

    /**
     * Gets unlocode.
     *
     * @return the unlocode
     */
    public String getUnlocode() {
        return unlocode;
    }

    /**
     * Sets unlocode.
     *
     * @param unlocode the unlocode
     */
    public void setUnlocode(String unlocode) {
        this.unlocode = unlocode;
    }
}
