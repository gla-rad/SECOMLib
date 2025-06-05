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
import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class CapabilityObjectTest {

    // Class Variables
    private ImplementedInterfaces implementedInterfaces;
    private CapabilityObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() throws MalformedURLException {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());

        // Create an implementation interfaces object
        this.implementedInterfaces = new ImplementedInterfaces();
        this.implementedInterfaces.setUpload(Boolean.TRUE);
        this.implementedInterfaces.setUploadLink(Boolean.TRUE);
        this.implementedInterfaces.setGet(Boolean.TRUE);
        this.implementedInterfaces.setGetSummary(Boolean.TRUE);
        this.implementedInterfaces.setGetByLink(Boolean.TRUE);
        this.implementedInterfaces.setSubscription(Boolean.TRUE);
        this.implementedInterfaces.setAccess(Boolean.TRUE);
        this.implementedInterfaces.setEncryptionKey(Boolean.TRUE);

        // Generate a new object
        this.obj = new CapabilityObject();
        this.obj.setContainerType(ContainerTypeEnum.S100_DataSet);
        this.obj.setDataProductType(SECOM_DataProductType.S101);
        this.obj.setProductSchemaUrl(new URL("http://localhost"));
        this.obj.setImplementedInterfaces(implementedInterfaces);
        this.obj.setServiceVersion("serviceVersion");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        CapabilityObject result = this.mapper.readValue(jsonString, CapabilityObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getContainerType(), result.getContainerType());
        assertEquals(this.obj.getDataProductType(), result.getDataProductType());
        assertEquals(this.obj.getProductSchemaUrl(), result.getProductSchemaUrl());
        assertNotNull(result.getImplementedInterfaces());
        assertEquals(this.obj.getImplementedInterfaces().getUpload(), result.getImplementedInterfaces().getUpload());
        assertEquals(this.obj.getImplementedInterfaces().getUploadLink(), result.getImplementedInterfaces().getUploadLink());
        assertEquals(this.obj.getImplementedInterfaces().getGet(), result.getImplementedInterfaces().getGet());
        assertEquals(this.obj.getImplementedInterfaces().getGetSummary(), result.getImplementedInterfaces().getGetSummary());
        assertEquals(this.obj.getImplementedInterfaces().getGetByLink(), result.getImplementedInterfaces().getGetByLink());
        assertEquals(this.obj.getImplementedInterfaces().getSubscription(), result.getImplementedInterfaces().getSubscription());
        assertEquals(this.obj.getImplementedInterfaces().getAccess(), result.getImplementedInterfaces().getAccess());
        assertEquals(this.obj.getImplementedInterfaces().getEncryptionKey(), result.getImplementedInterfaces().getEncryptionKey());
        assertEquals(this.obj.getServiceVersion(), result.getServiceVersion());
    }

}