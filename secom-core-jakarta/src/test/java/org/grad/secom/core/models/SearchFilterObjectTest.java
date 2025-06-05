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
import org.grad.secom.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class SearchFilterObjectTest {

    // Class Variables
    private SearchParameters searchParameters;
    private SearchFilterObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws URISyntaxException {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());

        // Generate a new search parameters object
        this.searchParameters = new SearchParameters();
        this.searchParameters.setName("name");
        this.searchParameters.setStatus("status");
        this.searchParameters.setVersion("version");
        this.searchParameters.setKeywords("keywords");
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
        this.searchParameters.setPage(0);
        this.searchParameters.setPageSize(100);

        // Generate a new object
        this.obj = new SearchFilterObject();
        this.obj.setQuery(this.searchParameters);
        this.obj.setGeometry("geometry");
        this.obj.setFreetext("freeText");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        SearchFilterObject result = this.mapper.readValue(jsonString, SearchFilterObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getQuery());
        assertEquals(this.obj.getQuery().getName(), result.getQuery().getName());
        assertEquals(this.obj.getQuery().getStatus(), result.getQuery().getStatus());
        assertEquals(this.obj.getQuery().getVersion(), result.getQuery().getVersion());
        assertEquals(this.obj.getQuery().getKeywords(), result.getQuery().getKeywords());
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
        assertEquals(this.obj.getQuery().getPage(), result.getQuery().getPage());
        assertEquals(this.obj.getQuery().getPageSize(), result.getQuery().getPageSize());
        assertEquals(this.obj.getGeometry(), result.getGeometry());
        assertEquals(this.obj.getFreetext(), result.getFreetext());
    }

}