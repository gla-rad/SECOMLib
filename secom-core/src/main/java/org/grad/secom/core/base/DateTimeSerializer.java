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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static org.grad.secom.core.base.SecomConstants.SECOM_DATE_TIME_FORMAT;
import static org.grad.secom.core.base.SecomConstants.SECOM_DATE_TIME_FORMATTER;

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
public class DateTimeSerializer extends StdSerializer<LocalDateTime> {

    /**
     * Instantiates a new Byte array serializer.
     */
    protected DateTimeSerializer() {
        this(null);
    }

    /**
     * Instantiates a new Byte array serializer.
     *
     * @param t the byte array class
     */
    protected DateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }

    /**
     * Implements the serialization procedure of the serializer.
     *
     * @param localDateTime         The input to be serialized
     * @param jg                    The JSON generator
     * @param serializerProvider    The serialization provider
     * @return the serialized output
     * @throws IOException for any IO exceptions
     */
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jg, SerializerProvider serializerProvider) throws IOException {
        String input = "";
        if (localDateTime != null) {
            try {
                input = localDateTime.format(SECOM_DATE_TIME_FORMATTER);
            } catch (Exception ex) {
                input = "";
            }
        }
        jg.writeString(input);
    }
}
