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

package org.grad.secom.core.base;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class InstantDeserializerTest {

    // Test Parameters
    InstantDeserializer instantDeserializer;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        this.instantDeserializer = new InstantDeserializer();
    }

    /**
     * make sure we can correctly deserialize the incoming SECOM-compliant
     * formatted dates.
     */
    @Test
    void testDeserializeDate() throws IOException {
        // Make some mocks to test easily
        ObjectCodec objectCodecMock = mock(ObjectCodec.class);
        doReturn("20010101T121314Z").when(objectCodecMock).readValue(any(), eq(String.class));
        JsonParser jsonParserMock = mock(JsonParser.class);
        doReturn(objectCodecMock).when(jsonParserMock).getCodec();
        DeserializationContext deserializationContextMock = mock(DeserializationContext.class);

        // And deserialize
        Instant result = this.instantDeserializer.deserialize(jsonParserMock, deserializationContextMock);

        // Make sure the result seems correct
        assertEquals(Instant.parse("2001-01-01T12:13:14.00Z"), result);
    }

    /**
     * make sure we can correctly deserialize the incoming SECOM-compliant
     * formatted dates with offsets.
     */
    @Test
    void testDeserializeDateWithOffset() throws IOException {
        // Make some mocks to test easily
        ObjectCodec objectCodecMock = mock(ObjectCodec.class);
        doReturn("20010101T121314+0100").when(objectCodecMock).readValue(any(), eq(String.class));
        JsonParser jsonParserMock = mock(JsonParser.class);
        doReturn(objectCodecMock).when(jsonParserMock).getCodec();
        DeserializationContext deserializationContextMock = mock(DeserializationContext.class);

        // And deserialize
        Instant result = this.instantDeserializer.deserialize(jsonParserMock, deserializationContextMock);

        // Make sure the result seems correct
        assertEquals(Instant.parse("2001-01-01T12:13:14.00+01:00"), result);
    }

}