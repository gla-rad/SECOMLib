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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static org.grad.secomv2.core.base.SecomConstants.SECOM_DATE_TIME_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecomByteArraySerializerTest {

    // Test Variables
    ObjectMapper objectMapper;
    SecomByteArraySerializer secomByteArraySerializer;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws IOException {

        // Initialise the serializer
        this.secomByteArraySerializer = new SecomByteArraySerializer();

        SimpleModule module = new SimpleModule();
        module.addSerializer(byte[].class, this.secomByteArraySerializer);

        // Initialise the object mapper
        this.objectMapper = new ObjectMapper().registerModule(module);

    }

    /**
     * Test that we can successfully serialise a byte array to string
     *
     */
    @Test
    void testSerialize() throws IOException {
        // Get a test string
        String testString = "hello world";
        byte[] testByteArray = testString.getBytes(StandardCharsets.UTF_8);

        // Check they match
        // writeValueAsString includes " in the output so remove them
        String serialisedByteArray = this.objectMapper.writeValueAsString(testByteArray).replace("\"", "");
        assertEquals(testString, serialisedByteArray);

    }

}