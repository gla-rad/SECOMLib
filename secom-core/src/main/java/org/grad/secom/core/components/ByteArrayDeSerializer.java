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

package org.grad.secom.core.components;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * The ByteArrayDeSerializer Class
 * <p/>
 * In SECOM the data byte arrays should be potentially encrypted and compressed
 * and definitely encoded into Base64. However, the encoding is already handled
 * by the SECOM reader, writer and filter interceptors and therefore allowing
 * that operation to be directly controlled by the library is better that doing
 * it through the JSON object mapper. Therefore, this de-serializer will just
 * decode byte arrays from a UTF-8 string, assuming the Base64 (or any other
 * specified encoding) is handled by the library.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class ByteArrayDeSerializer extends StdDeserializer<byte[]> {

    /**
     * Instantiates a new Byte array de serializer.
     */
    protected ByteArrayDeSerializer() {
        this(null);
    }

    /**
     * Instantiates a new Byte array de serializer.
     *
     * @param t the byte array class
     */
    protected ByteArrayDeSerializer(Class<byte[]> t) {
        super(t);
    }

    /**
     * Implements the de-serialization procedure of the de-serializer.
     *
     * @param jp    The JSON Parser
     * @param ctxt  The deserialization context
     * @return the deserialized output
     * @throws IOException for any IO exceptions
     * @throws JsonProcessingException for any JSON processign exceptions
     */
    @Override
    public byte[] deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final String value = jp.getCodec().readValue(jp, String.class);
        if(value == null) {
            return null;
        }
        return value.getBytes(StandardCharsets.UTF_8);
    }

}
