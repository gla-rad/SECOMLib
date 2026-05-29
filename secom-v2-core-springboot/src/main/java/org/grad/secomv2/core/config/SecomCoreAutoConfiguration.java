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
package org.grad.secomv2.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.grad.secomv2.core.components.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * AutoconfigurationDo for secom-v2-core-springboot components.
 * Registers beans that live outside the application's component-scan path.
 */
@AutoConfiguration
@Import({
        SecomSignatureAdvice.class,
        SecomReaderInterceptor.class,
        SecomWriterInterceptor.class,
        SecomV2ExceptionMapper.class,
        ContainerTypeToStringConverterProvider.class,
        StringToContainerTypeConverterProvider.class,
        DigitalSignatureEnumToStringConverter.class,
        DigitalSignatureStringToEnumConverter.class,
        InstantToISOConverterProvider.class,
        ISOToInstantConverterProvider.class
        })
public class SecomCoreAutoConfiguration {


}