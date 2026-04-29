/*
 * Copyright (c) 2026 GLA Research and Development Directorate
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

package org.grad.secomv2.core.base;

import tools.jackson.core.JsonParser;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

import java.nio.charset.StandardCharsets;

/**
 * The ByteArrayDeserializer Class
 * <p/>
 * In SECOM the data byte arrays should be potentially encrypted and compressed
 * and definitely encoded into Base64. However, the decoding is already handled
 * by the SECOM reader, writer and filter interceptors and therefore allowing
 * that operation to be directly controlled by the library is better that doing
 * it through the JSON object mapper. Therefore, this de-serializer will just
 * decode byte arrays from a UTF-8 string, assuming the Base64 (or any other
 * specified encoding) is handled by the library.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SecomByteArrayDeserializer extends ValueDeserializer<byte[]> {

    /**
     * Implements the de-serialization procedure of the de-serializer.
     *
     * @param jsonParser                The JSON Parser
     * @param deserializationContext    The deserialization context
     * @return the deserialized output
     * @throws JacksonException for any IO exceptions
     */
    @Override
    public byte[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws JacksonException {
        final String value = deserializationContext.readValue(jsonParser, String.class);
        if(value == null) {
            return null;
        }
        return value.getBytes(StandardCharsets.UTF_8);
    }

}
