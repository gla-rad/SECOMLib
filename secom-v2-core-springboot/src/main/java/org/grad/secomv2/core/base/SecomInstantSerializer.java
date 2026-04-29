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

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

import static org.grad.secomv2.core.base.SecomConstants.SECOM_DATE_TIME_FORMATTER;

/**
 * The DateTimeSerializer Class
 * <p/>
 * In SECOM the date-time format is not the frequently used ISO. According to
 * the standard A DateTime is a combination of a date and a time type.
 * Character encoding of a DateTime shall follow the example:
 * EXAMPLE: 19850412T101530
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SecomInstantSerializer extends ValueSerializer<Instant> {

    /**
     * Implements the serialization procedure of the serializer.
     *
     * @param value           The input to be serialized
     * @param jsonGenerator   The JSON generator
     * @param serializationContext    The serialization context
     * @throws JacksonException for any IO exceptions
     */
    @Override
    public void serialize(Instant value,
                          JsonGenerator jsonGenerator,
                          SerializationContext serializationContext) throws JacksonException {
        jsonGenerator.writeString(Optional.ofNullable(value)
                .map(dt -> dt.atZone(ZoneId.systemDefault()))
                .map(SECOM_DATE_TIME_FORMATTER::format)
                .orElse(""));
    }

}
