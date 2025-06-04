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

package org.grad.secom.v2.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.grad.secom.v2.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SearchObjectResultTest {

    // Class Variables
    private SearchObjectResult obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws URISyntaxException {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());

        // Generate a new object
        this.obj = new SearchObjectResult();
        this.obj.setInstanceId("instanceId");
        this.obj.setName("name");
        this.obj.setStatus("status");
        this.obj.setDescription("description");
        this.obj.setDataProductType(SECOM_DataProductType.S101);
        this.obj.setOrganizationId("organizationId");
        this.obj.setEndpointUri("http://localhost");
        this.obj.setEndpointType("endpointType");
        this.obj.setVersion("version");
        this.obj.setKeywords("keywords");
        this.obj.setUnlocode("unlocode");
        this.obj.setInstanceAsXml("instanceAsXml");
        this.obj.setPublishedAt(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setLastUpdatedAt(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setMmsi("mmsi");
        this.obj.setImo("imo");
        this.obj.setGeometry("geometry");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        SearchObjectResult result = this.mapper.readValue(jsonString, SearchObjectResult.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getInstanceId(), result.getInstanceId());
        assertEquals(this.obj.getName(), result.getName());
        assertEquals(this.obj.getStatus(), result.getStatus());
        assertEquals(this.obj.getDescription(), result.getDescription());
        assertEquals(this.obj.getDataProductType(), result.getDataProductType());
        assertEquals(this.obj.getOrganizationId(), result.getOrganizationId());
        assertEquals(this.obj.getEndpointUri(), result.getEndpointUri());
        assertEquals(this.obj.getEndpointType(), result.getEndpointType());
        assertEquals(this.obj.getVersion(), result.getVersion());
        assertEquals(this.obj.getKeywords(), result.getKeywords());
        assertNotNull(result.getUnlocode());
        assertEquals(this.obj.getInstanceAsXml(), result.getInstanceAsXml());
        assertEquals(this.obj.getPublishedAt(), result.getPublishedAt());
        assertEquals(this.obj.getLastUpdatedAt(), result.getLastUpdatedAt());
        assertEquals(this.obj.getMmsi(), result.getMmsi());
        assertEquals(this.obj.getImo(), result.getImo());
        assertEquals(this.obj.getGeometry(), result.getGeometry());
    }

}