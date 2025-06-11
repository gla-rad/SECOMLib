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

package org.grad.secom.core.models;

import io.swagger.v3.oas.annotations.media.Schema;
import org.grad.secom.core.base.CsvStringGenerator;
import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;

import javax.validation.constraints.NotNull;

/**
 * The SECOM Exchange Metadata Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class ExchangeMetadata implements CsvStringGenerator {

    // Class Variables
    @NotNull
    @Schema(description = "(S-100) Indicates if the data is encrypted. \\r\\n0 indicates unencrypted data\\r\\n1 indicates encrypted data")
    private Boolean dataProtection;
    @NotNull
    @Schema(description = "S-100) Specification or method used for data protection Such as S-63, SECOM")
    private String protectionScheme;
    @NotNull
    @Schema(description = "(S-100) Specifies the algorithm used to compute digitalSignatureValue\\r\\nFor example \\\"ECDSA-384-SHA2\\\"")
    private DigitalSignatureAlgorithmEnum digitalSignatureReference;
    @NotNull
    private DigitalSignatureValueObject digitalSignatureValueObject;
    @NotNull
    @Schema(description = "(S-100) Indicates if data is compressed.\\r\\n0 indicates uncompressed\\r\\n1 indicates compressed")
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
    public DigitalSignatureValueObject getDigitalSignatureValue() {
        return digitalSignatureValueObject;
    }

    /**
     * Sets digital signature value.
     *
     * @param digitalSignatureValueObject the digital signature value
     */
    public void setDigitalSignatureValue(DigitalSignatureValueObject digitalSignatureValueObject) {
        this.digitalSignatureValueObject = digitalSignatureValueObject;
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
                digitalSignatureValueObject,
                compressionFlag
        };
    }
}
