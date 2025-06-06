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
import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SECOM_ExchangeMetadataObjectTest {

    // Class Variables
    private DigitalSignatureValue digitalSignatureValue;
    private SECOM_ExchangeMetadataObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws URISyntaxException {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());

        // Generate a digital signature value
        this.digitalSignatureValue = new DigitalSignatureValue();
        this.digitalSignatureValue.setPublicRootCertificateThumbprint("thumbprint");
        this.digitalSignatureValue.setPublicCertificate("certificate");
        this.digitalSignatureValue.setDigitalSignature("signature");

        // Generate a new object
        this.obj = new SECOM_ExchangeMetadataObject();
        this.obj.setDataProtection(Boolean.TRUE);
        this.obj.setProtectionScheme("SECOM");
        this.obj.setDigitalSignatureReference(DigitalSignatureAlgorithmEnum.DSA);
        this.obj.setDigitalSignatureValue(this.digitalSignatureValue);
        this.obj.setCompressionFlag(Boolean.FALSE);
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        SECOM_ExchangeMetadataObject result = this.mapper.readValue(jsonString, SECOM_ExchangeMetadataObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getDataProtection(), result.getDataProtection());
        assertEquals(this.obj.getProtectionScheme(), result.getProtectionScheme());
        assertEquals(this.obj.getDigitalSignatureReference(), result.getDigitalSignatureReference());
        assertNotNull(result.getDigitalSignatureValue());
        assertEquals(this.obj.getDigitalSignatureValue().getPublicRootCertificateThumbprint(), result.getDigitalSignatureValue().getPublicRootCertificateThumbprint());
        assertEquals(this.obj.getDigitalSignatureValue().getPublicCertificate(), result.getDigitalSignatureValue().getPublicCertificate());
        assertEquals(this.obj.getDigitalSignatureValue().getDigitalSignature(), result.getDigitalSignatureValue().getDigitalSignature());
        assertEquals(this.obj.getCompressionFlag(), result.getCompressionFlag());
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
        assertEquals(this.obj.getDataProtection().toString(), csv[0]);
        assertEquals(this.obj.getProtectionScheme(), csv[1]);
        assertEquals(this.obj.getDigitalSignatureReference().toString().toLowerCase(), csv[2]);
        assertEquals(this.obj.getDigitalSignatureValue().getPublicRootCertificateThumbprint(), csv[3]);
        assertEquals(this.obj.getDigitalSignatureValue().getPublicCertificate(), csv[4]);
        assertEquals(this.obj.getDigitalSignatureValue().getDigitalSignature(), csv[5]);
        assertEquals(this.obj.getCompressionFlag().toString(), csv[6]);
    }

}