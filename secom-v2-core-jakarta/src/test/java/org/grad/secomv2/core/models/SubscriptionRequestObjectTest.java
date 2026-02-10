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

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SubscriptionRequestObjectTest {

    // Class Variables
    private EnvelopeSubscriptionObject envelopeSubscriptionObject;
    private SubscriptionRequestObject obj;

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
        this.envelopeSubscriptionObject = new EnvelopeSubscriptionObject();
        this.envelopeSubscriptionObject.setContainerType(ContainerTypeEnum.S100_DataSet);
        this.envelopeSubscriptionObject.setDataProductType(SECOM_DataProductType.S101);
        this.envelopeSubscriptionObject.setDataReference(UUID.randomUUID());
        this.envelopeSubscriptionObject.setProductVersion("version");
        this.envelopeSubscriptionObject.setGeometry("geometry");
        this.envelopeSubscriptionObject.setUnlocode("unlocode");
        this.envelopeSubscriptionObject.setSubscriptionPeriodStart(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.envelopeSubscriptionObject.setSubscriptionPeriodEnd(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj = new SubscriptionRequestObject();
        this.obj.setEnvelope(this.envelopeSubscriptionObject);
        this.obj.setEnvelopeSignature("envelopeSignature");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JacksonException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        SubscriptionRequestObject result = this.mapper.readValue(jsonString, SubscriptionRequestObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getEnvelope());
        assertEquals(this.obj.getEnvelope().getContainerType(), result.getEnvelope().getContainerType());
        assertEquals(this.obj.getEnvelope().getDataProductType(), result.getEnvelope().getDataProductType());
        assertEquals(this.obj.getEnvelope().getDataReference(), result.getEnvelope().getDataReference());
        assertEquals(this.obj.getEnvelope().getProductVersion(), result.getEnvelope().getProductVersion());
        assertEquals(this.obj.getEnvelope().getGeometry(), result.getEnvelope().getGeometry());
        assertEquals(this.obj.getEnvelope().getUnlocode(), result.getEnvelope().getUnlocode());
        assertEquals(this.obj.getEnvelope().getSubscriptionPeriodStart(), result.getEnvelope().getSubscriptionPeriodStart());
        assertEquals(this.obj.getEnvelope().getSubscriptionPeriodEnd(), result.getEnvelope().getSubscriptionPeriodEnd());
        assertEquals(this.obj.getEnvelopeSignature(), result.getEnvelopeSignature());
    }

}