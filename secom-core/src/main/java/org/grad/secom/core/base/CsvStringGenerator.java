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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.grad.secom.core.models.enums.SECOM_Enum;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The SECOM Envelope Interface.
 *
 * Envelopes in SECOM have a specific way of being serialized top generate the
 * signatures. This interface contains all the required functionality shared
 * between these envelope classes.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface CsvStringGenerator {

    /**
     * This function provides SECOM envelope objects with the ability to convert
     * the internal attributes as a single string representation, as CSV. The
     * CSV separator is defined in SECOM as a dot (.) and the output string can
     * be used to generate the envelope's signature.
     *
     * @return the envelope's CSV string representation
     */
    @JsonIgnore
    default String getCsvString() {
        return Arrays.stream(this.getAttributeArray())
                .map(this::attributeConversion)
                .collect(Collectors.joining("."));
    }

    /**
     * This method should be implemented by all envelop objects to allow the
     * generation of the signature CSV attribute array
     *
     * @return the generated signature CSV attribute array
     */
    @JsonIgnore
    Object[] getAttributeArray();

    /**
     * The general attribute conversion function as described in the SECOM
     * standard, section 7.3.4.
     *
     * @param attribute     The attribute to be converted onto a string
     * @return the converted string of the attribute value
     */
    @JsonIgnore
    default String attributeConversion(Object attribute) {
        // Check for nulls and then use attribute type
        if(attribute == null) {
            return "";
        }
        else if(attribute instanceof CsvStringGenerator) {
            return Optional.of(attribute)
                    .map(CsvStringGenerator.class::cast)
                    .map(CsvStringGenerator::getCsvString)
                    .orElse("");
        }
        else if(attribute instanceof LocalDateTime) {
            return Optional.of(attribute)
                    .map(LocalDateTime.class::cast)
                    .map(ldt -> ldt.toEpochSecond(ZoneOffset.UTC))
                    .map(String::valueOf)
                    .orElse("");
        } else if(attribute instanceof Boolean) {
            return Optional.of(attribute)
                    .map(Boolean.class::cast)
                    .map(Object::toString)
                    .orElse("");
        } else if(attribute instanceof UUID) {
            return Optional.of(attribute)
                    .map(UUID.class::cast)
                    .map(UUID::toString)
                    .orElse("");
        } else if(attribute instanceof SECOM_Enum) {
            return Optional.of(attribute)
                    .map(SECOM_Enum.class::cast)
                    .map(SECOM_Enum::asString)
                    .orElse("");
        } else if(attribute instanceof byte[]) {
            return Optional.of(attribute)
                    .map(byte[].class::cast)
                    .map(src -> Base64.getEncoder().encode(src))
                    .map(out -> new String(out, StandardCharsets.UTF_8))
                    .orElse("");
        } else if(attribute instanceof Number) {
            return Optional.of(attribute)
                    .map(Number.class::cast)
                    .map(Number::toString)
                    .orElse("");
        } else {
            return attribute.toString();
        }
    }

}
