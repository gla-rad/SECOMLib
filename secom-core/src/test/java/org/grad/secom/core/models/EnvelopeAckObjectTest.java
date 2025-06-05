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
import org.grad.secom.core.models.enums.AckTypeEnum;
import org.grad.secom.core.models.enums.NackTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EnvelopeAckObjectTest {

    // Class Variables
    private EnvelopeAckObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());

        // Generate a new object
        this.obj = new EnvelopeAckObject();
        this.obj.setCreatedAt(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setEnvelopeSignatureCertificate("envelopeCertificate");
        this.obj.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.obj.setTransactionIdentifier(UUID.randomUUID());
        this.obj.setAckType(AckTypeEnum.OPENED_ACK);
        this.obj.setNackType(NackTypeEnum.UNKNOWN_DATA_TYPE_OR_VERSION);
        this.obj.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setDigitalSignatureReference("signatureRef");
        this.obj.setDataReference(UUID.randomUUID());
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EnvelopeAckObject result = this.mapper.readValue(jsonString, EnvelopeAckObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getCreatedAt(), result.getCreatedAt());
        assertEquals(this.obj.getEnvelopeCertificate(), result.getEnvelopeCertificate());
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), result.getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getTransactionIdentifier(), result.getTransactionIdentifier());
        assertEquals(this.obj.getAckType(), result.getAckType());
        assertEquals(this.obj.getNackType(), result.getNackType());
        assertEquals(this.obj.getEnvelopeSignatureTime(), result.getEnvelopeSignatureTime());
        assertEquals(this.obj.getDigitalSignatureReference(), result.getDigitalSignatureReference());
        assertEquals(this.obj.getDataReference(), result.getDataReference());
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
        assertEquals(this.obj.getCreatedAt().getEpochSecond(), Long.parseLong(csv[0]));
        assertEquals(this.obj.getEnvelopeCertificate(), csv[1]);
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), csv[2]);
        assertEquals(this.obj.getTransactionIdentifier().toString(), csv[3]);
        assertEquals(String.valueOf(this.obj.getAckType().getValue()), csv[4]);
        assertEquals(String.valueOf(this.obj.getNackType().getValue()), csv[5]);
        assertEquals(String.valueOf(this.obj.getEnvelopeSignatureTime().getEpochSecond()), csv[6]);
        assertEquals(this.obj.getDigitalSignatureReference(), csv[7]);
        assertEquals(this.obj.getDataReference().toString(), csv[8]);
    }

}