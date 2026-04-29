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
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class SecomInstantDeserializerTest {

    // Test Parameters
    SecomInstantDeserializer secomInstantDeserializer;
    private ObjectMapper objectMapper;
    private JsonNode jsonNode;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        this.secomInstantDeserializer = new SecomInstantDeserializer();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * make sure we can correctly deserialize the incoming SECOM-compliant
     * formatted local dates.
     */
    @Test
    void testDeserializeInstant() {
        // Make some mocks to test easily
        this.jsonNode = this.objectMapper.createObjectNode().stringNode("2001-01-01T12:13:14Z");

        // And deserialize
        Instant result = this.secomInstantDeserializer.deserialize(this.objectMapper.createParser(this.jsonNode.toString()), this.objectMapper._deserializationContext());

        // Make sure the result seems correct
        assertEquals(Instant.parse("2001-01-01T12:13:14Z"), result);
    }

    /**
     * make sure we can correctly deserialize the incoming SECOM-compliant
     * formatted local daylight-saving dates.
     */
    @Test
    void testDeserializeInstantDLS() {
        // Make some mocks to test easily
        this.jsonNode = this.objectMapper.createObjectNode().stringNode("2008-08-08T12:13:14+01:00");

        // And deserialize
        Instant result = this.secomInstantDeserializer.deserialize(this.objectMapper.createParser(this.jsonNode.toString()), this.objectMapper._deserializationContext());

        // Make sure the result seems correct
        assertEquals(Instant.parse("2008-08-08T12:13:14+01:00"), result);
    }

    /**
     * make sure we can correctly deserialize the incoming SECOM-compliant
     * formatted UTC dates.
     */
    @Test
    void testDeserializeUTCDate() {
        // Make some mocks to test easily
        this.jsonNode = this.objectMapper.createObjectNode().stringNode("2001-01-01T12:13:14Z");

        // And deserialize
        Instant result = this.secomInstantDeserializer.deserialize(this.objectMapper.createParser(this.jsonNode.toString()), this.objectMapper._deserializationContext());

        // Make sure the result seems correct
        assertEquals(Instant.parse("2001-01-01T12:13:14Z"), result);
    }

    /**
     * make sure we can correctly deserialize the incoming SECOM-compliant
     * formatted UTC daylight-saving dates.
     */
    @Test
    void testDeserializeUTCDateDLS() {
        // Make some mocks to test easily
        this.jsonNode = this.objectMapper.createObjectNode().stringNode("2008-08-08T12:13:14Z");

        // And deserialize
        Instant result = this.secomInstantDeserializer.deserialize(this.objectMapper.createParser(this.jsonNode.toString()), this.objectMapper._deserializationContext());

        // Make sure the result seems correct
        assertEquals(Instant.parse("2008-08-08T12:13:14Z"), result);
    }

    /**
     * make sure we can correctly deserialize the incoming SECOM-compliant
     * formatted dates with offsets.
     */
    @Test
    void testDeserializeDateWithOffset() throws IOException {
        // Make some mocks to test easily
        this.jsonNode = this.objectMapper.createObjectNode().stringNode("2001-01-01T12:13:14+01:00");

        // And deserialize
        Instant result = this.secomInstantDeserializer.deserialize(this.objectMapper.createParser(this.jsonNode.toString()), this.objectMapper._deserializationContext());

        // Make sure the result seems correct
        assertEquals(Instant.parse("2001-01-01T12:13:14+01:00"), result);
    }

    /**
     * make sure we can correctly deserialize the incoming SECOM-compliant
     * formatted daylight-saving dates with offsets.
     */
    @Test
    void testDeserializeDateWithOffsetDLS() throws IOException {
        // Make some mocks to test easily
        this.jsonNode = this.objectMapper.createObjectNode().stringNode("2008-08-08T12:13:14+01:00");

        // And deserialize
        Instant result = this.secomInstantDeserializer.deserialize(this.objectMapper.createParser(this.jsonNode.toString()), this.objectMapper._deserializationContext());

        // Make sure the result seems correct
        assertEquals(Instant.parse("2008-08-08T12:13:14+01:00"), result);
    }

}