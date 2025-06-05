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
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.grad.secom.core.models.enums.AckRequestEnum;
import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DataResponseObjectTest {

    // Class Variables
    private DigitalSignatureValueObject digitalSignatureValueObject;
    private SECOM_ServiceExchangeMetadataObject exchangeMetadata;
    private DataResponseObject obj;
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
        this.digitalSignatureValueObject = new DigitalSignatureValueObject();
        this.digitalSignatureValueObject.setPublicRootCertificateThumbprint("thumbprint");
        this.digitalSignatureValueObject.setPublicCertificate("certificate");
        this.digitalSignatureValueObject.setDigitalSignature("signature");

        // Create SECOM exchange metadata
        this.exchangeMetadata = new SECOM_ServiceExchangeMetadataObject();
        this.exchangeMetadata.setDataProtection(Boolean.TRUE);
        this.exchangeMetadata.setProtectionScheme("SECOM");
        this.exchangeMetadata.setDigitalSignatureReference(DigitalSignatureAlgorithmEnum.DSA);
        this.exchangeMetadata.setDigitalSignatureValue(this.digitalSignatureValueObject);
        this.exchangeMetadata.setCompressionFlag(Boolean.FALSE);

        // Generate a new object
        this.obj = new DataResponseObject();
        this.obj.setData("data".getBytes(StandardCharsets.UTF_8));
        this.obj.setExchangeMetadata(this.exchangeMetadata);
        this.obj.setAckRequest(AckRequestEnum.NO_ACK_REQUESTED);
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        DataResponseObject result = this.mapper.readValue(jsonString, DataResponseObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(new String(this.obj.getData()), new String(result.getData()));
        assertEquals(this.obj.getExchangeMetadata().getDataProtection(), result.getExchangeMetadata().getDataProtection());
        assertEquals(this.obj.getExchangeMetadata().getProtectionScheme(), result.getExchangeMetadata().getProtectionScheme());
        assertEquals(this.obj.getExchangeMetadata().getDigitalSignatureReference(), result.getExchangeMetadata().getDigitalSignatureReference());
        assertEquals(this.obj.getExchangeMetadata().getDigitalSignatureValue().getPublicRootCertificateThumbprint(), result.getExchangeMetadata().getDigitalSignatureValue().getPublicRootCertificateThumbprint());
        assertEquals(this.obj.getExchangeMetadata().getDigitalSignatureValue().getPublicCertificate(), result.getExchangeMetadata().getDigitalSignatureValue().getPublicCertificate());
        assertEquals(this.obj.getExchangeMetadata().getDigitalSignatureValue().getDigitalSignature(), result.getExchangeMetadata().getDigitalSignatureValue().getDigitalSignature());
        assertEquals(this.obj.getExchangeMetadata().getCompressionFlag(), result.getExchangeMetadata().getCompressionFlag());
        assertEquals(this.obj.getAckRequest(), result.getAckRequest());
    }
}