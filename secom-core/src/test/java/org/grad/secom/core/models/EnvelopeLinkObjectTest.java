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

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopeLinkObjectTest {

    // Class Variables
    private DigitalSignatureValue digitalSignatureValue;
    private SECOM_ExchangeMetadataObject exchangeMetadata;
    private EnvelopeLinkObject obj;

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

        // Generate a new object
        this.obj = new EnvelopeLinkObject();
        this.obj.setContainerType(ContainerTypeEnum.S100_DataSet);
        this.obj.setDataProductType(SECOM_DataProductType.S101);
        this.obj.setExchangeMetadata(this.exchangeMetadata);
        this.obj.setFromSubscription(Boolean.FALSE);
        this.obj.setAckRequest(AckRequestEnum.NO_ACK_REQUESTED);
        this.obj.setTransactionIdentifier(UUID.randomUUID());
        this.obj.setEnvelopeSignatureCertificate("envelopeCertificate");
        this.obj.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.obj.setSize(1);
        this.obj.setTimeToLive(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EnvelopeLinkObject result = this.mapper.readValue(jsonString, EnvelopeLinkObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getContainerType(), result.getContainerType());
        assertEquals(this.obj.getDataProductType(), result.getDataProductType());
        assertNotNull(result.getExchangeMetadata());
        assertEquals(this.obj.getExchangeMetadata().getDataProtection(), result.getExchangeMetadata().getDataProtection());
        assertEquals(this.obj.getExchangeMetadata().getProtectionScheme(), result.getExchangeMetadata().getProtectionScheme());
        assertEquals(this.obj.getExchangeMetadata().getDigitalSignatureReference(), result.getExchangeMetadata().getDigitalSignatureReference());
        assertNotNull(result.getExchangeMetadata().getDigitalSignatureValue());
        assertEquals(this.obj.getExchangeMetadata().getDigitalSignatureValue().getPublicRootCertificateThumbprint(), result.getExchangeMetadata().getDigitalSignatureValue().getPublicRootCertificateThumbprint());
        assertEquals(this.obj.getExchangeMetadata().getDigitalSignatureValue().getPublicCertificate(), result.getExchangeMetadata().getDigitalSignatureValue().getPublicCertificate());
        assertEquals(this.obj.getExchangeMetadata().getDigitalSignatureValue().getDigitalSignature(), result.getExchangeMetadata().getDigitalSignatureValue().getDigitalSignature());
        assertEquals(this.obj.getExchangeMetadata().getCompressionFlag(), result.getExchangeMetadata().getCompressionFlag());
        assertEquals(this.obj.getAckRequest(), result.getAckRequest());
        assertEquals(this.obj.getTransactionIdentifier(), result.getTransactionIdentifier());
        assertEquals(this.obj.getEnvelopeSignatureCertificate(), result.getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), result.getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getSize(), result.getSize());
        assertEquals(this.obj.getTimeToLive(), result.getTimeToLive());
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
        assertEquals(String.valueOf(this.obj.getContainerType().getValue()), csv[0]);
        assertEquals(this.obj.getDataProductType().name(), csv[1]);
        assertEquals(this.obj.getExchangeMetadata().getDataProtection().toString(), csv[2]);
        assertEquals(this.obj.getExchangeMetadata().getProtectionScheme(), csv[3]);
        assertEquals(this.obj.getExchangeMetadata().getDigitalSignatureReference().toString().toLowerCase(), csv[4]);
        assertEquals(this.obj.getExchangeMetadata().getDigitalSignatureValue().getPublicRootCertificateThumbprint(), csv[5]);
        assertEquals(this.obj.getExchangeMetadata().getDigitalSignatureValue().getPublicCertificate(), csv[6]);
        assertEquals(this.obj.getExchangeMetadata().getDigitalSignatureValue().getDigitalSignature(), csv[7]);
        assertEquals(this.obj.getExchangeMetadata().getCompressionFlag().toString(), csv[8]);
        assertEquals(this.obj.getFromSubscription().toString(), csv[9]);
        assertEquals(String.valueOf(this.obj.getAckRequest().getValue()), csv[10]);
        assertEquals(this.obj.getTransactionIdentifier().toString(), csv[11]);
        assertEquals(this.obj.getEnvelopeSignatureCertificate(), csv[12]);
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), csv[13]);
        assertEquals(String.valueOf(this.obj.getSize()), csv[14]);
        assertEquals(String.valueOf(this.obj.getTimeToLive().getEpochSecond()), csv[15]);
        assertEquals(String.valueOf(this.obj.getEnvelopeSignatureTime().getEpochSecond()), csv[16]);
    }

}