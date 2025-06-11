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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SummaryObjectTest {

    // Class Variables
    private SummaryObject obj;
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
        this.obj = new SummaryObject();
        this.obj.setDataReference(UUID.randomUUID());
        this.obj.setDataProtection(Boolean.TRUE);
        this.obj.setDataCompression(Boolean.FALSE);
        this.obj.setContainerType(ContainerTypeEnum.S100_DataSet);
        this.obj.setDataProductType(SECOM_DataProductType.S101);
        this.obj.setInfo_identifier("infoIdentifier");
        this.obj.setInfo_name("infoName");
        this.obj.setInfo_status("infoStatus");
        this.obj.setInfo_description("infoDescription");
        this.obj.setInfo_lastModifiedDate(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setInfo_productVersion("infoProductVersion");
        this.obj.setInfo_size(1L);
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        SummaryObject result = this.mapper.readValue(jsonString, SummaryObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getDataReference(), result.getDataReference());
        assertEquals(this.obj.getDataProtection(), result.getDataProtection());
        assertEquals(this.obj.getDataCompression(), result.getDataCompression());
        assertEquals(this.obj.getContainerType(), result.getContainerType());
        assertEquals(this.obj.getDataProductType(), result.getDataProductType());
        assertEquals(this.obj.getInfo_identifier(), result.getInfo_identifier());
        assertEquals(this.obj.getInfo_name(), result.getInfo_name());
        assertEquals(this.obj.getInfo_status(), result.getInfo_status());
        assertEquals(this.obj.getInfo_description(), result.getInfo_description());
        assertEquals(this.obj.getInfo_lastModifiedDate(), result.getInfo_lastModifiedDate());
        assertEquals(this.obj.getInfo_productVersion(), result.getInfo_productVersion());
        assertEquals(this.obj.getInfo_size(), result.getInfo_size());
    }

}