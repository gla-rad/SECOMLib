package org.grad.secomv2.core.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class SecomByteArrayDeSerializerTest {

    // Test Parameters
    SecomByteArrayDeSerializer secomByteArrayDeserializer;
    private ObjectMapper objectMapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        this.secomByteArrayDeserializer = new SecomByteArrayDeSerializer();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(byte[].class, this.secomByteArrayDeserializer);
        this.objectMapper = JsonMapper.builder().addModule(module).build();
    }

    /**
     * make sure we can correctly deserialize the incoming string to a bytes
     * array.
     */
    @Test
    void testDeserializeByteArray() throws IOException {
        byte[] result = this.objectMapper.readValue("\"hello world\"", byte[].class);
        assertArrayEquals("hello world".getBytes(StandardCharsets.UTF_8), result);
    }

    /**
     * make sure we can correctly deserialize a more realistic incoming byte array as a string
     */
    @Test
    void testDeserializeBase64String() throws IOException {
        String base64 = "aGVsbG8gd29ybGQ=";
        byte[] result = this.objectMapper.readValue("\"" + base64 + "\"", byte[].class);
        assertArrayEquals(base64.getBytes(StandardCharsets.UTF_8), result);
    }

}