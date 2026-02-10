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

import tools.jackson.core.JacksonException;

import org.grad.secomv2.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class ServiceInstanceObjectTest {

    // Class Variables
    private ServiceInstanceObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws URISyntaxException {
        //Setup an object mapper
        this.mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

        // Generate a new object
        this.obj = new ServiceInstanceObject();
        this.obj.setInstanceId("instanceId");
        this.obj.setName("name");
        this.obj.setStatus("status");
        this.obj.setDescription("description");
        this.obj.setDataProductType(new SECOM_DataProductType[]{SECOM_DataProductType.S101});
        this.obj.setOrganizationId("organizationId");
        this.obj.setEndpointUri("http://localhost");
        this.obj.setEndpointType(new String[] {"endpointType"});
        this.obj.setVersion("version");
        this.obj.setKeywords(new String[]{"keywords"});
        this.obj.setUnlocode("unlocode");
        this.obj.setInstanceAsXml("instanceAsXml");
        this.obj.setMmsi("mmsi");
        this.obj.setImo("imo");
        this.obj.setCoverageArea(new String[] {"geometry"});
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JacksonException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        ServiceInstanceObject result = this.mapper.readValue(jsonString, ServiceInstanceObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getInstanceId(), result.getInstanceId());
        assertEquals(this.obj.getName(), result.getName());
        assertEquals(this.obj.getStatus(), result.getStatus());
        assertEquals(this.obj.getDescription(), result.getDescription());
        assertArrayEquals(this.obj.getDataProductType(), result.getDataProductType());
        assertEquals(this.obj.getOrganizationId(), result.getOrganizationId());
        assertEquals(this.obj.getEndpointUri(), result.getEndpointUri());
        assertArrayEquals(this.obj.getEndpointType(), result.getEndpointType());
        assertEquals(this.obj.getVersion(), result.getVersion());
        assertArrayEquals(this.obj.getKeywords(), result.getKeywords());
        assertNotNull(result.getUnlocode());
        assertEquals(this.obj.getInstanceAsXml(), result.getInstanceAsXml());
        assertEquals(this.obj.getMmsi(), result.getMmsi());
        assertEquals(this.obj.getImo(), result.getImo());

    }

}