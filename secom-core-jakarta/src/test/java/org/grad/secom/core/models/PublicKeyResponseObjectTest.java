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

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the Public Key Object
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
public class PublicKeyResponseObjectTest {

    private PublicKeyResponseObject obj;
    private ObjectMapper mapper;

    /**
     * Set up the tests
     */
    @BeforeEach
    void setUp() {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Create an envelope public key reponse object
        final EnvelopePublicKeyResponseObject envelopePublicKeyResponseObject = new EnvelopePublicKeyResponseObject();
        envelopePublicKeyResponseObject.setPublicCertificate("publicCertificate");
        envelopePublicKeyResponseObject.setDigitalSignatureReference("digitalSignatureRweference");
        envelopePublicKeyResponseObject.setEnvelopeSignatureCertificate(new String[]{"envelopeSignatureCertificate"});
        envelopePublicKeyResponseObject.setEnvelopeRootCertificateThumbprint("envelopeRootCertificateThumbprint");
        envelopePublicKeyResponseObject.setEnvelopeSignatureTime(Instant.now());
        // And populate the public key response object
        obj = new PublicKeyResponseObject();
        obj.setEnvelope(envelopePublicKeyResponseObject);
        obj.setEnvelopeSignature("envelopeSignature");
    }

    /**
     * Test the Public Key Object can be converted to json and back
     *
     * @throws JsonProcessingException
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        PublicKeyResponseObject result = this.mapper.readValue(jsonString, PublicKeyResponseObject.class);

        assertNotNull(result);
        assertNotNull(result.getEnvelope());
        assertEquals(obj.getEnvelope().getPublicCertificate(), result.getEnvelope().getPublicCertificate());
        assertEquals(obj.getEnvelope().getDigitalSignatureReference(), result.getEnvelope().getDigitalSignatureReference());
        assertArrayEquals(obj.getEnvelope().getEnvelopeSignatureCertificate(), result.getEnvelope().getEnvelopeSignatureCertificate());
        assertEquals(obj.getEnvelope().getEnvelopeRootCertificateThumbprint(), result.getEnvelope().getEnvelopeRootCertificateThumbprint());
        assertEquals(obj.getEnvelope().getEnvelopeSignatureTime().truncatedTo(ChronoUnit.SECONDS), result.getEnvelope().getEnvelopeSignatureTime().truncatedTo(ChronoUnit.SECONDS));
        assertEquals(obj.getEnvelopeSignature(), result.getEnvelopeSignature());
    }

}
