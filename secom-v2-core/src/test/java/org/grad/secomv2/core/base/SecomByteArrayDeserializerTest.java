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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SecomByteArrayDeserializerTest {

    // Test Parameters
    SecomByteArrayDeSerializer secomByteArrayDeSerializer;
    ObjectMapper objectMapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        this.secomByteArrayDeSerializer = new SecomByteArrayDeSerializer();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(byte[].class, this.secomByteArrayDeSerializer);
        this.objectMapper = new ObjectMapper().registerModule(module);
    }

    /**
     * make sure we can correctly deserialize an incoming byte array as a string
     *
     */
    @Test
    void testDeserializeByteArray() throws IOException {
        byte[] result = this.objectMapper.readValue("\"hello world\"", byte[].class);
        assertArrayEquals("hello world".getBytes(StandardCharsets.UTF_8), result);
    }

    /**
     * make sure we can correctly deserialize a more realistic incoming byte array as a string
     *
     */
    @Test
    void testDeserializeBase64String() throws IOException {
        // Realistic SECOM payload: the deserializer receives it already as a string;
        // the interceptors handle actual Base64 decode separately.
        String base64 = "aGVsbG8gd29ybGQ=";
        byte[] result = this.objectMapper.readValue("\"" + base64 + "\"", byte[].class);
        assertArrayEquals(base64.getBytes(StandardCharsets.UTF_8), result);
    }

}