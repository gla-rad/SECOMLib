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
import org.grad.secomv2.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopeSearchFilterObjectTest {

    // Class Variables
    private SearchParameters searchParameters;
    private EnvelopeSearchFilterObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws URISyntaxException {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Generate a new search parameters object
        this.searchParameters = new SearchParameters();
        this.searchParameters.setName("name");
        this.searchParameters.setStatus("status");
        this.searchParameters.setVersion("version");
        this.searchParameters.setKeywords(new String[]{"keywords"});
        this.searchParameters.setDescription("description");
        this.searchParameters.setDataProductType(SECOM_DataProductType.S101);
        this.searchParameters.setSpecificationId("specificationId");
        this.searchParameters.setDesignId("designId");
        this.searchParameters.setInstanceId("instanceId");
        this.searchParameters.setMmsi("mmsi");
        this.searchParameters.setImo("imo");
        this.searchParameters.setServiceType("serviceType");
        this.searchParameters.setUnlocode("unlocode");
        this.searchParameters.setEndpointUri(new URI("http://localhost"));

        // Generate a new object
        this.obj = new EnvelopeSearchFilterObject();
        this.obj.setQuery(this.searchParameters);
        this.obj.setGeometry("geometry");
        this.obj.setLocalOnly(true);
        this.obj.setEnvelopeSignatureCertificate(new String[]{"certificate"});
        this.obj.setEnvelopeRootCertificateThumbprint("certificateThumbprint");
        this.obj.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EnvelopeSearchFilterObject result = this.mapper.readValue(jsonString, EnvelopeSearchFilterObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getQuery());
        assertEquals(this.obj.getQuery().getName(), result.getQuery().getName());
        assertEquals(this.obj.getQuery().getStatus(), result.getQuery().getStatus());
        assertEquals(this.obj.getQuery().getVersion(), result.getQuery().getVersion());
        assertArrayEquals(this.obj.getQuery().getKeywords(), result.getQuery().getKeywords());
        assertEquals(this.obj.getQuery().getDescription(), result.getQuery().getDescription());
        assertEquals(this.obj.getQuery().getDataProductType(), result.getQuery().getDataProductType());
        assertEquals(this.obj.getQuery().getSpecificationId(), result.getQuery().getSpecificationId());
        assertEquals(this.obj.getQuery().getDesignId(), result.getQuery().getDesignId());
        assertEquals(this.obj.getQuery().getInstanceId(), result.getQuery().getInstanceId());
        assertEquals(this.obj.getQuery().getMmsi(), result.getQuery().getMmsi());
        assertEquals(this.obj.getQuery().getImo(), result.getQuery().getImo());
        assertEquals(this.obj.getQuery().getServiceType(), result.getQuery().getServiceType());
        assertEquals(this.obj.getQuery().getUnlocode(), result.getQuery().getUnlocode());
        assertEquals(this.obj.getQuery().getEndpointUri(), result.getQuery().getEndpointUri());
        assertEquals(this.obj.getGeometry(), result.getGeometry());
        assertEquals(this.obj.getLocalOnly(), result.getLocalOnly());
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
        assertEquals(this.obj.getQuery().getName(), csv[0]);
        assertEquals(this.obj.getQuery().getStatus(), csv[1]);
        assertEquals(this.obj.getQuery().getVersion(), csv[2]);
        assertEquals(Arrays.toString(this.obj.getQuery().getKeywords()), csv[3]);
        assertEquals(this.obj.getQuery().getDescription(), csv[4]);
        assertEquals(this.obj.getQuery().getDataProductType().asString(), csv[5]);
        assertEquals(this.obj.getQuery().getSpecificationId(), csv[6]);
        assertEquals(this.obj.getQuery().getDesignId(), csv[7]);
        assertEquals(this.obj.getQuery().getInstanceId(), csv[8]);
        assertEquals(this.obj.getQuery().getMmsi(), csv[9]);
        assertEquals(this.obj.getQuery().getImo(), csv[10]);
        assertEquals(this.obj.getQuery().getServiceType(), csv[11]);
        assertEquals(this.obj.getQuery().getUnlocode(), csv[12]);
        assertEquals(this.obj.getQuery().getEndpointUri().toString(), csv[13]);
        assertEquals(this.obj.getGeometry(), csv[14]);
        assertEquals(this.obj.getLocalOnly(), Boolean.parseBoolean(csv[15]));
        assertEquals(Arrays.toString(this.obj.getEnvelopeSignatureCertificate()), csv[16]);
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), csv[17]);
        assertEquals(this.obj.getEnvelopeSignatureTime().getEpochSecond(), Long.parseLong(csv[18]));
    }

    /**
     * Test that obj extends AbstractEnvelope
     */
    @Test
    void testObjExtendsAbstractEnvelope() {
        assertTrue(AbstractEnvelope.class.isAssignableFrom(this.obj.getClass()));
    }

}