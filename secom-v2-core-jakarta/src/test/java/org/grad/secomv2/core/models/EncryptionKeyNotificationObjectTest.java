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

import tools.jackson.core.JacksonException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.jsr310.JavaTimeModule;

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
        this.mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        // Create a new envelop key notification object
        this.envelopeKeyNotificationObject = new EnvelopeKeyNotificationObject();
        this.envelopeKeyNotificationObject.setDataReference(UUID.randomUUID());
        this.envelopeKeyNotificationObject.setEnvelopeSignatureCertificate(new String[]{"signatureCertificate"});
        this.envelopeKeyNotificationObject.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
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
    void testJson() throws JacksonException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EncryptionKeyNotificationObject result = this.mapper.readValue(jsonString, EncryptionKeyNotificationObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getEnvelopeSignature());
        assertEquals(this.obj.getEnvelope().getDataReference(), result.getEnvelope().getDataReference());
        assertArrayEquals(this.obj.getEnvelope().getEnvelopeSignatureCertificate(), result.getEnvelope().getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelope().getEnvelopeRootCertificateThumbprint(), result.getEnvelope().getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getEnvelope().getEnvelopeSignatureTime(), result.getEnvelope().getEnvelopeSignatureTime());
        assertEquals(this.obj.getEnvelopeSignature(), result.getEnvelopeSignature());
    }

}