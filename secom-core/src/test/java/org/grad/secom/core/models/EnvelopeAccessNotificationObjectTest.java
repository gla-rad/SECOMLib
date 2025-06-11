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

package org.grad.secom.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopeAccessNotificationObjectTest {

    // Class Variables
    private EnvelopeAccessNotificationObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Generate a new object
        this.obj = new EnvelopeAccessNotificationObject();
        this.obj.setDecision(Boolean.TRUE);
        this.obj.setDecisionReason("Test");
        this.obj.setTransactionIdentifier(UUID.randomUUID());
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EnvelopeAccessNotificationObject result = this.mapper.readValue(jsonString, EnvelopeAccessNotificationObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getDecision(), result.getDecision());
        assertEquals(this.obj.getDecisionReason(), result.getDecisionReason());
        assertEquals(this.obj.getTransactionIdentifier(), result.getTransactionIdentifier());
    }
}