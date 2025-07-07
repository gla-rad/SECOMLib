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

package org.grad.secomv2.springboot3.openapi;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.servers.Server;

import java.util.Arrays;

/**
 * The SECOM OpenAPI information Provider Interface
 * <p/>
 * A helper interface to provide the basic information required to the
 * generation of the OpenAPI documentation in the implementing service.
 *
 * @author Nikolaos.Vastardis@gla-rad.org
 */
public interface SecomV2OpenApiInfoProvider {

    /**
     * Returns the OpenAPI definition information used for the service
     * documentation.
     *
     * @return the OpenAPI definition.
     */
    OpenAPI getOpenApiInfo();

    /**
     * Returns the default OpenAPI definition information used for the service
     * documentation.
     *
     * @return the default OpenAPI definition.
     */
    static OpenAPI defaultOpenAPIInfo() {
        return new OpenAPI().schema("secom-v2", new Schema<>().$schema("openapi.json"))
                .info(new Info().title("SECOM v2.0 Interfaces")
                        .description("The SECOM v2.0 Interfaces of the Service")
                        .termsOfService("")
                        .version("v0.0.1")
                        .contact(new Contact().email("name@test.org"))
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(Arrays.asList(new Server[]{
                        new Server().url("http://localhost:8080/api/secom2")
                }))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

}
