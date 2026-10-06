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

import java.util.Arrays;
import java.util.Objects;

/**
 * The SECOM Service Type Enum.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public enum SECOM_ServiceType implements SECOM_Enum {
    MS1("MS 1 - VTS Service"),
    MS2("MS 2 - Aids to Navigation Service (AtoN)"),
    MS3("MS 3 - Reserved"),
    MS4("MS 4 - Port Support Service (PSS)"),
    MS5("MS 5 - Maritime Safety Information (MSI) Service"),
    MS6("MS 6 - Pilotage service"),
    MS7("MS 7 - Tug service"),
    MS8("MS 8 - Vessel shore reporting"),
    MS9("MS 9 - Telemedical assistance service (TMAS)"),
    MS10("MS 10 - Maritime assistance service (MAS)"),
    MS11("MS 11 - Nautical chart service"),
    MS12("MS 12 - Nautical publications service"),
    MS13("MS 13 - Ice navigation service"),
    MS14("MS 14 - Meteorological information service"),
    MS15("MS 15 - Real-time hydrographic and environmental information services"),
    MS16("MS 16 - Search and rescue (SAR) service"),
    OTHER("Any other maritime service");

    // Enum Variables
    private final String description;

    /**
     * Enum Constructor
     *
     * @param description the enum description
     */
    SECOM_ServiceType(final String description) {
        this.description = description;
    }

    /**
     * Gets value.
     value
     * @return the description
     */
    @JsonValue
    public String getValue() {
        return description;
    }

    /**
     * Find the enum entry that corresponds to the provided value.
     *
     * @param value the enum value
     * @return The respective enum entry
     */
    @JsonCreator
    public static SECOM_ServiceType fromValue(String value) {
        return Arrays.stream(SECOM_ServiceType.values())
                .filter(t -> Objects.equals(t.getValue(), value))
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
        return this.name();
    }
}
