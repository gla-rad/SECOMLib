package org.grad.secomv2.core.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

        SimpleModule module = new SimpleModule();
        module.addSerializer(byte[].class, this.secomByteArraySerializer);

        // Initialise the object mapper
        this.objectMapper = JsonMapper.builder().addModule(module).build();
    }

    /**
     * Test that we can successfully serialise a byte array into a simple
     * character array. Base64 encoding is handled elsewhere.
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