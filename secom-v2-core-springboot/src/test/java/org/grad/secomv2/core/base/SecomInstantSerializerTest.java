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

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.json.WriterBasedJsonGenerator;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class SecomInstantSerializerTest {

    // Test Variables
    ObjectMapper objectMapper;
    SecomInstantSerializer secomInstantSerializer;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws IOException {
        // Initialise the serializer
        this.secomInstantSerializer = new SecomInstantSerializer();

        // Initialise the object mapper
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Test that we can successfully serialise a temporal instant according to
     * the SECOM standard.
     */
    @Test
    void testSerialize() throws IOException {
        // Get a test time instance
        Instant instant = Instant.now();

        // Serialize the input
        final StringWriter stringWriter = new StringWriter();
        try (JsonGenerator jsonGenerator = this.objectMapper.createGenerator(stringWriter)) {
            this.secomInstantSerializer.serialize(instant, jsonGenerator, this.objectMapper._serializationContext());
        }

        // And get the result
        String result = stringWriter.toString();

        // Make sure it seems fine
        assertNotNull(result);
        assertTrue(result.matches("(\"\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})(Z|\\+(\\d{2}):(\\d{2}))\""));
    }
}