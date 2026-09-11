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

import org.grad.secomv2.core.exceptions.SecomValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SECOM_DataProductTypeTest {

    /**
     * Test that the SECOM Data Product Type enumeration has the expected
     * values in each of the associated entries.
     */
    @Test
    void testValues() {
        assertEquals(SECOM_DataProductType.OTHER.getValue(), "OTHER");
        assertEquals(SECOM_DataProductType.S57.getValue(), "S-57");
        assertEquals(SECOM_DataProductType.S101.getValue(), "S-101");
        assertEquals(SECOM_DataProductType.S102.getValue(), "S-102");
        assertEquals(SECOM_DataProductType.S104.getValue(), "S-104");
        assertEquals(SECOM_DataProductType.S111.getValue(), "S-111");
        assertEquals(SECOM_DataProductType.S122.getValue(), "S-122");
        assertEquals(SECOM_DataProductType.S123.getValue(), "S-123");
        assertEquals(SECOM_DataProductType.S124.getValue(), "S-124");
        assertEquals(SECOM_DataProductType.S125.getValue(), "S-125");
        assertEquals(SECOM_DataProductType.S126.getValue(), "S-126");
        assertEquals(SECOM_DataProductType.S127.getValue(), "S-127");
        assertEquals(SECOM_DataProductType.S128.getValue(), "S-128");
        assertEquals(SECOM_DataProductType.S129.getValue(), "S-129");
        assertEquals(SECOM_DataProductType.S131.getValue(), "S-131");
        assertEquals(SECOM_DataProductType.S201.getValue(), "S-201");
        assertEquals(SECOM_DataProductType.S210.getValue(), "S-210");
        assertEquals(SECOM_DataProductType.S211.getValue(), "S-211");
        assertEquals(SECOM_DataProductType.S212.getValue(), "S-212");
        assertEquals(SECOM_DataProductType.S401.getValue(), "S-401");
        assertEquals(SECOM_DataProductType.S402.getValue(), "S-402");
        assertEquals(SECOM_DataProductType.S411.getValue(), "S-411");
        assertEquals(SECOM_DataProductType.S412.getValue(), "S-412");
        assertEquals(SECOM_DataProductType.S413.getValue(), "S-413");
        assertEquals(SECOM_DataProductType.S414.getValue(), "S-414");
        assertEquals(SECOM_DataProductType.RTZ.getValue(), "RTZ");
        assertEquals(SECOM_DataProductType.EPC.getValue(), "EPC");
    }

    /**
     * Test that the SECOM Data Product Type enumeration has the expected
     * descriptions in each of the associated entries.
     */
    @Test
    void testDescriptions() {
        assertEquals(SECOM_DataProductType.OTHER.getDescription(), "Other data types not covered in this table");
        assertEquals(SECOM_DataProductType.S57.getDescription(), "S-57 Electronic Navigational Chart (ENC)");
        assertEquals(SECOM_DataProductType.S101.getDescription(), "S-101 Electronic Navigational Chart (ENC)");
        assertEquals(SECOM_DataProductType.S102.getDescription(), "S-102 Bathymetric Surface");
        assertEquals(SECOM_DataProductType.S104.getDescription(), "S-104 Water Level Information for Surface Navigation");
        assertEquals(SECOM_DataProductType.S111.getDescription(), "S-111 Surface Currents");
        assertEquals(SECOM_DataProductType.S122.getDescription(), "S-122 Marine Protected Areas (MPAs)");
        assertEquals(SECOM_DataProductType.S123.getDescription(), "S-123 Marine Radio Services");
        assertEquals(SECOM_DataProductType.S124.getDescription(), "S-124 Navigational Warnings");
        assertEquals(SECOM_DataProductType.S125.getDescription(), "S-125 Marine Navigational Services");
        assertEquals(SECOM_DataProductType.S126.getDescription(), "S-126 Marine Physical Environment");
        assertEquals(SECOM_DataProductType.S127.getDescription(), "S-127 Marine Traffic Management");
        assertEquals(SECOM_DataProductType.S128.getDescription(), "S-128 Catalogue of Nautical Products");
        assertEquals(SECOM_DataProductType.S129.getDescription(), "S-129 Under Keel Clearance Management (UKCM)");
        assertEquals(SECOM_DataProductType.S131.getDescription(), "S-131 Marine Harbour Infrastructure");
        assertEquals(SECOM_DataProductType.S201.getDescription(), "S-201 Aids to Navigation (AtoN) Information");
        assertEquals(SECOM_DataProductType.S210.getDescription(), "S-210 Inter-VTS Exchange Format");
        assertEquals(SECOM_DataProductType.S211.getDescription(), "S-211 Port Call Message Format");
        assertEquals(SECOM_DataProductType.S212.getDescription(), "S-212 VTS Digital Information Service");
        assertEquals(SECOM_DataProductType.S401.getDescription(), "S-401 Inland ENC");
        assertEquals(SECOM_DataProductType.S402.getDescription(), "S-402 Bathymetric Contour Overlay for Inland ENC");
        assertEquals(SECOM_DataProductType.S411.getDescription(), "S-411 Sea Ice Information");
        assertEquals(SECOM_DataProductType.S412.getDescription(), "S-412 Weather Overlay");
        assertEquals(SECOM_DataProductType.S413.getDescription(), "S-413 Marine Weather Conditions");
        assertEquals(SECOM_DataProductType.S414.getDescription(), "S-414 Marine Weather Observations");
        assertEquals(SECOM_DataProductType.S421.getDescription(), "S-421 Route Plan");
        assertEquals(SECOM_DataProductType.RTZ.getDescription(), "Route Plan");
        assertEquals(SECOM_DataProductType.EPC.getDescription(), "Electronic Port Clearance");
    }

    /**
     * Test that we can identify the correct data product type from a given
     * string, even if this contains or no the S-100 product dash or it
     * upper/lower case.
     */
    @Test
    void testFromString() {
        assertEquals(SECOM_DataProductType.OTHER, SECOM_DataProductType.fromString("OTHER"));
        assertEquals(SECOM_DataProductType.S57, SECOM_DataProductType.fromString("S-57"));
        assertEquals(SECOM_DataProductType.S101, SECOM_DataProductType.fromString("S-101"));
        assertEquals(SECOM_DataProductType.S102, SECOM_DataProductType.fromString("S-102"));
        assertEquals(SECOM_DataProductType.S104, SECOM_DataProductType.fromString("S-104"));
        assertEquals(SECOM_DataProductType.S111, SECOM_DataProductType.fromString("S-111"));
        assertEquals(SECOM_DataProductType.S122, SECOM_DataProductType.fromString("S-122"));
        assertEquals(SECOM_DataProductType.S123, SECOM_DataProductType.fromString("S-123"));
        assertEquals(SECOM_DataProductType.S124, SECOM_DataProductType.fromString("S-124"));
        assertEquals(SECOM_DataProductType.S125, SECOM_DataProductType.fromString("S-125"));
        assertEquals(SECOM_DataProductType.S126, SECOM_DataProductType.fromString("S-126"));
        assertEquals(SECOM_DataProductType.S127, SECOM_DataProductType.fromString("S-127"));
        assertEquals(SECOM_DataProductType.S128, SECOM_DataProductType.fromString("S-128"));
        assertEquals(SECOM_DataProductType.S129, SECOM_DataProductType.fromString("S-129"));
        assertEquals(SECOM_DataProductType.S131, SECOM_DataProductType.fromString("S-131"));
        assertEquals(SECOM_DataProductType.S201, SECOM_DataProductType.fromString("S-201"));
        assertEquals(SECOM_DataProductType.S210, SECOM_DataProductType.fromString("S-210"));
        assertEquals(SECOM_DataProductType.S211, SECOM_DataProductType.fromString("S-211"));
        assertEquals(SECOM_DataProductType.S212, SECOM_DataProductType.fromString("S-212"));
        assertEquals(SECOM_DataProductType.S401, SECOM_DataProductType.fromString("S-401"));
        assertEquals(SECOM_DataProductType.S402, SECOM_DataProductType.fromString("S-402"));
        assertEquals(SECOM_DataProductType.S411, SECOM_DataProductType.fromString("S-411"));
        assertEquals(SECOM_DataProductType.S412, SECOM_DataProductType.fromString("S-412"));
        assertEquals(SECOM_DataProductType.S413, SECOM_DataProductType.fromString("S-413"));
        assertEquals(SECOM_DataProductType.S414, SECOM_DataProductType.fromString("S-414"));
        assertEquals(SECOM_DataProductType.RTZ, SECOM_DataProductType.fromString("RTZ"));
        assertEquals(SECOM_DataProductType.EPC, SECOM_DataProductType.fromString("EPC"));

        // Also check the normalised values - without dashes
        assertEquals(SECOM_DataProductType.S57, SECOM_DataProductType.fromString("S57"));
        assertEquals(SECOM_DataProductType.S101, SECOM_DataProductType.fromString("S101"));
        assertEquals(SECOM_DataProductType.S102, SECOM_DataProductType.fromString("S102"));
        assertEquals(SECOM_DataProductType.S104, SECOM_DataProductType.fromString("S104"));
        assertEquals(SECOM_DataProductType.S111, SECOM_DataProductType.fromString("S111"));
        assertEquals(SECOM_DataProductType.S122, SECOM_DataProductType.fromString("S122"));
        assertEquals(SECOM_DataProductType.S123, SECOM_DataProductType.fromString("S123"));
        assertEquals(SECOM_DataProductType.S124, SECOM_DataProductType.fromString("S124"));
        assertEquals(SECOM_DataProductType.S125, SECOM_DataProductType.fromString("S125"));
        assertEquals(SECOM_DataProductType.S126, SECOM_DataProductType.fromString("S126"));
        assertEquals(SECOM_DataProductType.S127, SECOM_DataProductType.fromString("S127"));
        assertEquals(SECOM_DataProductType.S128, SECOM_DataProductType.fromString("S128"));
        assertEquals(SECOM_DataProductType.S129, SECOM_DataProductType.fromString("S129"));
        assertEquals(SECOM_DataProductType.S131, SECOM_DataProductType.fromString("S131"));
        assertEquals(SECOM_DataProductType.S201, SECOM_DataProductType.fromString("S201"));
        assertEquals(SECOM_DataProductType.S210, SECOM_DataProductType.fromString("S210"));
        assertEquals(SECOM_DataProductType.S211, SECOM_DataProductType.fromString("S211"));
        assertEquals(SECOM_DataProductType.S212, SECOM_DataProductType.fromString("S212"));
        assertEquals(SECOM_DataProductType.S401, SECOM_DataProductType.fromString("S401"));
        assertEquals(SECOM_DataProductType.S402, SECOM_DataProductType.fromString("S402"));
        assertEquals(SECOM_DataProductType.S411, SECOM_DataProductType.fromString("S411"));
        assertEquals(SECOM_DataProductType.S412, SECOM_DataProductType.fromString("S412"));
        assertEquals(SECOM_DataProductType.S413, SECOM_DataProductType.fromString("S413"));
        assertEquals(SECOM_DataProductType.S414, SECOM_DataProductType.fromString("S414"));

        // Also check other normalised values - lower case
        assertEquals(SECOM_DataProductType.OTHER, SECOM_DataProductType.fromString("other"));
        assertEquals(SECOM_DataProductType.S57, SECOM_DataProductType.fromString("s57"));
        assertEquals(SECOM_DataProductType.S101, SECOM_DataProductType.fromString("s101"));
        assertEquals(SECOM_DataProductType.S102, SECOM_DataProductType.fromString("s102"));
        assertEquals(SECOM_DataProductType.S104, SECOM_DataProductType.fromString("s104"));
        assertEquals(SECOM_DataProductType.S111, SECOM_DataProductType.fromString("s111"));
        assertEquals(SECOM_DataProductType.S122, SECOM_DataProductType.fromString("s122"));
        assertEquals(SECOM_DataProductType.S123, SECOM_DataProductType.fromString("s123"));
        assertEquals(SECOM_DataProductType.S124, SECOM_DataProductType.fromString("s124"));
        assertEquals(SECOM_DataProductType.S125, SECOM_DataProductType.fromString("s125"));
        assertEquals(SECOM_DataProductType.S126, SECOM_DataProductType.fromString("s126"));
        assertEquals(SECOM_DataProductType.S127, SECOM_DataProductType.fromString("s127"));
        assertEquals(SECOM_DataProductType.S128, SECOM_DataProductType.fromString("s128"));
        assertEquals(SECOM_DataProductType.S129, SECOM_DataProductType.fromString("s129"));
        assertEquals(SECOM_DataProductType.S131, SECOM_DataProductType.fromString("s131"));
        assertEquals(SECOM_DataProductType.S201, SECOM_DataProductType.fromString("s201"));
        assertEquals(SECOM_DataProductType.S210, SECOM_DataProductType.fromString("s210"));
        assertEquals(SECOM_DataProductType.S211, SECOM_DataProductType.fromString("s211"));
        assertEquals(SECOM_DataProductType.S212, SECOM_DataProductType.fromString("s212"));
        assertEquals(SECOM_DataProductType.S401, SECOM_DataProductType.fromString("s401"));
        assertEquals(SECOM_DataProductType.S402, SECOM_DataProductType.fromString("s402"));
        assertEquals(SECOM_DataProductType.S411, SECOM_DataProductType.fromString("s411"));
        assertEquals(SECOM_DataProductType.S412, SECOM_DataProductType.fromString("s412"));
        assertEquals(SECOM_DataProductType.S413, SECOM_DataProductType.fromString("s413"));
        assertEquals(SECOM_DataProductType.S414, SECOM_DataProductType.fromString("s414"));
        assertEquals(SECOM_DataProductType.RTZ, SECOM_DataProductType.fromString("rtz"));
        assertEquals(SECOM_DataProductType.EPC, SECOM_DataProductType.fromString("epc"));
    }

    /**
     * Test that for string invalid values, the fromValue function will throw
     * a SecomValidationException.
     */
    @Test
    void testFromStringInvalidd() {
        assertThrows(SecomValidationException.class, () -> SECOM_DataProductType.fromString(null));
        assertThrows(SecomValidationException.class, () -> SECOM_DataProductType.fromString("invalid"));
    }

    /**
     * Test that we can identify the correct data product type from a given
     * description string, if it matches the defined description, even if it
     * is upper/lower case.
     */
    @Test
    void testFromDescription() {
        assertEquals(SECOM_DataProductType.OTHER, SECOM_DataProductType.fromDescription("Other data types not covered in this table"));
        assertEquals(SECOM_DataProductType.S57, SECOM_DataProductType.fromDescription("S-57 Electronic Navigational Chart (ENC)"));
        assertEquals(SECOM_DataProductType.S101, SECOM_DataProductType.fromDescription("S-101 Electronic Navigational Chart (ENC)"));
        assertEquals(SECOM_DataProductType.S102, SECOM_DataProductType.fromDescription("S-102 Bathymetric Surface"));
        assertEquals(SECOM_DataProductType.S104, SECOM_DataProductType.fromDescription("S-104 Water Level Information for Surface Navigation"));
        assertEquals(SECOM_DataProductType.S111, SECOM_DataProductType.fromDescription("S-111 Surface Currents"));
        assertEquals(SECOM_DataProductType.S122, SECOM_DataProductType.fromDescription("S-122 Marine Protected Areas (MPAs)"));
        assertEquals(SECOM_DataProductType.S123, SECOM_DataProductType.fromDescription("S-123 Marine Radio Services"));
        assertEquals(SECOM_DataProductType.S124, SECOM_DataProductType.fromDescription("S-124 Navigational Warnings"));
        assertEquals(SECOM_DataProductType.S125, SECOM_DataProductType.fromDescription("S-125 Marine Navigational Services"));
        assertEquals(SECOM_DataProductType.S126, SECOM_DataProductType.fromDescription("S-126 Marine Physical Environment"));
        assertEquals(SECOM_DataProductType.S127, SECOM_DataProductType.fromDescription("S-127 Marine Traffic Management"));
        assertEquals(SECOM_DataProductType.S128, SECOM_DataProductType.fromDescription("S-128 Catalogue of Nautical Products"));
        assertEquals(SECOM_DataProductType.S129, SECOM_DataProductType.fromDescription("S-129 Under Keel Clearance Management (UKCM)"));
        assertEquals(SECOM_DataProductType.S131, SECOM_DataProductType.fromDescription("S-131 Marine Harbour Infrastructure"));
        assertEquals(SECOM_DataProductType.S201, SECOM_DataProductType.fromDescription("S-201 Aids to Navigation (AtoN) Information"));
        assertEquals(SECOM_DataProductType.S210, SECOM_DataProductType.fromDescription("S-210 Inter-VTS Exchange Format"));
        assertEquals(SECOM_DataProductType.S211, SECOM_DataProductType.fromDescription("S-211 Port Call Message Format"));
        assertEquals(SECOM_DataProductType.S212, SECOM_DataProductType.fromDescription("S-212 VTS Digital Information Service"));
        assertEquals(SECOM_DataProductType.S401, SECOM_DataProductType.fromDescription("S-401 Inland ENC"));
        assertEquals(SECOM_DataProductType.S402, SECOM_DataProductType.fromDescription("S-402 Bathymetric Contour Overlay for Inland ENC"));
        assertEquals(SECOM_DataProductType.S411, SECOM_DataProductType.fromDescription("S-411 Sea Ice Information"));
        assertEquals(SECOM_DataProductType.S412, SECOM_DataProductType.fromDescription("S-412 Weather Overlay"));
        assertEquals(SECOM_DataProductType.S413, SECOM_DataProductType.fromDescription("S-413 Marine Weather Conditions"));
        assertEquals(SECOM_DataProductType.S414, SECOM_DataProductType.fromDescription("S-414 Marine Weather Observations"));
        assertEquals(SECOM_DataProductType.S421, SECOM_DataProductType.fromDescription("S-421 Route Plan"));
        assertEquals(SECOM_DataProductType.RTZ, SECOM_DataProductType.fromDescription("Route Plan"));
        assertEquals(SECOM_DataProductType.EPC, SECOM_DataProductType.fromDescription("Electronic Port Clearance"));

        // Now also test for lower case values
        assertEquals(SECOM_DataProductType.OTHER, SECOM_DataProductType.fromDescription("other data types not covered in this table"));
        assertEquals(SECOM_DataProductType.S57, SECOM_DataProductType.fromDescription("s-57 electronic navigational chart (enc)"));
        assertEquals(SECOM_DataProductType.S101, SECOM_DataProductType.fromDescription("s-101 electronic navigational chart (enc)"));
        assertEquals(SECOM_DataProductType.S102, SECOM_DataProductType.fromDescription("s-102 bathymetric surface"));
        assertEquals(SECOM_DataProductType.S104, SECOM_DataProductType.fromDescription("s-104 water level information for surface navigation"));
        assertEquals(SECOM_DataProductType.S111, SECOM_DataProductType.fromDescription("s-111 surface currents"));
        assertEquals(SECOM_DataProductType.S122, SECOM_DataProductType.fromDescription("s-122 marine protected areas (mpas)"));
        assertEquals(SECOM_DataProductType.S123, SECOM_DataProductType.fromDescription("s-123 marine radio services"));
        assertEquals(SECOM_DataProductType.S124, SECOM_DataProductType.fromDescription("s-124 navigational warnings"));
        assertEquals(SECOM_DataProductType.S125, SECOM_DataProductType.fromDescription("s-125 marine navigational services"));
        assertEquals(SECOM_DataProductType.S126, SECOM_DataProductType.fromDescription("s-126 marine physical environment"));
        assertEquals(SECOM_DataProductType.S127, SECOM_DataProductType.fromDescription("s-127 marine traffic management"));
        assertEquals(SECOM_DataProductType.S128, SECOM_DataProductType.fromDescription("s-128 catalogue of nautical products"));
        assertEquals(SECOM_DataProductType.S129, SECOM_DataProductType.fromDescription("s-129 under keel clearance management (ukcm)"));
        assertEquals(SECOM_DataProductType.S131, SECOM_DataProductType.fromDescription("s-131 marine harbour infrastructure"));
        assertEquals(SECOM_DataProductType.S201, SECOM_DataProductType.fromDescription("s-201 aids to navigation (aton) information"));
        assertEquals(SECOM_DataProductType.S210, SECOM_DataProductType.fromDescription("s-210 inter-vts exchange format"));
        assertEquals(SECOM_DataProductType.S211, SECOM_DataProductType.fromDescription("s-211 port call message format"));
        assertEquals(SECOM_DataProductType.S212, SECOM_DataProductType.fromDescription("s-212 vts digital information service"));
        assertEquals(SECOM_DataProductType.S401, SECOM_DataProductType.fromDescription("s-401 inland enc"));
        assertEquals(SECOM_DataProductType.S402, SECOM_DataProductType.fromDescription("s-402 bathymetric contour overlay for inland enc"));
        assertEquals(SECOM_DataProductType.S411, SECOM_DataProductType.fromDescription("s-411 sea ice information"));
        assertEquals(SECOM_DataProductType.S412, SECOM_DataProductType.fromDescription("s-412 weather overlay"));
        assertEquals(SECOM_DataProductType.S413, SECOM_DataProductType.fromDescription("s-413 marine weather conditions"));
        assertEquals(SECOM_DataProductType.S414, SECOM_DataProductType.fromDescription("s-414 marine weather observations"));
        assertEquals(SECOM_DataProductType.S421, SECOM_DataProductType.fromDescription("s-421 route plan"));
        assertEquals(SECOM_DataProductType.RTZ, SECOM_DataProductType.fromDescription("route plan"));
        assertEquals(SECOM_DataProductType.EPC, SECOM_DataProductType.fromDescription("electronic port clearance"));
    }

    /**
     * Test that for string invalid values, the fromDescription function will throw
     * a SecomValidationException.
     */
    @Test
    void testFromDescriptionInvalidd() {
        assertThrows(SecomValidationException.class, () -> SECOM_DataProductType.fromDescription(null));
        assertThrows(SecomValidationException.class, () -> SECOM_DataProductType.fromDescription("invalid"));
    }

}

