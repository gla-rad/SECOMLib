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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.json.WriterBasedJsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SecomInstantSerializerTest {

    // Test Variables
    ObjectMapper objectMapper;
    SecomInstantSerializer secomInstantSerializer;
    StringWriter stringWriter;
    WriterBasedJsonGenerator jsonGenerator;
    SerializerProvider serializerProvider;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws IOException {
        // Initialise the object mapper
        this.objectMapper = new ObjectMapper();

        // Initialise the serializer
        this.secomInstantSerializer = new SecomInstantSerializer();

        // Create a json generator
        this.stringWriter = new StringWriter();
        this.jsonGenerator = (WriterBasedJsonGenerator) new JsonFactory().createGenerator(this.stringWriter);

        // And add a serialisation provider
        this.serializerProvider = new ObjectMapper().getSerializerProvider();
    }

    /**
     * Test that we can successfully serialise a temporal instant according to
     * the SECOM standard.
     */
    @Test
    void testSerialize() throws IOException {
        // Get a test time instance
        Instant instant = Instant.now();

        // Begin the JSON Generation
        this.jsonGenerator.writeStartObject();

        // Serialize the input
        jsonGenerator.writeFieldName("date");
        secomInstantSerializer.serialize(instant, this.jsonGenerator, this.serializerProvider);

        // Finish the JSON Generation
        this.jsonGenerator.writeEndObject();
        this.jsonGenerator.close();

        // And get the final result
        final Map<String, String> result = this.objectMapper.readValue(
                this.stringWriter.toString(),
                new TypeReference<>() {}
        );

        // Make sure it seems fine
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get("date").matches("(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})(Z|\\+(\\d{2}):(\\d{2}))"));
    }

}