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
import org.grad.secomv2.core.models.enums.ContainerTypeEnum;
import org.jspecify.annotations.NullMarked;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


/**
 * The ContainerTypeEnum converter.
 *
 * @author Lawrence Hughes (email:lawrence.hughes@gla-rad.org)
 */
@Component
public class StringToContainerTypeConverterProvider implements Converter<String, ContainerTypeEnum> {


    /**
     * Implement the converter function to convert the string value to the enum
     *
     * @param source a string containing the integer value
     * @return the ContainerTypeEnum constant
     */
    @Override
    @NullMarked
    public ContainerTypeEnum convert(String source) {
        try {
            return ContainerTypeEnum.fromValue(Integer.parseInt(source));
        } catch (Exception ex) { // Direct to BAD_REQUEST
            throw new SecomValidationException(ex.getMessage());
        }
    }

}
