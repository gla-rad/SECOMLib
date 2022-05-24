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

import org.grad.secom.models.enums.ClassificationEnum;

/**
 * The SECOM Exchange Metadata Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class ExchangeMetadata {

    // Class Variables
    private boolean dataProtection;
    private String protectionScheme;
    private String digitalSignatureReference;
    private DigitalSignatureValue digitalSignatureValue;
    private ClassificationEnum classification;
    private boolean compressionFlag;

    /**
     * Is data protection boolean.
     *
     * @return the boolean
     */
    public boolean isDataProtection() {
        return dataProtection;
    }

    /**
     * Sets data protection.
     *
     * @param dataProtection the data protection
     */
    public void setDataProtection(boolean dataProtection) {
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
    public String getDigitalSignatureReference() {
        return digitalSignatureReference;
    }

    /**
     * Sets digital signature reference.
     *
     * @param digitalSignatureReference the digital signature reference
     */
    public void setDigitalSignatureReference(String digitalSignatureReference) {
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
     * Gets classification.
     *
     * @return the classification
     */
    public ClassificationEnum getClassification() {
        return classification;
    }

    /**
     * Sets classification.
     *
     * @param classification the classification
     */
    public void setClassification(ClassificationEnum classification) {
        this.classification = classification;
    }

    /**
     * Is compression flag boolean.
     *
     * @return the boolean
     */
    public boolean isCompressionFlag() {
        return compressionFlag;
    }

    /**
     * Sets compression flag.
     *
     * @param compressionFlag the compression flag
     */
    public void setCompressionFlag(boolean compressionFlag) {
        this.compressionFlag = compressionFlag;
    }
}
