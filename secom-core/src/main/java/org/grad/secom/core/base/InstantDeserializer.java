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
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

import static java.util.function.Predicate.not;
import static org.grad.secom.core.base.SecomConstants.SECOM_DATE_TIME_FORMATTER;

/**
 * The DateTimeDeSerializer Class
 * <p/>
 * In SECOM the date-time format is not the frequently used ISO. According to
 * the standard A DateTime is a combination of a date and a time type.
 * Character encoding of a DateTime shall follow the example:
 * EXAMPLE: 19850412T101530
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class InstantDeserializer extends StdDeserializer<Instant> {

    /**
     * Instantiates a new Byte array de serializer.
     */
    protected InstantDeserializer() {
        this(null);
    }

    /**
     * Instantiates a new Byte array de serializer.
     *
     * @param t the byte array class
     */
    protected InstantDeserializer(Class<Instant> t) {
        super(t);
    }

    /**
     * Implements the de-serialization procedure of the de-serializer.
     *
     * @param jp        The JSON Parser
     * @param context   The deserialization context
     * @return the deserialized output
     * @throws IOException for any IO exceptions
     */
    @Override
    public Instant deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        final String value = jp.getCodec().readValue(jp, String.class);
        return Optional.ofNullable(value)
                .filter(not(String::isBlank))
                .map(SECOM_DATE_TIME_FORMATTER::parse)
                .map(Instant::from)
                .orElse(null);
    }
}
