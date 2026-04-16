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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {Description}
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
public class EnvelopeKeyRequestObjectTest {

    // Class Variables
    private DigitalSignatureValueObject digitalSignatureValueObject;
    private EnvelopeKeyRequestObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws URISyntaxException, MalformedURLException {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Create a digital signature value
        this.digitalSignatureValueObject = new DigitalSignatureValueObject();
        this.digitalSignatureValueObject.setPublicRootCertificateThumbprint("thumbprint");
        this.digitalSignatureValueObject.setPublicCertificate(new String[]{"certificate"});
        this.digitalSignatureValueObject.setDigitalSignature("signature");

        // Generate a new object
        this.obj = new EnvelopeKeyRequestObject();
        this.obj.setDataReference(UUID.randomUUID());
        this.obj.setPublicCertificate("certificate");
        this.obj.setCallbackEndpoint(new URI("https://callbackendpoint").toURL());

        this.obj.setEnvelopeSignatureCertificate(new String[]{"envelopeCertificate"});
        this.obj.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.obj.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setEnvelopeSignatureReference("digitalSignatureReference");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EnvelopeKeyRequestObject result = this.mapper.readValue(jsonString, EnvelopeKeyRequestObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getDataReference(), result.getDataReference());
        assertEquals(this.obj.getPublicCertificate(), result.getPublicCertificate());
        assertEquals(this.obj.getCallbackEndpoint().toString(), result.getCallbackEndpoint().toString());
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
        assertEquals(this.obj.getDataReference().toString(), csv[0]);
        assertEquals(this.obj.getPublicCertificate(), csv[1]);
        assertEquals(Arrays.toString(this.obj.getEnvelopeSignatureCertificate()), csv[2]);
        assertEquals(this.obj.getEnvelopeSignatureTime().getEpochSecond(), Long.parseLong(csv[3]));
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), csv[4]);
        assertEquals(this.obj.getCallbackEndpoint().toString(), csv[5]);
        assertEquals(this.obj.getEnvelopeSignatureReference(), csv[6]);
    }

    /**
     * Test that obj extends AbstractEnvelope
     */
    @Test
    void testObjExtendsAbstractEnvelope() {
        assertTrue(AbstractEnvelope.class.isAssignableFrom(this.obj.getClass()));
    }

}
