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
import org.grad.secom.v2.core.models.enums.ContainerTypeEnum;
import org.grad.secom.v2.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GetSummaryResponseObjectTest {

    // Class Variables
    private SummaryObject summaryObject;
    private PaginationObject paginationObject;
    private GetSummaryResponseObject obj;
    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());

        // Create a digital signature value
        this.summaryObject = new SummaryObject();
        this.summaryObject.setDataReference(UUID.randomUUID());
        this.summaryObject.setDataProtection(Boolean.TRUE);
        this.summaryObject.setDataCompression(Boolean.FALSE);
        this.summaryObject.setContainerType(ContainerTypeEnum.S100_DataSet);
        this.summaryObject.setDataProductType(SECOM_DataProductType.S101);
        this.summaryObject.setInfo_identifier("infoIdentifier");
        this.summaryObject.setInfo_name("infoName");
        this.summaryObject.setInfo_status("infoStatus");
        this.summaryObject.setInfo_description("infoDescription");
        this.summaryObject.setInfo_lastModifiedDate(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.summaryObject.setInfo_productVersion("infoProductVersion");
        this.summaryObject.setInfo_size(1L);

        // Create a pagination object
        this.paginationObject = new PaginationObject();
        this.paginationObject.setMaxItemsPerPage(100);
        this.paginationObject.setTotalItems(999);

        // Generate a new object
        this.obj = new GetSummaryResponseObject();
        this.obj.setSummaryObject(Collections.singletonList(this.summaryObject));
        this.obj.setPagination(this.paginationObject);
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        GetSummaryResponseObject result = this.mapper.readValue(jsonString, GetSummaryResponseObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getSummaryObject());
        assertEquals(1, result.getSummaryObject().size());
        assertNotNull(this.obj.getSummaryObject().get(0));
        assertEquals(this.obj.getSummaryObject().get(0).getDataReference(), result.getSummaryObject().get(0).getDataReference());
        assertEquals(this.obj.getSummaryObject().get(0).getDataProtection(), result.getSummaryObject().get(0).getDataProtection());
        assertEquals(this.obj.getSummaryObject().get(0).getDataCompression(), result.getSummaryObject().get(0).getDataCompression());
        assertEquals(this.obj.getSummaryObject().get(0).getContainerType(), result.getSummaryObject().get(0).getContainerType());
        assertEquals(this.obj.getSummaryObject().get(0).getDataProductType(), result.getSummaryObject().get(0).getDataProductType());
        assertEquals(this.obj.getSummaryObject().get(0).getInfo_identifier(), result.getSummaryObject().get(0).getInfo_identifier());
        assertEquals(this.obj.getSummaryObject().get(0).getInfo_name(), result.getSummaryObject().get(0).getInfo_name());
        assertEquals(this.obj.getSummaryObject().get(0).getInfo_status(), result.getSummaryObject().get(0).getInfo_status());
        assertEquals(this.obj.getSummaryObject().get(0).getInfo_description(), result.getSummaryObject().get(0).getInfo_description());
        assertEquals(this.obj.getSummaryObject().get(0).getInfo_lastModifiedDate(), result.getSummaryObject().get(0).getInfo_lastModifiedDate());
        assertEquals(this.obj.getSummaryObject().get(0).getInfo_productVersion(), result.getSummaryObject().get(0).getInfo_productVersion());
        assertEquals(this.obj.getSummaryObject().get(0).getInfo_size(), result.getSummaryObject().get(0).getInfo_size());
        assertNotNull(result.getPagination());
        assertEquals(this.obj.getPagination().getMaxItemsPerPage(), result.getPagination().getMaxItemsPerPage());
        assertEquals(this.obj.getPagination().getTotalItems(), result.getPagination().getTotalItems());
    }

}