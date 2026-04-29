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
package org.grad.secomv2.core.components;

import org.grad.secomv2.core.exceptions.SecomValidationException;

import org.jspecify.annotations.NullMarked;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.grad.secomv2.core.models.enums.DigitalSignatureAlgorithmEnum;


/**
 * The Digital Signature enum to string converter
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@Component
public class DigitalSignatureEnumToStringConverter implements Converter<DigitalSignatureAlgorithmEnum, String> {

    /**
     * Implement the converter function to DigitalSignatureAlgorithmEnum to string
     *
     * @param source the DigitalSignatureAlgorithmEnum to convert
     * @return the string representation of the enum
     */
    @Override
    @NullMarked
    public String convert(DigitalSignatureAlgorithmEnum source) {
        try {
            return source.getValue().toLowerCase();
        } catch (Exception ex) { // Direct to BAD_REQUEST
            throw new SecomValidationException(ex.getMessage());
        }
    }
}
