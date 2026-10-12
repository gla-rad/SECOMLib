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

package org.grad.secomv2.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.grad.secomv2.core.base.EnvelopeSignatureBearer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RemoveSubscriptionObjectTest {

    // Class Variables
    private EnvelopeRemoveSubscriptionObject envelopeRemoveSubscriptionObject;
    private RemoveSubscriptionObject obj;
    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Create a new envelope remove subscription object
        this.envelopeRemoveSubscriptionObject = new EnvelopeRemoveSubscriptionObject();
        this.envelopeRemoveSubscriptionObject.setSubscriptionIdentifier(UUID.randomUUID());
        this.envelopeRemoveSubscriptionObject.setEnvelopeSignatureCertificate(new String[]{"envelopeCertificate"});
        this.envelopeRemoveSubscriptionObject.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.envelopeRemoveSubscriptionObject.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));

        // Generate a new object
        this.obj = new RemoveSubscriptionObject();
        this.obj.setEnvelope(this.envelopeRemoveSubscriptionObject);
        this.obj.setEnvelopeSignature("signature");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        RemoveSubscriptionObject result = this.mapper.readValue(jsonString, RemoveSubscriptionObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getEnvelope());
        assertEquals(this.obj.getEnvelope().getSubscriptionIdentifier(), result.getEnvelope().getSubscriptionIdentifier());
        assertArrayEquals(this.obj.getEnvelope().getEnvelopeSignatureCertificate(), result.getEnvelope().getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelope().getEnvelopeRootCertificateThumbprint(), result.getEnvelope().getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getEnvelope().getEnvelopeSignatureTime(), result.getEnvelope().getEnvelopeSignatureTime());
        assertEquals(this.obj.getEnvelopeSignature(), result.getEnvelopeSignature());
    }

    /**
     * Test that obj implements EnvelopeSignatureBearer
     */
    @Test
    void testObjImplementsEnvelopeSignatureBearer() {
        assertTrue(EnvelopeSignatureBearer.class.isAssignableFrom(this.obj.getClass()));
    }

}
