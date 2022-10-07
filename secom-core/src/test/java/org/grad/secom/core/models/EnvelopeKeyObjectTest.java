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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopeKeyObjectTest {

    /**
     * Test that we can correctly generate the SECOM signature CSV.
     */
    @Test
    void testGetCsvString() {
        // Generate a digital signature value
        DigitalSignatureValue digitalSignatureValue = new DigitalSignatureValue();
        digitalSignatureValue.setPublicRootCertificateThumbprint("thumbprint");
        digitalSignatureValue.setPublicCertificate("certificate");
        digitalSignatureValue.setDigitalSignature("signature");

        // Generate a new object
        EnvelopeKeyObject obj = new EnvelopeKeyObject();
        obj.setEncryptionKey("encryptionKey");
        obj.setIv("iv");
        obj.setTransactionIdentifier(UUID.randomUUID());
        obj.setDigitalSignatureValue(digitalSignatureValue);
        obj.setEnvelopeSignatureCertificate("envelopeCertificate");
        obj.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        obj.setEnvelopeSignatureTime(LocalDateTime.now());

        // Generate the signature CSV
        String signatureCSV = obj.getCsvString();

        // Match the individual entries of the string
        String[] csv = signatureCSV.split("\\.");
        assertEquals(obj.getEncryptionKey(), csv[0]);
        assertEquals(obj.getIv(), csv[1]);
        assertEquals(obj.getTransactionIdentifier().toString(), csv[2]);
        assertEquals(obj.getDigitalSignatureValue().getPublicRootCertificateThumbprint(), csv[3]);
        assertEquals(obj.getDigitalSignatureValue().getPublicCertificate(), csv[4]);
        assertEquals(obj.getDigitalSignatureValue().getDigitalSignature(), csv[5]);
        assertEquals(obj.getEnvelopeSignatureCertificate(), csv[6]);
        assertEquals(obj.getEnvelopeRootCertificateThumbprint(), csv[7]);
        assertEquals(obj.getEnvelopeSignatureTime().toEpochSecond(ZoneOffset.UTC), Long.parseLong(csv[8]));
    }

}