package org.grad.secom.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.ReasonEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccessRequestObjectTest {

    // Class Variables
    private EnvelopeAccessObject envelopeAccessObject;
    private AccessRequestObject obj;

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
        this.envelopeAccessObject = new EnvelopeAccessObject();
        envelopeAccessObject.setReason("Test");
        envelopeAccessObject.setReasonEnum(ReasonEnum.REQUESTED_BY_AUTHORITY);
        envelopeAccessObject.setContainerType(ContainerTypeEnum.S100_DataSet);
        envelopeAccessObject.setDataProductType(SECOM_DataProductType.S101);
        envelopeAccessObject.setDataReference(UUID.randomUUID());
        envelopeAccessObject.setProductVersion("productVersion");
        this.obj = new AccessRequestObject();
        this.obj.setEnvelope(envelopeAccessObject);
        this.obj.setEnvelopeSignature("envelopeSignature");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        AccessRequestObject result = this.mapper.readValue(jsonString, AccessRequestObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getEnvelope().getReason(), result.getEnvelope().getReason());
        assertEquals(this.obj.getEnvelope().getReasonEnum(), result.getEnvelope().getReasonEnum());
        assertEquals(this.obj.getEnvelope().getContainerType(), result.getEnvelope().getContainerType());
        assertEquals(this.obj.getEnvelope().getDataProductType(), result.getEnvelope().getDataProductType());
        assertEquals(this.obj.getEnvelope().getDataReference(), result.getEnvelope().getDataReference());
        assertEquals(this.obj.getEnvelope().getProductVersion(), result.getEnvelope().getProductVersion());
        assertEquals(this.obj.getEnvelopeSignature(), result.getEnvelopeSignature());
    }

}