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

package org.grad.secomv2.core.components;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;

/**
 * The ObjetMapper Provider.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SecomObjectMapperProvider implements ContextResolver<ObjectMapper> {

    // Class Variables
    ObjectMapper objectMapper;

    /**
     * The ObjetMapper Provider Constructor.
     *
     * @param objectMapper the provided object mapper
     */
    public SecomObjectMapperProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Return the acquired object mapper when required.
     *
     * @param type the type of the class to return the object mapper for
     * @return the appropriate object mapper
     */
    @Override
    public ObjectMapper getContext(Class<?> type) {
        return this.objectMapper;
    }
}
