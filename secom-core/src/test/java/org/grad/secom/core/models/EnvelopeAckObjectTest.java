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

import org.grad.secom.core.models.enums.AckTypeEnum;
import org.grad.secom.core.models.enums.NackTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvelopeAckObjectTest {

    /**
     * Test that we can correctly generate the SECOM signature CSV.
     */
    @Test
    void testGetCsvString() {
        // Generate a new object
        EnvelopeAckObject obj = new EnvelopeAckObject();
        obj.setCreatedAt(LocalDateTime.now());
        obj.setEnvelopeSignatureCertificate("envelopeCertificate");
        obj.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        obj.setTransactionIdentifier(UUID.randomUUID());
        obj.setAckType(AckTypeEnum.OPENED_ACK);
        obj.setNackType(NackTypeEnum.UNKNOWN_DATA_TYPE_OR_VERSION);
        obj.setEnvelopeSignatureTime(LocalDateTime.now());

        // Generate the signature CSV
        String signatureCSV = obj.getCsvString();

        // Match the individual entries of the string
        String[] csv = signatureCSV.split("\\.");
        assertEquals(obj.getCreatedAt().toEpochSecond(ZoneOffset.UTC), Long.parseLong(csv[0]));
        assertEquals(obj.getEnvelopeCertificate(), csv[1]);
        assertEquals(obj.getEnvelopeRootCertificateThumbprint(), csv[2]);
        assertEquals(obj.getTransactionIdentifier().toString(), csv[3]);
        assertEquals(String.valueOf(obj.getAckType().getValue()), csv[4]);
        assertEquals(String.valueOf(obj.getNackType().getValue()), csv[5]);
        assertEquals(String.valueOf(obj.getEnvelopeSignatureTime().toEpochSecond(ZoneOffset.UTC)), csv[6]);
    }

}