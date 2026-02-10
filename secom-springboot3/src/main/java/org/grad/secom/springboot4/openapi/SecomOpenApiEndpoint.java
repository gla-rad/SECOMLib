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

package org.grad.secom.springboot4.openapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.jaxrs2.Reader;
import org.grad.secom.core.base.SecomConstants;
import org.grad.secom.core.interfaces.GenericSecomInterface;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The SECOM V1 OpenAPI Endpoint
 * <p/>
 * This is the implementation of an endpoint that provides the OpenAPI
 * definition for the Springboot service using the SECOMLib. It
 * automatically identifies all registered controller components and
 * parses them to generate the OpenAPI JSON file which can be served
 * through Swagger.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Component
@Path("/")
public class SecomOpenApiEndpoint {

    /**
     * The Application Context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * The SECOM OpenAPI Info Provider Component.
     */
    @Autowired(required = false)
    private SecomOpenApiInfoProvider secomOpenApiInfoProvider;

    /**
     * GET /v1/openapi.json : returns the OpenAPI JSON file generated
     * automatically for the controller components extending the
     * GenericSecomInterface class.
     *
     * @return the OpenAPIJson
     * @throws JsonProcessingException when the JSON processing fails
     */
    @Path("/" + SecomConstants.SECOM_VERSION + "/openapi.json")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOpenApiV1() throws JsonProcessingException {
        // Get the basic OpenAPI information and create a Reader for scanning
        final Reader reader = new Reader(Optional.ofNullable(secomOpenApiInfoProvider)
                .map(SecomOpenApiInfoProvider::getOpenApiInfo)
                .orElseGet(SecomOpenApiInfoProvider::defaultOpenAPIInfo));

        // Swagger Core Reader will scan these classes for annotations
        final Set<Class<?>> classes = this.applicationContext.getBeansOfType(GenericSecomInterface.class).values()
                .stream()
                .map(AopUtils::getTargetClass)
                .collect(Collectors.toSet());

        // Now parse through the SECOM interfaces and generate the OpenAPI JSON
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return Response.ok(objectMapper.writeValueAsString(reader.read(classes))).build();
    }

}
