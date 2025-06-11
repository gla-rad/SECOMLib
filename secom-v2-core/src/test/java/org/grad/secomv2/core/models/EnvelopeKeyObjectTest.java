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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopeKeyObjectTest {

    // Class Variables
    private DigitalSignatureValueObject digitalSignatureValueObject;
    private EnvelopeKeyObject obj;

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

        // Generate a new object
        this.obj = new EnvelopeKeyObject();
        this.obj.setEncryptionKey("encryptionKey".getBytes());
        this.obj.setIv("iv".getBytes());
        this.obj.setTransactionIdentifier(UUID.randomUUID());
        this.obj.setDigitalSignatureValue(this.digitalSignatureValueObject);
        this.obj.setEnvelopeSignatureCertificate(new String[]{"envelopeCertificate"});
        this.obj.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.obj.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EnvelopeKeyObject result = this.mapper.readValue(jsonString, EnvelopeKeyObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(new String(this.obj.getEncryptionKey(), StandardCharsets.UTF_8), new String(result.getEncryptionKey(), StandardCharsets.UTF_8));
        assertEquals(new String(this.obj.getIv(), StandardCharsets.UTF_8), new String(result.getIv(), StandardCharsets.UTF_8));
        assertEquals(this.obj.getTransactionIdentifier(), result.getTransactionIdentifier());
        assertNotNull(result.getDigitalSignatureValue());
        assertArrayEquals(this.obj.getDigitalSignatureValue().getPublicCertificate(), result.getDigitalSignatureValue().getPublicCertificate());
        assertEquals(this.obj.getDigitalSignatureValue().getPublicRootCertificateThumbprint(), result.getDigitalSignatureValue().getPublicRootCertificateThumbprint());
        assertEquals(this.obj.getDigitalSignatureValue().getDigitalSignature(), result.getDigitalSignatureValue().getDigitalSignature());
        assertArrayEquals(this.obj.getEnvelopeSignatureCertificate(), result.getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), result.getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getEnvelopeSignatureTime(), result.getEnvelopeSignatureTime());
    }

    /**
     * Test that we can correctly generate the SECOM signature CSV.
     */
    @Test
    void testGetCsvString() {
        // Generate the signature CSV
        String signatureCSV = this.obj.getCsvString();

        // Match the individual entries of the string
        String[] csv = signatureCSV.split("\\.");
        assertEquals(new String(obj.getEncryptionKey(), StandardCharsets.UTF_8), new String(Base64.getDecoder().decode(csv[0])));
        assertEquals(new String(obj.getIv(), StandardCharsets.UTF_8), new String(Base64.getDecoder().decode(csv[1])));
        assertEquals(obj.getTransactionIdentifier().toString(), csv[2]);
        assertEquals(obj.getDigitalSignatureValue().getPublicRootCertificateThumbprint(), csv[3]);
        assertEquals(Arrays.toString(obj.getDigitalSignatureValue().getPublicCertificate()), csv[4]);
        assertEquals(obj.getDigitalSignatureValue().getDigitalSignature(), csv[5]);
        assertEquals(Arrays.toString(obj.getEnvelopeSignatureCertificate()), csv[6]);
        assertEquals(obj.getEnvelopeRootCertificateThumbprint(), csv[7]);
        assertEquals(obj.getEnvelopeSignatureTime().getEpochSecond(), Long.parseLong(csv[8]));
    }

}