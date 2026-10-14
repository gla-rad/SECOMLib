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

package org.grad.secomv2.core.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvStringGeneratorTest {

    // Test Variables
    private CsvStringGenerator generator;

    /**
     * Common setup for all the tests.
     */
    @BeforeEach
    void setup() {
        this.generator = () -> new Object[]{};
    }

    /**
     * Test that the string array elements are Base64 encoded and joined
     * using a comma, according to the SECOM standard, section 7.3.4.
     */
    @Test
    void testStringArrayConversion() {
        assertEquals(Base64.getEncoder().encodeToString("foobar".getBytes(StandardCharsets.UTF_8)) + "," + Base64.getEncoder().encodeToString("10".getBytes(StandardCharsets.UTF_8)), this.generator.attributeConversion(new String[]{"foobar", "10"}));
    }

    /**
     * Test that the string array elements are encoded using UTF-8.
     */
    @Test
    void testStringArrayConversionUtf8() {
        assertEquals(Base64.getEncoder().encodeToString("αβγ".getBytes(StandardCharsets.UTF_8)), this.generator.attributeConversion(new String[]{"αβγ"}));
    }

    /**
     * Test that plain string array elements are always encoded, even when
     * they happen to also be valid Base64 strings.
     */
    @Test
    void testStringArrayConversionBase64LikeText() {
        assertEquals(Base64.getEncoder().encodeToString("wind".getBytes(StandardCharsets.UTF_8)) + "," + Base64.getEncoder().encodeToString("tide".getBytes(StandardCharsets.UTF_8)), this.generator.attributeConversion(new String[]{"wind", "tide"}));
    }

    /**
     * Test that empty arrays and null array elements are converted to empty
     * strings.
     */
    @Test
    void testStringArrayConversionEmptyAndNull() {
        assertEquals("", this.generator.attributeConversion(new String[]{}));
        assertEquals("," + Base64.getEncoder().encodeToString("foobar".getBytes(StandardCharsets.UTF_8)), this.generator.attributeConversion(new String[]{null, "foobar"}));
    }

    /**
     * Test that the already Base64 encoded string arrays, e.g. the minified
     * PEM certificates, are not encoded a second time but only joined using
     * a comma.
     */
    @Test
    void testBase64StringArrayConversion() {
        assertEquals("MIIE1zCCBFygAwIBAgIU,MIIE1zCCBFygAsITQfOY", this.generator.attributeConversion(Base64StringArray.of(new String[]{"MIIE1zCCBFygAwIBAgIU", "MIIE1zCCBFygAsITQfOY"})));
    }

    /**
     * Test that null Base64 string arrays and null elements are converted to
     * empty strings.
     */
    @Test
    void testBase64StringArrayConversionNull() {
        assertEquals("", this.generator.attributeConversion(Base64StringArray.of(null)));
        assertEquals(",MIIE1zCCBFygAwIBAgIU", this.generator.attributeConversion(Base64StringArray.of(new String[]{null, "MIIE1zCCBFygAwIBAgIU"})));
    }

}
