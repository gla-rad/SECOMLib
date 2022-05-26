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

package org.grad.secom.models.enums;


import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The Info Status Enum Class.
 *
 * This is a custom implementation for the status options of an object, to
 * make life a bit easier.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public enum InfoStatusEnum {
    PRESENT("present"),
    DELETED("deleted"),
    INVALID("invalid"),
    DEPRECATED("deprecated"),
    ERROR("error");

    // Enum Variables
    private final String value;

    /**
     * Enum Constructor
     *
     * @param newValue the enum value
     */
    InfoStatusEnum(final String newValue) {
        value = newValue;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    @JsonValue
    public String getValue() { return value; }

}
