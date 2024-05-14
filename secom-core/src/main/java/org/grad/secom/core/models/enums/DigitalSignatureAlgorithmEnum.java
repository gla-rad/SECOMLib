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

package org.grad.secom.core.models.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

/**
 * The Digital Signature Algorithm Enum.
 *
 * This enumeration describes the algorithms supported for generating SECOM
 * signatures.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public enum DigitalSignatureAlgorithmEnum implements SECOM_Enum {
    @JsonProperty("dsa")
    DSA("SHA3-384withDSA"),
    @JsonProperty("ECDSA-256-SHA2-256")
    SHA2_256_WITH_ECDSA("SHA256withECDSA"),
    @JsonProperty("ECDSA-256-SHA3-256")
    SHA3_256_WITH_ECDSA("SHA3-256withECDSA"),
    @JsonProperty("ECDSA-384-SHA2")
    SHA2_384_WITH_ECDSA("SHA384withECDSA"),
    @JsonProperty("ECDSA-384-SHA3")
    SHA3_384_WITH_ECDSA("SHA3-384withECDSA"),
    @JsonProperty("cvc_ecdsa")
    CVC_ECDSA("SHA-384withCVC-ECDSA");

    // Enum Variables
    private final String value;

    /**
     * Enum Constructor.
     *
     * @param value     the Java signature instance algorithm to be used
     */
    DigitalSignatureAlgorithmEnum(String value) {
        this.value = value;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Find the enum entry that corresponds to the provided value.
     *
     * @param value     the enum value
     * @return The respective enum entry
     */
    public static DigitalSignatureAlgorithmEnum fromValue(String value) {
        return Arrays.stream(DigitalSignatureAlgorithmEnum.values())
                .filter(t -> t.getValue().compareTo(value) == 0)
                .findFirst()
                .orElse(null);
    }

    /**
     * The conversion to a string operation.
     *
     * @return the SECOM string representation of the enum
     */
    @Override
    public String asString() {
        return this.name().toLowerCase();
    }
}
