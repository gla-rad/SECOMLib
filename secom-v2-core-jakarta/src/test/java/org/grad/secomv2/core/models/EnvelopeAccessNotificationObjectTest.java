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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
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
        this.obj.setDataReference(UUID.randomUUID());
        this.obj.setTransactionIdentifier(UUID.randomUUID());

        // Set signature settings
        this.obj.setEnvelopeSignatureCertificate(new String[]{"certificate"});
        this.obj.setEnvelopeRootCertificateThumbprint("thumbprint");
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
        EnvelopeAccessNotificationObject result = this.mapper.readValue(jsonString, EnvelopeAccessNotificationObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getDecision(), result.getDecision());
        assertEquals(this.obj.getDecisionReason(), result.getDecisionReason());
        assertEquals(this.obj.getDataReference(), result.getDataReference());
        assertEquals(this.obj.getTransactionIdentifier(), result.getTransactionIdentifier());
        assertArrayEquals(this.obj.getEnvelopeSignatureCertificate(), result.getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), result.getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getEnvelopeSignatureTime(), result.getEnvelopeSignatureTime());
        assertEquals(this.obj.getEnvelopeSignatureReference(), result.getEnvelopeSignatureReference());
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
        assertEquals(this.obj.getDecision(), Boolean.parseBoolean(csv[0]));
        assertEquals(this.obj.getDecisionReason(), csv[1]);
        assertEquals(this.obj.getDataReference().toString(), csv[2]);
        assertEquals(this.obj.getTransactionIdentifier().toString(), csv[3]);
        assertEquals(Arrays.toString(this.obj.getEnvelopeSignatureCertificate()), csv[4]);
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), csv[5]);
        assertEquals(this.obj.getEnvelopeSignatureTime().getEpochSecond(), Long.parseLong(csv[6]));
        assertEquals(this.obj.getEnvelopeSignatureReference(), csv[7]);
    }

    /**
     * Test that obj extends AbstractEnvelope
     */
    @Test
    void testObjExtendsAbstractEnvelope() {
        assertTrue(AbstractEnvelope.class.isAssignableFrom(this.obj.getClass()));
    }

}