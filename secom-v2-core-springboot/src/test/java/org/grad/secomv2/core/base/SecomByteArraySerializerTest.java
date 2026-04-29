package org.grad.secomv2.core.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;

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

        // Initialise the object mapper
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Test that we can successfully serialise a byte array into a simple
     * character array. Base64 encoding is handled elsewhere.
     */
    @Test
    void testSerialize() throws IOException {
        // Get a test time instance
        byte[] bytes = new byte[]{'a', 'b', 'c', 'd'};

        // Serialize the input
        final StringWriter stringWriter = new StringWriter();
        try (JsonGenerator jsonGenerator = this.objectMapper.createGenerator(stringWriter)) {
            this.secomByteArraySerializer.serialize(bytes, jsonGenerator, this.objectMapper._serializationContext());
        }

        // And get the result
        String result = stringWriter.toString();

        // Make sure it seems fine
        assertNotNull(result);
        assertEquals("\"abcd\"", result);
    }

}