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

import org.grad.secom.core.models.enums.AckRequestEnum;
import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopeUploadObjectTest {

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

        // Generate SECOM exchange metadata
        SECOM_ExchangeMetadataObject exchangeMetadata = new SECOM_ExchangeMetadataObject();
        exchangeMetadata.setDataProtection(Boolean.TRUE);
        exchangeMetadata.setProtectionScheme("SECOM");
        exchangeMetadata.setDigitalSignatureReference(DigitalSignatureAlgorithmEnum.DSA);
        exchangeMetadata.setDigitalSignatureValue(digitalSignatureValue);
        exchangeMetadata.setCompressionFlag(Boolean.FALSE);


        // Generate a new object
        EnvelopeUploadObject obj = new EnvelopeUploadObject();
        obj.setData("data");
        obj.setContainerType(ContainerTypeEnum.S100_DataSet);
        obj.setDataProductType(SECOM_DataProductType.S101);
        obj.setExchangeMetadata(exchangeMetadata);
        obj.setFromSubscription(Boolean.FALSE);
        obj.setAckRequest(AckRequestEnum.NO_ACK_REQUESTED);
        obj.setTransactionIdentifier(UUID.randomUUID());
        obj.setEnvelopeSignatureCertificate("envelopeCertificate");
        obj.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        obj.setEnvelopeSignatureTime(LocalDateTime.now());

        // Generate the signature CSV
        String signatureCSV = obj.getCsvString();

        // Match the individual entries of the string
        String[] csv = signatureCSV.split("\\.");
        assertEquals(obj.getData(), csv[0]);
        assertEquals(String.valueOf(obj.getContainerType().getValue()), csv[1]);
        assertEquals(obj.getDataProductType().name(), csv[2]);
        assertEquals(obj.getExchangeMetadata().getDataProtection().toString(), csv[3]);
        assertEquals(obj.getExchangeMetadata().getProtectionScheme(), csv[4]);
        assertEquals(obj.getExchangeMetadata().getDigitalSignatureReference().toString().toLowerCase(), csv[5]);
        assertEquals(obj.getExchangeMetadata().getDigitalSignatureValue().getPublicRootCertificateThumbprint(), csv[6]);
        assertEquals(obj.getExchangeMetadata().getDigitalSignatureValue().getPublicCertificate(), csv[7]);
        assertEquals(obj.getExchangeMetadata().getDigitalSignatureValue().getDigitalSignature(), csv[8]);
        assertEquals(obj.getExchangeMetadata().getCompressionFlag().toString(), csv[9]);
        assertEquals(obj.getFromSubscription().toString(), csv[10]);
        assertEquals(String.valueOf(obj.getAckRequest().getValue()), csv[11]);
        assertEquals(obj.getTransactionIdentifier().toString(), csv[12]);
        assertEquals(obj.getEnvelopeSignatureCertificate(), csv[13]);
        assertEquals(obj.getEnvelopeRootCertificateThumbprint(), csv[14]);
        assertEquals(String.valueOf(obj.getEnvelopeSignatureTime().toEpochSecond(ZoneOffset.UTC)), csv[15]);
    }

}