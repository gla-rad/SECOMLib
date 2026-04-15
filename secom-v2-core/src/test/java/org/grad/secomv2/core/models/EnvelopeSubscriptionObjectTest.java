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

package org.grad.secomv2.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.grad.secomv2.core.models.enums.ContainerTypeEnum;
import org.grad.secomv2.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopeSubscriptionObjectTest {

    // Class Variables
    private EnvelopeSubscriptionObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws URISyntaxException, MalformedURLException {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Generate a new object
        this.obj = new EnvelopeSubscriptionObject();
        this.obj.setContainerType(ContainerTypeEnum.S100_DataSet);
        this.obj.setDataProductType(SECOM_DataProductType.S101);
        this.obj.setDataReference(UUID.randomUUID());
        this.obj.setProductVersion("version");
        this.obj.setGeometry("geometry");
        this.obj.setUnlocode("unlocode");
        this.obj.setSubscriptionPeriodStart(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setSubscriptionPeriodEnd(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setCallbackEndpoint(URI.create("http://localhost").toURL());
        this.obj.setPushAll(Boolean.FALSE);

        // Set signature settings
        this.obj.setEnvelopeSignatureCertificate(new String[]{"certificate"});
        this.obj.setEnvelopeRootCertificateThumbprint("thumbprint");
        this.obj.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setDigitalSignatureReference("digitalSignatureReference");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EnvelopeSubscriptionObject result = this.mapper.readValue(jsonString, EnvelopeSubscriptionObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getContainerType(), result.getContainerType());
        assertEquals(this.obj.getDataProductType(), result.getDataProductType());
        assertEquals(this.obj.getDataReference(), result.getDataReference());
        assertEquals(this.obj.getProductVersion(), result.getProductVersion());
        assertEquals(this.obj.getGeometry(), result.getGeometry());
        assertEquals(this.obj.getUnlocode(), result.getUnlocode());
        assertEquals(this.obj.getSubscriptionPeriodStart(), result.getSubscriptionPeriodStart());
        assertEquals(this.obj.getSubscriptionPeriodEnd(), result.getSubscriptionPeriodEnd());
        assertEquals(this.obj.getCallbackEndpoint(), result.getCallbackEndpoint());
        assertEquals(this.obj.getPushAll(), result.getPushAll());

        assertArrayEquals(this.obj.getEnvelopeSignatureCertificate(), result.getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), result.getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getEnvelopeSignatureTime(), result.getEnvelopeSignatureTime());
        assertEquals(this.obj.getDigitalSignatureReference(), result.getDigitalSignatureReference());
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
        assertEquals(this.obj.getContainerType().asString(), csv[0]);
        assertEquals(this.obj.getDataProductType().asString(), csv[1]);
        assertEquals(this.obj.getDataReference().toString(), csv[2]);
        assertEquals(this.obj.getProductVersion(), csv[3]);
        assertEquals(this.obj.getGeometry(), csv[4]);
        assertEquals(this.obj.getUnlocode(), csv[5]);
        assertEquals(this.obj.getSubscriptionPeriodStart().getEpochSecond(), Long.parseLong(csv[6]));
        assertEquals(this.obj.getSubscriptionPeriodEnd().getEpochSecond(), Long.parseLong(csv[7]));
        assertEquals(this.obj.getCallbackEndpoint().toString(), csv[8]);
        assertEquals(this.obj.getPushAll(), Boolean.parseBoolean(csv[9]));
        assertEquals(Arrays.toString(this.obj.envelopeSignatureCertificate), csv[10]);
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), csv[11]);
        assertEquals(this.obj.getEnvelopeSignatureTime().getEpochSecond(), Long.parseLong(csv[12]));
        assertEquals(this.obj.getDigitalSignatureReference().toString(), csv[13]);
    }

    /**
     * Test that obj extends AbstractEnvelope
     */
    @Test
    void testObjExtendsAbstractEnvelope() {
        assertTrue(AbstractEnvelope.class.isAssignableFrom(this.obj.getClass()));
    }


}