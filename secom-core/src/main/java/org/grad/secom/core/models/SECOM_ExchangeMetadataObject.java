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

import org.grad.secom.core.base.CsvStringGenerator;
import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;

import javax.validation.constraints.NotNull;

/**
 * The SECOM Exchange Metadata Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SECOM_ExchangeMetadataObject implements CsvStringGenerator {

    // Class Variables
    @NotNull
    private Boolean dataProtection;
    @NotNull
    private String protectionScheme;
    @NotNull
    private DigitalSignatureAlgorithmEnum digitalSignatureReference;
    @NotNull
    private DigitalSignatureValue digitalSignatureValue;
    @NotNull
    private Boolean compressionFlag;

    /**
     * Gets data protection.
     *
     * @return the data protection
     */
    public Boolean getDataProtection() {
        return dataProtection;
    }

    /**
     * Sets data protection.
     *
     * @param dataProtection the data protection
     */
    public void setDataProtection(Boolean dataProtection) {
        this.dataProtection = dataProtection;
    }

    /**
     * Gets protection scheme.
     *
     * @return the protection scheme
     */
    public String getProtectionScheme() {
        return protectionScheme;
    }

    /**
     * Sets protection scheme.
     *
     * @param protectionScheme the protection scheme
     */
    public void setProtectionScheme(String protectionScheme) {
        this.protectionScheme = protectionScheme;
    }

    /**
     * Gets digital signature reference.
     *
     * @return the digital signature reference
     */
    public DigitalSignatureAlgorithmEnum getDigitalSignatureReference() {
        return digitalSignatureReference;
    }

    /**
     * Sets digital signature reference.
     *
     * @param digitalSignatureReference the digital signature reference
     */
    public void setDigitalSignatureReference(DigitalSignatureAlgorithmEnum digitalSignatureReference) {
        this.digitalSignatureReference = digitalSignatureReference;
    }

    /**
     * Gets digital signature value.
     *
     * @return the digital signature value
     */
    public DigitalSignatureValue getDigitalSignatureValue() {
        return digitalSignatureValue;
    }

    /**
     * Sets digital signature value.
     *
     * @param digitalSignatureValue the digital signature value
     */
    public void setDigitalSignatureValue(DigitalSignatureValue digitalSignatureValue) {
        this.digitalSignatureValue = digitalSignatureValue;
    }

    /**
     * Gets compression flag.
     *
     * @return the compression flag
     */
    public Boolean getCompressionFlag() {
        return compressionFlag;
    }

    /**
     * Sets compression flag.
     *
     * @param compressionFlag the compression flag
     */
    public void setCompressionFlag(Boolean compressionFlag) {
        this.compressionFlag = compressionFlag;
    }

    /**
     * This method should be implemented by all envelop objects to allow the
     * generation of the signature CSV attribute array
     *
     * @return the generated signature CSV attribute array
     */
    @Override
    public Object[] getAttributeArray() {
        // Create the CSV array
        return new Object[] {
                dataProtection,
                protectionScheme,
                digitalSignatureReference,
                digitalSignatureValue,
                compressionFlag
        };
    }
}
