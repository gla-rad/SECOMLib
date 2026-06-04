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
package org.grad.secomv2.springboot4.config;

import org.grad.secomv2.springboot4.components.SecomConfigProperties;
import org.grad.secomv2.springboot4.components.SecomSpringContext;
import org.grad.secomv2.springboot4.openapi.SecomV2OpenAPIConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * Autoconfiguration for secom-v2-springboot4 components.
 * Registers beans that live outside the application's component-scan path.
 */
@AutoConfiguration
@EnableConfigurationProperties(SecomConfigProperties.class)
@Import({
        SecomSpringContext.class,
        SecomV2RequestLoggingFilterConfig.class,
        SecomV2OpenAPIConfiguration.class
})
public class SecomV2AutoConfiguration {

}