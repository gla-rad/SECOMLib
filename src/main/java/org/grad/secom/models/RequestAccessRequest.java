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

import org.grad.secom.models.enums.DataTypeEnum;
import org.grad.secom.models.enums.ReasonEnum;

import javax.validation.constraints.NotNull;

/**
 * The SECOM Request Access Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class RequestAccessRequest {

    // Class Variables
    @NotNull
    private String reason;
    @NotNull
    private ReasonEnum reasonEnum;
    private DataTypeEnum dataType;
    private S100ProductSpecification productSpecification;

    /**
     * Gets reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets reason.
     *
     * @param reason the reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets reason enum.
     *
     * @return the reason enum
     */
    public ReasonEnum getReasonEnum() {
        return reasonEnum;
    }

    /**
     * Sets reason enum.
     *
     * @param reasonEnum the reason enum
     */
    public void setReasonEnum(ReasonEnum reasonEnum) {
        this.reasonEnum = reasonEnum;
    }

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
}
