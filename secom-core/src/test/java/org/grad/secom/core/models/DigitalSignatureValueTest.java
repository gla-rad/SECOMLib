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
import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigitalSignatureValueTest {

    // Class Variables
    private DigitalSignatureValue obj;
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
        this.obj = new DigitalSignatureValue();
        this.obj.setPublicRootCertificateThumbprint("thumbprint");
        this.obj.setPublicCertificate("certificate");
        this.obj.setDigitalSignature("signature");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        DigitalSignatureValue result = this.mapper.readValue(jsonString, DigitalSignatureValue.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getPublicRootCertificateThumbprint(), result.getPublicRootCertificateThumbprint());
        assertEquals(this.obj.getPublicCertificate(), result.getPublicCertificate());
        assertEquals(this.obj.getDigitalSignature(), result.getDigitalSignature());
    }

    /**
     * Test that we can correctly generate the SECOM signature CSV.
     */
    @Test
    void testGetCsvString() {
        // Generate a new object
        DigitalSignatureValue obj = new DigitalSignatureValue();
        obj.setPublicRootCertificateThumbprint("thumbprint");
        obj.setPublicCertificate("certificate");
        obj.setDigitalSignature("signature");

        // Generate the signature CSV
        String signatureCSV = obj.getCsvString();

        // Match the individual entries of the string
        String[] csv = signatureCSV.split("\\.");
        assertEquals(obj.getPublicRootCertificateThumbprint(), csv[0]);
        assertEquals(obj.getPublicCertificate(), csv[1]);
        assertEquals(obj.getDigitalSignature(), csv[2]);
    }

}