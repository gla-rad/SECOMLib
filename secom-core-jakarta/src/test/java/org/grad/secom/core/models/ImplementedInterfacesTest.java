/*
 * Copyright (c) 2022 GLA Research and Development Directorate
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

package org.grad.secom.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ImplementedInterfacesTest {

    // Class Variables
    private ImplementedInterfaces obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws MalformedURLException {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Generate a new object
        this.obj = new ImplementedInterfaces();
        this.obj.setUpload(Boolean.TRUE);
        this.obj.setUploadLink(Boolean.TRUE);
        this.obj.setGet(Boolean.TRUE);
        this.obj.setGetSummary(Boolean.TRUE);
        this.obj.setGetByLink(Boolean.TRUE);
        this.obj.setSubscription(Boolean.TRUE);
        this.obj.setAccess(Boolean.TRUE);
        this.obj.setEncryptionKey(Boolean.TRUE);
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        ImplementedInterfaces result = this.mapper.readValue(jsonString, ImplementedInterfaces.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getUpload(), result.getUpload());
        assertEquals(this.obj.getUploadLink(), result.getUploadLink());
        assertEquals(this.obj.getGet(), result.getGet());
        assertEquals(this.obj.getGetSummary(), result.getGetSummary());
        assertEquals(this.obj.getGetByLink(), result.getGetByLink());
        assertEquals(this.obj.getSubscription(), result.getSubscription());
        assertEquals(this.obj.getAccess(), result.getAccess());
        assertEquals(this.obj.getEncryptionKey(), result.getEncryptionKey());
    }

}