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
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionKeyNotificationObjectTest {

    // Class Variables
    private EnvelopeKeyNotificationObject envelopeKeyNotificationObject;
    private EncryptionKeyNotificationObject obj;
    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());

        // Create a new envelop key notification object
        this.envelopeKeyNotificationObject = new EnvelopeKeyNotificationObject();
        this.envelopeKeyNotificationObject.setDataReference(UUID.randomUUID());
        this.envelopeKeyNotificationObject.setEnvelopeSignatureCertificate("signatureCertificate");
        this.envelopeKeyNotificationObject.setPublicCertificate("publicCertificate");
        this.envelopeKeyNotificationObject.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));

        // Generate a new object
        this.obj = new EncryptionKeyNotificationObject();
        this.obj.setEnvelope(this.envelopeKeyNotificationObject);
        this.obj.setEnvelopeSignature("signature");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EncryptionKeyNotificationObject result = this.mapper.readValue(jsonString, EncryptionKeyNotificationObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getEnvelopeSignature());
        assertEquals(this.obj.getEnvelope().getDataReference(), result.getEnvelope().getDataReference());
        assertEquals(this.obj.getEnvelope().getEnvelopeSignatureCertificate(), result.getEnvelope().getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelope().getPublicCertificate(), result.getEnvelope().getPublicCertificate());
        assertEquals(this.obj.getEnvelope().getEnvelopeSignatureTime(), result.getEnvelope().getEnvelopeSignatureTime());
        assertEquals(this.obj.getEnvelopeSignature(), result.getEnvelopeSignature());
    }

}