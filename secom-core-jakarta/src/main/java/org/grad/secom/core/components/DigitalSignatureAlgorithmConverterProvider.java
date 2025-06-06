/*
 * Copyright (c) 2025 GLA Research and Development Directorate
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

import org.grad.secom.core.exceptions.SecomValidationException;
import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.DigitalSignatureAlgorithmEnum;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * The DigitalSignatureAlgorithm Converter Provider.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Provider
public class DigitalSignatureAlgorithmConverterProvider implements ParamConverterProvider {

    // Class Variables
    private final DigitalSignatureAlgorithmConverter converter = new DigitalSignatureAlgorithmConverter();

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
        if (!aClass.equals(DigitalSignatureAlgorithmEnum.class)) return null;
        return (ParamConverter<T>) converter;
    }

    /**
     * The Digital Signature Algorithm Converter Class.
     *
     * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
     */
    public class DigitalSignatureAlgorithmConverter implements ParamConverter<DigitalSignatureAlgorithmEnum> {

        /**
         * Implement the fromString operation.
         *
         * @param value the string value to be converted into an object
         * @return the converted object
         */
        public DigitalSignatureAlgorithmEnum fromString(String value) {
            if (value == null || value.isEmpty()) return null;
            try {
                return DigitalSignatureAlgorithmEnum.valueOf(value.toUpperCase());
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
        public String toString(DigitalSignatureAlgorithmEnum value) {
            if (value == null) return "";
            try {
                return value.getValue().toLowerCase();
            } catch (Exception ex) { // Direct to BAD_REQUEST
                throw new SecomValidationException(ex.getMessage());
            }
        }

    }

}
