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

import java.util.Arrays;

/**
 * The SECOM Reason Enum.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public enum AreaNameEnum {
    METAREA_I(0),
    METAREA_II(1),
    METAREA_III(2),
    METAREA_IV(3),
    METAREA_V(4),
    METAREA_VI(5),
    METAREA_VII(6),
    METAREA_VIII_N(7),
    METAREA_VIII_S(8),
    METAREA_IX(9),
    METAREA_X(10),
    METAREA_XI(11),
    METAREA_XII(12),
    METAREA_XIII(13),
    METAREA_XIV(14),
    METAREA_XV(15),
    METAREA_XVI(16),
    METAREA_XVII(17),
    METAREA_XVIII(18),
    METAREA_XIX(19),
    METAREA_XX(20),
    METAREA_XXI(21),
    NAVAREA_I(22),
    NAVAREA_II(23),
    NAVAREA_III(24),
    NAVAREA_IV(25),
    NAVAREA_V(26),
    NAVAREA_VI(27),
    NAVAREA_VII(28),
    NAVAREA_VIII(29),
    NAVAREA_IX(30),
    NAVAREA_X(31),
    NAVAREA_XI(32),
    NAVAREA_XII(33),
    NAVAREA_XIII(34),
    NAVAREA_XIV(35),
    NAVAREA_XV(36),
    NAVAREA_XVI(37),
    NAVAREA_XVII(38),
    NAVAREA_XVIII(39),
    NAVAREA_XIX(40),
    NAVAREA_XX(41),
    NAVAREA_XXI(42);

    // Enum Variables
    private final int value;

    /**
     * Enum Constructor
     *
     * @param newValue the enum value
     */
    AreaNameEnum(final int newValue) {
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
    public static AreaNameEnum fromValue(int value) {
        return Arrays.stream(AreaNameEnum.values())
                .filter(t -> t.getValue() == value)
                .findFirst()
                .orElse(null);
    }
}
