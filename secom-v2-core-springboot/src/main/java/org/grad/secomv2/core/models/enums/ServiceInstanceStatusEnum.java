/*
 * Copyright (c) 2026 GLA Research and Development Directorate
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

package org.grad.secomv2.core.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.grad.secomv2.core.exceptions.SecomValidationException;

/**
 * The SECOM ServiceInstanceStatus Enum.
 *
 * @author Jakob Svenningsen (email: jakob@dmc.international)
 * 0 = Provisional
 * 1 = Released
 * 2 = Deprecated
 * 3 = Deleted
 */
public enum ServiceInstanceStatusEnum implements SECOM_Enum {

    PROVISIONAL(0),
    RELEASED(1),
    DEPRECATED(2),
    DELETED(3);

    private final int value;

    ServiceInstanceStatusEnum(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @Override
    public String asString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static ServiceInstanceStatusEnum fromValue(int value) {
        switch (value) {
            case 0: return PROVISIONAL;
            case 1: return RELEASED;
            case 2: return DEPRECATED;
            case 3: return DELETED;
            default:
                throw new SecomValidationException(
                        "Unknown ServiceInstanceStatus value: " + value);
        }
    }
}
