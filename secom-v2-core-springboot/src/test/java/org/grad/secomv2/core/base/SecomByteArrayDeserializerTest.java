package org.grad.secomv2.core.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class SecomByteArrayDeserializerTest {

    // Test Parameters
    SecomByteArrayDeserializer secomByteArrayDeserializer;
    private ObjectMapper objectMapper;
    private JsonNode jsonNode;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        this.secomByteArrayDeserializer = new SecomByteArrayDeserializer();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * make sure we can correctly deserialize the incoming string to a bytes
     * array.
     */
    @Test
    void testDeserializeInstant() {
        // Make some mocks to test easily
        this.jsonNode = this.objectMapper.createObjectNode().stringNode("abcd");

        // And deserialize
        byte[] result = this.secomByteArrayDeserializer.deserialize(this.objectMapper.createParser(this.jsonNode.toString()), this.objectMapper._deserializationContext());

        // Make sure the result seems correct
        assertEquals("abcd", new String(result, StandardCharsets.UTF_8));
    }

}