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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionKeyRequestObjectTest {

    // Class Variables
    private DigitalSignatureValueObject digitalSignatureValueObject;
    private EnvelopeKeyRequestObject envelopeKeyRequestObject;
    private EncryptionKeyRequestObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Create a digital signature value
        this.digitalSignatureValueObject = new DigitalSignatureValueObject();
        this.digitalSignatureValueObject.setPublicRootCertificateThumbprint("thumbprint");
        this.digitalSignatureValueObject.setPublicCertificate(new String[]{"certificate"});
        this.digitalSignatureValueObject.setDigitalSignature("signature");

        // Create a new envelope upload object
        this.envelopeKeyRequestObject = new EnvelopeKeyRequestObject();
        this.envelopeKeyRequestObject.setDataReference(UUID.randomUUID());
        this.envelopeKeyRequestObject.setDigitalSignatureReference("digitalSignatureReference");
        this.envelopeKeyRequestObject.setPublicCertificate("publicCertificate");
        this.envelopeKeyRequestObject.setEnvelopeSignatureCertificate(new String[]{"envelopeCertificate"});
        this.envelopeKeyRequestObject.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.envelopeKeyRequestObject.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));

        // Generate a new upload object
        this.obj = new EncryptionKeyRequestObject();
        this.obj.setEnvelope(this.envelopeKeyRequestObject);
        this.obj.setDigitalSignature("signature");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EncryptionKeyRequestObject result = this.mapper.readValue(jsonString, EncryptionKeyRequestObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getEnvelope());
        assertEquals(this.obj.getEnvelope().getDataReference(), result.getEnvelope().getDataReference());
        assertEquals(this.obj.getEnvelope().getDigitalSignatureReference(), result.getEnvelope().getDigitalSignatureReference());
        assertEquals(this.obj.getEnvelope().getPublicCertificate(), result.getEnvelope().getPublicCertificate());
        assertArrayEquals(this.obj.getEnvelope().getEnvelopeSignatureCertificate(), result.getEnvelope().getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelope().getEnvelopeRootCertificateThumbprint(), result.getEnvelope().getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getEnvelope().getEnvelopeSignatureTime(), result.getEnvelope().getEnvelopeSignatureTime());
        assertEquals(this.obj.getEnvelopeSignature(), result.getEnvelopeSignature());
    }

}