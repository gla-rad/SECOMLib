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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DigitalSignatureValueObjectTest {

    // Class Variables
    private DigitalSignatureValueObject obj;
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
        this.obj = new DigitalSignatureValueObject();
        this.obj.setPublicRootCertificateThumbprint("thumbprint");
        this.obj.setPublicCertificate(new String[]{"certificate"});
        this.obj.setDigitalSignature("signature");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        DigitalSignatureValueObject result = this.mapper.readValue(jsonString, DigitalSignatureValueObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getPublicRootCertificateThumbprint(), result.getPublicRootCertificateThumbprint());
        assertArrayEquals(this.obj.getPublicCertificate(), result.getPublicCertificate());
        assertEquals(this.obj.getDigitalSignature(), result.getDigitalSignature());
    }

    /**
     * Test that we can correctly generate the SECOM signature CSV.
     */
    @Test
    void testGetCsvString() {
        // Generate a new object
        DigitalSignatureValueObject obj = new DigitalSignatureValueObject();
        obj.setPublicRootCertificateThumbprint("thumbprint");
        obj.setPublicCertificate(new String[]{"certificate"});
        obj.setDigitalSignature("signature");

        // Generate the signature CSV
        String signatureCSV = obj.getCsvString();

        // Match the individual entries of the string
        String[] csv = signatureCSV.split("\\.");
        assertEquals(obj.getPublicRootCertificateThumbprint(), csv[0]);
        assertEquals(Arrays.toString(obj.getPublicCertificate()), csv[1]);
        assertEquals(obj.getDigitalSignature(), csv[2]);
    }

}