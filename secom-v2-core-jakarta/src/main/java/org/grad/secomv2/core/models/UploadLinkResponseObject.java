/*
 * Copyright (c) 2025 GLA Research and Development Directorate
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

package org.grad.secomv2.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.grad.secomv2.core.models.enums.SECOM_ResponseCodeEnum;

/**
 * The SECOM Upload Link Response Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class UploadLinkResponseObject {

    // Class Variables
    private SECOM_ResponseCodeEnum SECOM_ResponseCode;
    private String message;

    /**
     * Gets secom response code.
     *
     * @return the secom response code
     */
    @JsonProperty("SECOM_ResponseCode")
    public SECOM_ResponseCodeEnum getSECOM_ResponseCode() {
        return SECOM_ResponseCode;
    }

    /**
     * Sets secom response code.
     *
     * @param SECOM_ResponseCode the secom response code
     */
    public void setSECOM_ResponseCode(SECOM_ResponseCodeEnum SECOM_ResponseCode) {
        this.SECOM_ResponseCode = SECOM_ResponseCode;
    }

    /**
     * Gets response text.
     *
     * @return the response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets response text.
     *
     * @param message the response message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
