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
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.grad.secom.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ResponseSearchObjectTest {

    // Class Variables
    private ResponseSearchObject obj;
    private SearchObjectResult searchObjectResult;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws URISyntaxException {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());

        // Generate a new search object result
        this.searchObjectResult = new SearchObjectResult();
        this.searchObjectResult.setInstanceId("instanceId");
        this.searchObjectResult.setName("name");
        this.searchObjectResult.setStatus("status");
        this.searchObjectResult.setDescription("description");
        this.searchObjectResult.setDataProductType(SECOM_DataProductType.S101);
        this.searchObjectResult.setOrganizationId("organizationId");
        this.searchObjectResult.setEndpointUri("http://localhost");
        this.searchObjectResult.setEndpointType("endpointType");
        this.searchObjectResult.setVersion("version");
        this.searchObjectResult.setKeywords("keywords");
        this.searchObjectResult.setUnlocode(Collections.singletonList("unlocode"));
        this.searchObjectResult.setInstanceAsXml("instanceAsXml");
        this.searchObjectResult.setPublishedAt(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.searchObjectResult.setLastUpdatedAt(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.searchObjectResult.setMmsi("mmsi");
        this.searchObjectResult.setImo("imo");
        this.searchObjectResult.setGeometry("geometry");

        // Generate a new object
        this.obj = new ResponseSearchObject();
        this.obj.setSearchServiceResult(Collections.singletonList(this.searchObjectResult));
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        ResponseSearchObject result = this.mapper.readValue(jsonString, ResponseSearchObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getSearchServiceResult());
        assertEquals(1, result.getSearchServiceResult().size());
        assertNotNull(result.getSearchServiceResult().get(0));
        assertEquals(this.obj.getSearchServiceResult().get(0).getInstanceId(), result.getSearchServiceResult().get(0).getInstanceId());
        assertEquals(this.obj.getSearchServiceResult().get(0).getName(), result.getSearchServiceResult().get(0).getName());
        assertEquals(this.obj.getSearchServiceResult().get(0).getStatus(), result.getSearchServiceResult().get(0).getStatus());
        assertEquals(this.obj.getSearchServiceResult().get(0).getDescription(), result.getSearchServiceResult().get(0).getDescription());
        assertEquals(this.obj.getSearchServiceResult().get(0).getDataProductType(), result.getSearchServiceResult().get(0).getDataProductType());
        assertEquals(this.obj.getSearchServiceResult().get(0).getOrganizationId(), result.getSearchServiceResult().get(0).getOrganizationId());
        assertEquals(this.obj.getSearchServiceResult().get(0).getEndpointUri(), result.getSearchServiceResult().get(0).getEndpointUri());
        assertEquals(this.obj.getSearchServiceResult().get(0).getEndpointType(), result.getSearchServiceResult().get(0).getEndpointType());
        assertEquals(this.obj.getSearchServiceResult().get(0).getVersion(), result.getSearchServiceResult().get(0).getVersion());
        assertEquals(this.obj.getSearchServiceResult().get(0).getKeywords(), result.getSearchServiceResult().get(0).getKeywords());
        assertNotNull(result.getSearchServiceResult().get(0).getUnlocode());
        assertEquals(1, result.getSearchServiceResult().get(0).getUnlocode().size());
        assertEquals(this.obj.getSearchServiceResult().get(0).getUnlocode().get(0), result.getSearchServiceResult().get(0).getUnlocode().get(0));
        assertEquals(this.obj.getSearchServiceResult().get(0).getInstanceAsXml(), result.getSearchServiceResult().get(0).getInstanceAsXml());
        assertEquals(this.obj.getSearchServiceResult().get(0).getPublishedAt(), result.getSearchServiceResult().get(0).getPublishedAt());
        assertEquals(this.obj.getSearchServiceResult().get(0).getLastUpdatedAt(), result.getSearchServiceResult().get(0).getLastUpdatedAt());
        assertEquals(this.obj.getSearchServiceResult().get(0).getMmsi(), result.getSearchServiceResult().get(0).getMmsi());
        assertEquals(this.obj.getSearchServiceResult().get(0).getImo(), result.getSearchServiceResult().get(0).getImo());
        assertEquals(this.obj.getSearchServiceResult().get(0).getGeometry(), result.getSearchServiceResult().get(0).getGeometry());
    }

}