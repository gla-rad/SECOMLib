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

import org.grad.secom.core.base.SecomV1Param;
import org.grad.secom.core.exceptions.SecomValidationException;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.grad.secom.core.base.SecomConstants.SECOM_DATE_TIME_FORMATTER;

/**
 * The Instant Format Converter Provider.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class InstantToS100ConverterProvider implements ParamConverterProvider {

    // Class Variables
    private final InstantConverter converter = new InstantConverter();

    /**
     * Implement the converter provision function.
     *
     * @param aClass        the class to be converted
     * @param type          the type of the object
     * @param annotations   the list of annotations of the object
     * @return the identified converter
     * @param <T> the class of the converter input
     */
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {
        final Set<Class<?>> annotationClasses = Stream.of(annotations).map(Annotation::annotationType).collect(Collectors.toSet());
        if (!aClass.equals(Instant.class) || !annotationClasses.contains(SecomV1Param.class)) return null;
        return (ParamConverter<T>) converter;
    }

    /**
     * The Instant Converter Class.
     *
     * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
     */
    public static class InstantConverter implements ParamConverter<Instant> {

        /**
         * Implement the fromString operation.
         *
         * @param value the string value to be converted into an object
         * @return the converted object
         */
        public Instant fromString(String value) {
            if (value == null || value.isEmpty()) return null;
            try {
                return Instant.from(SECOM_DATE_TIME_FORMATTER.parse(value));
            } catch (Exception ex) { // Direct to BAD_REQUEST
                throw new SecomValidationException(ex.getMessage());
            }
        }

        /**
         * Implement the toString operation.
         *
         * @param value the object to be converted into a string
         * @return the converted string
         */
        public String toString(Instant value) {
            if (value == null) return "";
            try {
                return SECOM_DATE_TIME_FORMATTER.format(value);
            } catch (Exception ex) { // Direct to BAD_REQUEST
                throw new SecomValidationException(ex.getMessage());
            }
        }

    }

}
