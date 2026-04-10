/*
 * Copyright (c) 2026 AIVeNautics
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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopeGetSummaryFilterObjectTest {

    // Class Variables
    private EnvelopeGetSummaryFilterObject obj;

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
        this.obj = new EnvelopeGetSummaryFilterObject();
        this.obj.setContainerType(ContainerTypeEnum.S100_DataSet);
        this.obj.setDataProductType(SECOM_DataProductType.S101);
        this.obj.setProductVersion("version");
        this.obj.setGeometry("geometry");
        this.obj.setUnlocode("unlocode");
        this.obj.setValidFrom(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setValidTo(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setPage(1);
        this.obj.setPageSize(100);
        this.obj.setEnvelopeSignatureCertificate(new String[]{"envelopeCertificate"});
        this.obj.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.obj.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EnvelopeGetSummaryFilterObject result = this.mapper.readValue(jsonString, EnvelopeGetSummaryFilterObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getContainerType(), result.getContainerType());
        assertEquals(this.obj.getDataProductType(), result.getDataProductType());
        assertEquals(this.obj.getProductVersion(), result.getProductVersion());
        assertEquals(this.obj.getGeometry(), result.getGeometry());
        assertEquals(this.obj.getUnlocode(), result.getUnlocode());
        assertEquals(this.obj.getValidFrom(), result.getValidFrom());
        assertEquals(this.obj.getValidTo(), result.getValidTo());
        assertEquals(this.obj.getPage(), result.getPage());
        assertEquals(this.obj.getPageSize(), result.getPageSize());
        assertArrayEquals(this.obj.getEnvelopeSignatureCertificate(), result.getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), result.getEnvelopeRootCertificateThumbprint());
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
        assertEquals(this.obj.getDataProductType().getValue(), csv[1]);
        assertEquals(this.obj.getProductVersion(), csv[2]);
        assertEquals(this.obj.getGeometry(), csv[3]);
        assertEquals(this.obj.getUnlocode(), csv[4]);
        assertEquals(String.valueOf(this.obj.getValidFrom().getEpochSecond()), csv[5]);
        assertEquals(String.valueOf(this.obj.getValidTo().getEpochSecond()), csv[6]);
        assertEquals(String.valueOf(this.obj.getPage()), csv[7]);
        assertEquals(String.valueOf(this.obj.getPageSize()), csv[8]);
        assertEquals(Arrays.toString(this.obj.getEnvelopeSignatureCertificate()), csv[9]);
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), csv[10]);
        assertEquals(String.valueOf(this.obj.getEnvelopeSignatureTime().getEpochSecond()), csv[11]);
    }

    /**
     * Test that obj extends AbstractEnvelope
     */
    @Test
    void testObjExtendsAbstractEnvelope() {
        assertInstanceOf(AbstractEnvelope.class, this.obj);
    }

}
