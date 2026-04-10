package org.grad.secomv2.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.grad.secomv2.core.base.EnvelopeSignatureBearer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class GetByLinkResponseObjectTest {
    // Class Variables
    private GetByLinkResponseObject obj;
    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Set up an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Generate a new object
        this.obj = new GetByLinkResponseObject();
        this.obj.setData(new byte[0]);
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        GetByLinkResponseObject result = this.mapper.readValue(jsonString, GetByLinkResponseObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertArrayEquals(this.obj.getData(), result.getData());
    }

}