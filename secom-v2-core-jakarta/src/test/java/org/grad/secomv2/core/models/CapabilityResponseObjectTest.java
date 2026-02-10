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
import org.grad.secomv2.core.models.enums.ContainerTypeEnum;
import org.grad.secomv2.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.jsr310.JavaTimeModule;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CapabilityResponseObjectTest {

    // Class Variables
    private CapabilityObject capabilityObject;
    private CapabilityResponseObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws MalformedURLException {
        //Setup an object mapper
        this.mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        // Create an implementation interfaces object
        ImplementedInterfaces implementedInterfaces = new ImplementedInterfaces();
        implementedInterfaces.setUpload(Boolean.TRUE);
        implementedInterfaces.setUploadLink(Boolean.TRUE);
        implementedInterfaces.setGet(Boolean.TRUE);
        implementedInterfaces.setGetSummary(Boolean.TRUE);
        implementedInterfaces.setGetByLink(Boolean.TRUE);
        implementedInterfaces.setSubscription(Boolean.TRUE);
        implementedInterfaces.setAccess(Boolean.TRUE);
        implementedInterfaces.setEncryptionKey(Boolean.TRUE);

        // Create a new capability object
        this.capabilityObject = new CapabilityObject();
        this.capabilityObject.setContainerType(ContainerTypeEnum.S100_DataSet);
        this.capabilityObject.setDataProductType(SECOM_DataProductType.S101);
        this.capabilityObject.setProductSchemaUrl(new URL("http://localhost"));
        this.capabilityObject.setImplementedInterfaces(implementedInterfaces);
        this.capabilityObject.setServiceVersion("serviceVersion");

        // Generate a new object
        this.obj = new CapabilityResponseObject();
        this.obj.setCapability(Collections.singletonList(capabilityObject));
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JacksonException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        CapabilityResponseObject result = this.mapper.readValue(jsonString, CapabilityResponseObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getCapability());
        assertEquals(1, result.getCapability().size());
        assertEquals(this.obj.getCapability().get(0).getContainerType(), result.getCapability().get(0).getContainerType());
        assertEquals(this.obj.getCapability().get(0).getDataProductType(), result.getCapability().get(0).getDataProductType());
        assertEquals(this.obj.getCapability().get(0).getProductSchemaUrl(), result.getCapability().get(0).getProductSchemaUrl());
        assertNotNull(result.getCapability().get(0).getImplementedInterfaces());
        assertEquals(this.obj.getCapability().get(0).getImplementedInterfaces().getUpload(), result.getCapability().get(0).getImplementedInterfaces().getUpload());
        assertEquals(this.obj.getCapability().get(0).getImplementedInterfaces().getUploadLink(), result.getCapability().get(0).getImplementedInterfaces().getUploadLink());
        assertEquals(this.obj.getCapability().get(0).getImplementedInterfaces().getGet(), result.getCapability().get(0).getImplementedInterfaces().getGet());
        assertEquals(this.obj.getCapability().get(0).getImplementedInterfaces().getGetSummary(), result.getCapability().get(0).getImplementedInterfaces().getGetSummary());
        assertEquals(this.obj.getCapability().get(0).getImplementedInterfaces().getGetByLink(), result.getCapability().get(0).getImplementedInterfaces().getGetByLink());
        assertEquals(this.obj.getCapability().get(0).getImplementedInterfaces().getSubscription(), result.getCapability().get(0).getImplementedInterfaces().getSubscription());
        assertEquals(this.obj.getCapability().get(0).getImplementedInterfaces().getAccess(), result.getCapability().get(0).getImplementedInterfaces().getAccess());
        assertEquals(this.obj.getCapability().get(0).getImplementedInterfaces().getEncryptionKey(), result.getCapability().get(0).getImplementedInterfaces().getEncryptionKey());
        assertEquals(this.obj.getCapability().get(0).getServiceVersion(), result.getCapability().get(0).getServiceVersion());
    }

}