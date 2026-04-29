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

import tools.jackson.core.JacksonException;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * The ByteArraySerializer Class
 * <p/>
 * In SECOM the data byte arrays should be potentially encrypted and compressed
 * and definitely encoded into Base64. However, the encoding is already handled
 * by the SECOM reader, writer and filter interceptors and therefore allowing
 * that operation to be directly controlled by the library is better that doing
 * it through the JSON object mapper. Therefore, this serializer will just
 * encode byte arrays as a UTF-8 string, assuming the Base64 (or any other
 * specified encoding) is handled by the library.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SecomByteArraySerializer extends ValueSerializer<byte[]> {

    /**
     * Implements the serialization procedure of the serializer.
     *
     * @param value           The input to be serialized
     * @param jsonGenerator   The JSON generator
     * @param serializationContext    The serialization context
     * @return the serialized output
     * @throws JacksonException for any IO exceptions
     */
    @Override
    public void serialize(byte[] value,
                          tools.jackson.core.JsonGenerator jsonGenerator,
                          SerializationContext serializationContext) throws JacksonException {
        jsonGenerator.writeString(Optional.ofNullable(value)
                .map(dt -> new String(dt, StandardCharsets.UTF_8))
                .orElse(""));
    }
}
