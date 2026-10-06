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
public class SecomV2OpenApiConfiguration {

    /**
     * Automatically create a default group for the service interfaces.
     *
     * @return a grouped open api default group
     */
    @Bean
    public GroupedOpenApi defaultApi() {
        return GroupedOpenApi.builder()
                .group("OpenAPI definition")
                .pathsToMatch("/**")
                .pathsToExclude(API_PATH + "/" + SECOM_VERSION + "/**")
                .addOpenApiCustomizer((openAPI) -> {
                    Optional.of(SecomV2OpenApiInfoProvider.class)
                            .map(SecomSpringContext::getBean)
                            .map(SecomV2OpenApiInfoProvider::getOpenApiInfo)
                            .ifPresent(customAPI -> copyOpenApi(customAPI, openAPI));
                })
                .build();
    }

    /**
     * Automatically create a group for the SECOM v2 interfaces
     *
     * @return a grouped open api SECOM group
     */
    @Bean
    public GroupedOpenApi secomApi() {
        return GroupedOpenApi.builder()
                .group("SECOM V2 API")
                .pathsToMatch(API_PATH + "/" + SECOM_VERSION + "/**")
                .addOpenApiCustomizer((openAPI) -> {
                    Optional.of(SecomV2OpenApiInfoProvider.class)
                            .map(SecomSpringContext::getBean)
                            .map(apiProvider -> Optional.of(apiProvider)
                                    .map(SecomV2OpenApiInfoProvider::getSecomOpenApiInfo)
                                    .orElseGet(apiProvider::defaultOpenAPIInfo))
                            .ifPresent(customAPI -> copyOpenApi(customAPI, openAPI));
                })
                .build();
    }

    /**
     * A helper function to copy as many fields as possible from one OpenAPI
     * documentation to another.
     *
     * @param source the source OpenAPI documentations
     * @param dest the destination OpenAPI documentation
     */
    private void copyOpenApi(OpenAPI source, OpenAPI dest) {
        if(source.getInfo() != null) dest.setInfo(source.getInfo());
        if(source.getServers() != null) dest.setServers(source.getServers());
        if(source.getExternalDocs() != null) dest.setExternalDocs(source.getExternalDocs());
        if(source.getTags() != null) dest.getTags().addAll(source.getTags());
        if(source.getSecurity() != null) dest.setSecurity(source.getSecurity());
        if(source.getExtensions() != null) dest.setExtensions(source.getExtensions());
    }

}
