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
package org.grad.secomv2.springboot4.openapi;


import io.swagger.v3.oas.models.OpenAPI;
import org.grad.secomv2.springboot4.components.SecomSpringContext;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

import static org.grad.secomv2.core.base.SecomConstants.API_PATH;
import static org.grad.secomv2.core.base.SecomConstants.SECOM_VERSION;

/**
 * Autoconfiguration to provide OpenAPI / Swagger-UI info and descriptions
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@Configuration
public class SecomV2OpenAPIConfiguration {

    /**
     * Automatically create a group for the SECOM v2 interfaces
     *
     * @return a grouped open api
     */
    @Bean
    public GroupedOpenApi secomApi() {
        return GroupedOpenApi.builder()
                .group("Secom V2 API")
                .pathsToMatch(API_PATH + "/" + SECOM_VERSION + "/**")
                .addOpenApiCustomizer(new SecomOpenApiCustomizer())
                .build();
    }

    static class SecomOpenApiCustomizer implements OpenApiCustomizer {

        /**
         * Automatically customise the OpenAPI document with the values provided by
         * SecomV2OpenApiInfoProvider
         *
         * @param openAPI the open api
         */
        @Override
        public void customise(OpenAPI openAPI) {
            OpenAPI customOpenAPI = Optional.ofNullable(SecomSpringContext.getBean(SecomV2OpenApiInfoProvider.class))
                    .map(SecomV2OpenApiInfoProvider::getOpenApiInfo)
                    .orElse(null);

            if(customOpenAPI != null) {
                if(customOpenAPI.getInfo() != null) openAPI.setInfo(customOpenAPI.getInfo());
                if(customOpenAPI.getServers() != null) openAPI.setServers(customOpenAPI.getServers());
                if(customOpenAPI.getExternalDocs() != null) openAPI.setExternalDocs(customOpenAPI.getExternalDocs());
                if(customOpenAPI.getTags() != null) openAPI.getTags().addAll(customOpenAPI.getTags());
                if(customOpenAPI.getSecurity() != null) openAPI.setSecurity(customOpenAPI.getSecurity());
                if(customOpenAPI.getExtensions() != null) openAPI.setExtensions(customOpenAPI.getExtensions());
            }

        }
    }

}
