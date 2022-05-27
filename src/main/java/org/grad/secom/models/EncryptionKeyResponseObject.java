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

import org.grad.secom.models.enums.SECOM_ResponseCodeEnum;

/**
 * The SECOM Encryption Key Response Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EncryptionKeyResponseObject {

    // Class Variables
    private SECOM_ResponseCodeEnum SECOM_ResponseCode;
    private String responseText;

    /**
     * Gets secom response code.
     *
     * @return the secom response code
     */
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

}
