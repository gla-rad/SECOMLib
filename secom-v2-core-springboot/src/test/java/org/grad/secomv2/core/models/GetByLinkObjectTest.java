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

class GetByLinkObjectTest {
    // Class Variables
    private DigitalSignatureValueObject digitalSignatureValueObject;
    private EnvelopeGetByLinkObject envelopeGetByLinkObject;
    private GetByLinkObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Set up an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Create a digital signature value
        this.digitalSignatureValueObject = new DigitalSignatureValueObject();
        this.digitalSignatureValueObject.setPublicRootCertificateThumbprint("thumbprint");
        this.digitalSignatureValueObject.setPublicCertificate(new String[]{"certificate"});
        this.digitalSignatureValueObject.setDigitalSignature("signature");

        // Create a new envelope get filter object
        this.envelopeGetByLinkObject = new EnvelopeGetByLinkObject();
        this.envelopeGetByLinkObject.setTransactionIdentifier(UUID.randomUUID());
        this.envelopeGetByLinkObject.setEnvelopeSignatureCertificate(new String[]{"envelopeCertificate"});
        this.envelopeGetByLinkObject.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.envelopeGetByLinkObject.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));

        // Generate a new object
        this.obj = new GetByLinkObject();
        this.obj.setEnvelope(this.envelopeGetByLinkObject);
        this.obj.setDigitalSignature("signature");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        GetByLinkObject result = this.mapper.readValue(jsonString, GetByLinkObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getEnvelope());
        assertEquals(this.obj.getEnvelope().getTransactionIdentifier(), result.getEnvelope().getTransactionIdentifier());
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