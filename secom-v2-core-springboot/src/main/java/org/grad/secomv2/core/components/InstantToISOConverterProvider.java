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

import org.jspecify.annotations.NullMarked;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.grad.secomv2.core.exceptions.SecomValidationException;

import java.time.*;

import static org.grad.secomv2.core.base.SecomConstants.SECOM_DATE_TIME_FORMATTER;

/**
 * The Instant Converter Provider.
 *
 * @author Lawrence Hughes (email: lawrence.hughes@gla-rad.org)
 */
@Component
public class InstantToISOConverterProvider implements Converter<String, Instant> {


    /**
     * Implement the converter function
     *
     * @param source the String representation of the Instant
     * @return the Instant representation of the date
     */
    @Override
    @NullMarked
    public Instant convert(String source) {
        try {
            return Instant.from(SECOM_DATE_TIME_FORMATTER.parse(source));
        } catch (Exception ex) { // Direct to BAD_REQUEST
            throw new SecomValidationException(ex.getMessage());
        }
    }

}
