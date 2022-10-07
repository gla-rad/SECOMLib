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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigitalSignatureValueTest {

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