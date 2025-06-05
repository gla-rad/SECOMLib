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
import org.grad.secom.core.models.enums.AckRequestEnum;
import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UploadObjectTest {

    // Class Variables
    private DigitalSignatureValue digitalSignatureValue;
    private SECOM_ExchangeMetadataObject exchangeMetadata;
    private EnvelopeUploadObject envelopeUploadObject;
    private UploadObject obj;

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

        // Create SECOM exchange metadata
        this.exchangeMetadata = new SECOM_ExchangeMetadataObject();
        this.exchangeMetadata.setDataProtection(Boolean.TRUE);
        this.exchangeMetadata.setProtectionScheme("SECOM");
        this.exchangeMetadata.setDigitalSignatureReference(DigitalSignatureAlgorithmEnum.DSA);
        this.exchangeMetadata.setDigitalSignatureValue(this.digitalSignatureValue);
        this.exchangeMetadata.setCompressionFlag(Boolean.FALSE);

        // Create a new envelope upload object
        this.envelopeUploadObject = new EnvelopeUploadObject();
        this.envelopeUploadObject.setData("data".getBytes(StandardCharsets.UTF_8));
        this.envelopeUploadObject.setContainerType(ContainerTypeEnum.S100_DataSet);
        this.envelopeUploadObject.setDataProductType(SECOM_DataProductType.S101);
        this.envelopeUploadObject.setExchangeMetadata(this.exchangeMetadata);
        this.envelopeUploadObject.setFromSubscription(Boolean.FALSE);
        this.envelopeUploadObject.setAckRequest(AckRequestEnum.NO_ACK_REQUESTED);
        this.envelopeUploadObject.setTransactionIdentifier(UUID.randomUUID());
        this.envelopeUploadObject.setEnvelopeSignatureCertificate("envelopeCertificate");
        this.envelopeUploadObject.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.envelopeUploadObject.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));

        // Generate a new object
        this.obj = new UploadObject();
        this.obj.setEnvelope(this.envelopeUploadObject);
        this.obj.setEnvelopeSignature("signature");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        UploadObject result = this.mapper.readValue(jsonString, UploadObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getEnvelope());
        assertEquals(new String(this.obj.getEnvelope().getData()), new String(result.getEnvelope().getData()));
        assertEquals(this.obj.getEnvelope().getContainerType(), result.getEnvelope().getContainerType());
        assertEquals(this.obj.getEnvelope().getDataProductType(), result.getEnvelope().getDataProductType());
        assertNotNull(result.getEnvelope().getExchangeMetadata());
        assertEquals(this.obj.getEnvelope().getExchangeMetadata().getDataProtection(), result.getEnvelope().getExchangeMetadata().getDataProtection());
        assertEquals(this.obj.getEnvelope().getExchangeMetadata().getProtectionScheme(), result.getEnvelope().getExchangeMetadata().getProtectionScheme());
        assertEquals(this.obj.getEnvelope().getExchangeMetadata().getDigitalSignatureReference(), result.getEnvelope().getExchangeMetadata().getDigitalSignatureReference());
        assertNotNull(result.getEnvelope().getExchangeMetadata().getDigitalSignatureValue());
        assertEquals(this.obj.getEnvelope().getExchangeMetadata().getDigitalSignatureValue().getPublicRootCertificateThumbprint(), result.getEnvelope().getExchangeMetadata().getDigitalSignatureValue().getPublicRootCertificateThumbprint());
        assertEquals(this.obj.getEnvelope().getExchangeMetadata().getDigitalSignatureValue().getPublicCertificate(), result.getEnvelope().getExchangeMetadata().getDigitalSignatureValue().getPublicCertificate());
        assertEquals(this.obj.getEnvelope().getExchangeMetadata().getDigitalSignatureValue().getDigitalSignature(), result.getEnvelope().getExchangeMetadata().getDigitalSignatureValue().getDigitalSignature());
        assertEquals(this.obj.getEnvelope().getExchangeMetadata().getCompressionFlag(), result.getEnvelope().getExchangeMetadata().getCompressionFlag());
        assertEquals(this.obj.getEnvelope().getFromSubscription(), result.getEnvelope().getFromSubscription());
        assertEquals(this.obj.getEnvelope().getAckRequest(), result.getEnvelope().getAckRequest());
        assertEquals(this.obj.getEnvelope().getTransactionIdentifier(), result.getEnvelope().getTransactionIdentifier());
        assertEquals(this.obj.getEnvelope().getEnvelopeSignatureCertificate(), result.getEnvelope().getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelope().getEnvelopeRootCertificateThumbprint(), result.getEnvelope().getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getEnvelope().getEnvelopeSignatureTime(), result.getEnvelope().getEnvelopeSignatureTime());
        assertEquals(this.obj.getEnvelopeSignature(), result.getEnvelopeSignature());
    }

}