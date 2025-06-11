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

package org.grad.secomv2.core.models.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * The SECOM Service Type Enum.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public enum SECOM_ServiceType implements SECOM_Enum {
    VTS_INFORMATION_SERVICE("MS 1 - VTS Information service (INS)"),
    VTS_NAVIGATIONAL_ASSISTANCE_SERVICE("MS 2 - VTS Navigational assistance service (NAS)"),
    TRAFFIC_ORGANIZATION_SERVICE("MS 3 - Traffic organization service (TOS)"),
    PORT_SUPPORT_SERVICE("MS 4 - Port support service (PSS)"),
    MARITIME_SAFETY_INFORMATION_SERVICE("MS 5 - Maritime safety information (MSI) service"),
    PILOTAGE_SERVICE("MS 6 - Pilotage service"),
    TUG_SERVICE("MS 7 - Tug service"),
    VESSEL_SHORE_REPORTING("MS 8 - Vessel shore reporting"),
    TELEMEDICAL_ASSISTANCE_SERVICE("MS 9 - Telemedical assistance service (TMAS)"),
    MARITIME_ASSISTANCE_SERVICE("MS 10 - Maritime assistance service (MAS)"),
    NAUTICAL_CHART_SERVICE("MS 11 - Nautical chart service"),
    NAUTICAL_PUBLICATIONS_SERVICE("MS 12 - Nautical publications service"),
    ICE_NAVIGATION_SERVICE("MS 13 - Ice navigation service"),
    METEOROLOGICAL_INFORMATION_SERVICE("MS 14 - Meteorological information service"),
    REAL_TIME_HYDROGRAPHIC_AND_ENVIRONMENTAL_INFORMATION_SERVICES("MS 15 - Real-time hydrographic and environmental information services"),
    SEARCH_AND_RESCUE_SERVICE("MS 16 - Search and rescue (SAR) service"),
    OTHER_SERVICE("Other Service");

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
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Find the enum entry that corresponds to the provided description.
     *
     * @param description the enum description
     * @return The respective enum entry
     */
    public static SECOM_ServiceType fromDescription(String description) {
        return Arrays.stream(SECOM_ServiceType.values())
                .filter(t -> Objects.equals(t.getDescription(), description))
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
