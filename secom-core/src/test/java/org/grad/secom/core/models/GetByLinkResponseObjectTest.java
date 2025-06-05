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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the Get By Link Response Object
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
public class GetByLinkResponseObjectTest {

    private GetByLinkResponseObject obj;
    private ObjectMapper mapper;

    /**
     * Set up the tests
     */
    @BeforeEach
    void setUp() {
        // Set up an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());

        obj = new GetByLinkResponseObject();
        obj.setData(Base64.getDecoder().decode("data"));
    }

    /**
     * Test the Public Key Object can be converted to json and back
     *
     * @throws JsonProcessingException
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        GetByLinkResponseObject result = this.mapper.readValue(jsonString, GetByLinkResponseObject.class);

        assertNotNull(result);
        assertArrayEquals(obj.getData(), result.getData());

    }

}
