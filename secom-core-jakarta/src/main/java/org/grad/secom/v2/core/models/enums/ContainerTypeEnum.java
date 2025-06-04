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

package org.grad.secom.v2.core.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

/**
 * The SECOM Container Type Enum.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Schema(implementation = Integer.class)
public enum ContainerTypeEnum implements SECOM_Enum {
    S100_DataSet(0),
    S100_ExchangeSet(1),
    NONE(2);

    // Enum Variables
    private final int value;

    /**
     * Enum Constructor
     *
     * @param newValue the enum value
     */
    ContainerTypeEnum(final int newValue) {
        value = newValue;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    @JsonValue
    public int getValue() { return value; }

    /**
     * Find the enum entry that corresponds to the provided value.
     *
     * @param value the enum value
     * @return The respective enum entry
     */
    public static ContainerTypeEnum fromValue(int value) {
        return Arrays.stream(ContainerTypeEnum.values())
                .filter(t -> t.getValue() == value)
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
        return String.valueOf(this.value);
    }
}
