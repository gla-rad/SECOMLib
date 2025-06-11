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
import org.grad.secom.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class SearchParametersTest {

    // Class Variables
    private SearchParameters obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws URISyntaxException {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Generate a new object
        this.obj = new SearchParameters();
        this.obj.setName("name");
        this.obj.setStatus("status");
        this.obj.setVersion("version");
        this.obj.setKeywords(new String[]{"keywords"});
        this.obj.setDescription("description");
        this.obj.setDataProductType(SECOM_DataProductType.S101);
        this.obj.setSpecificationId("specificationId");
        this.obj.setDesignId("designId");
        this.obj.setInstanceId("instanceId");
        this.obj.setMmsi("mmsi");
        this.obj.setImo("imo");
        this.obj.setServiceType("serviceType");
        this.obj.setUnlocode("unlocode");
        this.obj.setEndpointUri(new URI("http://localhost"));
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        SearchParameters result = this.mapper.readValue(jsonString, SearchParameters.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getName(), result.getName());
        assertEquals(this.obj.getStatus(), result.getStatus());
        assertEquals(this.obj.getVersion(), result.getVersion());
        assertArrayEquals(this.obj.getKeywords(), result.getKeywords());
        assertEquals(this.obj.getDescription(), result.getDescription());
        assertEquals(this.obj.getDataProductType(), result.getDataProductType());
        assertEquals(this.obj.getSpecificationId(), result.getSpecificationId());
        assertEquals(this.obj.getDesignId(), result.getDesignId());
        assertEquals(this.obj.getInstanceId(), result.getInstanceId());
        assertEquals(this.obj.getMmsi(), result.getMmsi());
        assertEquals(this.obj.getImo(), result.getImo());
        assertEquals(this.obj.getServiceType(), result.getServiceType());
        assertEquals(this.obj.getUnlocode(), result.getUnlocode());
        assertEquals(this.obj.getEndpointUri(), result.getEndpointUri());

    }

}