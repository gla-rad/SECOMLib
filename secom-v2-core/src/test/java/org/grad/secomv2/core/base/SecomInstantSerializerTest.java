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

package org.grad.secomv2.core.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.grad.secomv2.core.base.SecomConstants.SECOM_DATE_TIME_FORMAT;

class SecomInstantSerializerTest {

    // Test Variables
    ObjectMapper objectMapper;
    SecomInstantSerializer secomInstantSerializer;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {

        // Initialise the serializer
        this.secomInstantSerializer = new SecomInstantSerializer();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Instant.class, this.secomInstantSerializer);

        // Initialise the object mapper
        this.objectMapper = new ObjectMapper().registerModule(module);

    }

    /**
     * Test that we can successfully serialise a temporal instant according to
     * the SECOM standard.
     */
    @Test
    void testSerialize() throws IOException {
        // Get a test time instance
        Instant instant = Instant.now();

        // Format the date time
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(SECOM_DATE_TIME_FORMAT)
                .optionalStart()
                .parseLenient()
                .appendOffset("+HH:MM", "Z")
                .parseStrict()
                .optionalEnd()
                .toFormatter()
                .withZone(ZoneOffset.UTC);

        String secomDateTime = formatter.format(instant);

        // Check they match
        // writeValueAsString includes " in the output so remove them
        String serialisedInstant = this.objectMapper.writeValueAsString(instant).replace("\"", "");
        assertEquals(secomDateTime, serialisedInstant);
        assertTrue(serialisedInstant.matches("(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})(Z|\\+(\\d{2}):(\\d{2}))"));
    }
}