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
import org.grad.secom.core.models.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AcknowledgementObjectTest {

    // Class Variables
    private DigitalSignatureValue digitalSignatureValue;
    private EnvelopeAckObject envelopeLinkObject;
    private AcknowledgementObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());

        // Create a digital signature value
        this.digitalSignatureValue = new DigitalSignatureValue();
        this.digitalSignatureValue.setPublicRootCertificateThumbprint("thumbprint");
        this.digitalSignatureValue.setPublicCertificate("certificate");
        this.digitalSignatureValue.setDigitalSignature("signature");

        // Create a new envelope upload object
        this.envelopeLinkObject = new EnvelopeAckObject();
        this.envelopeLinkObject.setCreatedAt(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.envelopeLinkObject.setTransactionIdentifier(UUID.randomUUID());
        this.envelopeLinkObject.setAckType(AckTypeEnum.DELIVERED_ACK);
        this.envelopeLinkObject.setNackType(NackTypeEnum.UNKNOWN_DATA_TYPE_OR_VERSION);
        this.envelopeLinkObject.setEnvelopeSignatureCertificate("envelopeCertificate");
        this.envelopeLinkObject.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.envelopeLinkObject.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));

        // Generate a new object
        this.obj = new AcknowledgementObject();
        this.obj.setEnvelope(this.envelopeLinkObject);
        this.obj.setEnvelopeSignature("signature");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        AcknowledgementObject result = this.mapper.readValue(jsonString, AcknowledgementObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getEnvelope());
        assertEquals(this.obj.getEnvelope().getCreatedAt(), result.getEnvelope().getCreatedAt());
        assertEquals(this.obj.getEnvelope().getTransactionIdentifier(), result.getEnvelope().getTransactionIdentifier());
        assertEquals(this.obj.getEnvelope().getAckType(), result.getEnvelope().getAckType());
        assertEquals(this.obj.getEnvelope().getNackType(), result.getEnvelope().getNackType());
        assertEquals(this.obj.getEnvelope().getEnvelopeSignatureCertificate(), result.getEnvelope().getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelope().getEnvelopeRootCertificateThumbprint(), result.getEnvelope().getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getEnvelope().getEnvelopeSignatureTime(), result.getEnvelope().getEnvelopeSignatureTime());
        assertEquals(this.obj.getEnvelopeSignature(), result.getEnvelopeSignature());
    }

}