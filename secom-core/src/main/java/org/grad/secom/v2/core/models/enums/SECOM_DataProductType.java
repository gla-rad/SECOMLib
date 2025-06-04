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

import java.util.Arrays;
import java.util.Objects;

/**
 * The SECOM Response Code Enum.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public enum SECOM_DataProductType implements SECOM_Enum {
    OTHER("Other data types not covered in this table"),
    S57("S-57 Electronic Navigational Chart (ENC)"),
    S101("S-101 Electronic Navigational Chart (ENC)"),
    S102("S-102 Bathymetric Surface"),
    S104("S-104 Water Level Information for Surface Navigation"),
    S111("S-111 Surface Currents"),
    S122("S-122 Marine Protected Areas (MPAs)"),
    S123("S-123 Marine Radio Services"),
    S124("S-124 Navigational Warnings"),
    S125("S-125 Marine Navigational Services"),
    S126("S-126 Marine Physical Environment"),
    S127("S-127 Marine Traffic Management"),
    S128("S-128 Catalogue of Nautical Products"),
    S129("S-129 Under Keel Clearance Management (UKCM)"),
    S131("S-131 Marine Harbour Infrastructure"),
    S201("S-201 Aids to Navigation (AtoN) Information"),
    S210("S-210 Inter-VTS Exchange Format"),
    S211("S-211 Port Call Message Format"),
    S212("S-212 VTS Digital Information Service"),
    S401("S-401 Inland ENC"),
    S402("S-402 Bathymetric Contour Overlay for Inland ENC"),
    S411("S-411 Sea Ice Information"),
    S412("S-412 Weather Overlay"),
    S413("S-413 Marine Weather Conditions"),
    S414("S-414 Marine Weather Observations"),
    S421("S-421 Route Plan"),
    RTZ("Route Plan"),
    EPC("Electronic Port Clearance");

    // Enum Variables
    private final String description;

    /**
     * Enum Constructor
     *
     * @param description the enum description
     */
    SECOM_DataProductType(final String description) {
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
    public static SECOM_DataProductType fromDescription(String description) {
        return Arrays.stream(SECOM_DataProductType.values())
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
