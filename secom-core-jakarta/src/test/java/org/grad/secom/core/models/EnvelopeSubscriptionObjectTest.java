package org.grad.secom.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopeSubscriptionObjectTest {

    // Class Variables
    private EnvelopeSubscriptionObject obj;

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
        this.obj = new EnvelopeSubscriptionObject();
        this.obj.setContainerType(ContainerTypeEnum.S100_DataSet);
        this.obj.setDataProductType(SECOM_DataProductType.S101);
        this.obj.setDataReference(UUID.randomUUID());
        this.obj.setProductVersion("version");
        this.obj.setGeometry("geometry");
        this.obj.setUnlocode("unlocode");
        this.obj.setSubscriptionPeriodStart(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        this.obj.setSubscriptionPeriodEnd(Instant.now().truncatedTo(ChronoUnit.SECONDS));
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EnvelopeSubscriptionObject result = this.mapper.readValue(jsonString, EnvelopeSubscriptionObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getContainerType(), result.getContainerType());
        assertEquals(this.obj.getDataProductType(), result.getDataProductType());
        assertEquals(this.obj.getDataReference(), result.getDataReference());
        assertEquals(this.obj.getProductVersion(), result.getProductVersion());
        assertEquals(this.obj.getGeometry(), result.getGeometry());
        assertEquals(this.obj.getUnlocode(), result.getUnlocode());
        assertEquals(this.obj.getSubscriptionPeriodStart(), result.getSubscriptionPeriodStart());
        assertEquals(this.obj.getSubscriptionPeriodEnd(), result.getSubscriptionPeriodEnd());
    }

}