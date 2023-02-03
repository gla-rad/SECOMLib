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

package org.grad.secom.springboot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.grad.secom.core.base.*;
import org.grad.secom.core.components.*;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JAX-RS application
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Configuration
@ApplicationPath("/api/secom/")
public class JaxrsApplication extends Application {

    /**
     * Initialise the SECOM object mapping operation with the Springboot object
     * mapper.
     *
     * @param objectMapper the autowired object mapper
     * @return the object mapper provider
     */
    @Bean
    SecomObjectMapperProvider secomObjectMapperProvider(@Autowired ObjectMapper objectMapper) {
        return new SecomObjectMapperProvider(objectMapper);
    }

    /**
     * Initialise the SECOM exception mapper.
     *
     * @return the SECOM exception mapper bean
     */
    @Bean
    SecomExceptionMapper secomExceptionMapper() {
        return new SecomExceptionMapper();
    }

    /**
     * Initialise the SECOM writer interceptor.
     *
     * @return the SECOM writer interceptor bean
     */
    @Bean
    SecomWriterInterceptor secomWriterInterceptor(@Autowired(required = false) SecomCompressionProvider compressionProvider,
                                                  @Autowired(required = false) SecomEncryptionProvider encryptionProvider,
                                                  @Autowired(required = false) SecomCertificateProvider certificateProvider,
                                                  @Autowired(required = false) SecomSignatureProvider signatureProvider) {
        return new SecomWriterInterceptor(compressionProvider, encryptionProvider, certificateProvider, signatureProvider);
    }

    /**
     * Initialise the SECOM signature filter.
     *
     * @return the SECOM signature filter bean
     */
    @Bean
    SecomSignatureFilter secomSignatureFilter(@Autowired(required = false) SecomCompressionProvider compressionProvider,
                                              @Autowired(required = false) SecomEncryptionProvider encryptionProvider,
                                              @Autowired(required = false) SecomTrustStoreProvider trustStoreProvider,
                                              @Autowired(required = false) SecomSignatureProvider signatureProvider) {
        return new SecomSignatureFilter(compressionProvider, encryptionProvider, trustStoreProvider, signatureProvider);
    }

    /**
     * Initialise the SECOM reader interceptor.
     *
     * @return the SECOM reader interceptor bean
     */
    @Bean
    SecomReaderInterceptor secomReaderInterceptor(@Autowired(required = false) SecomCompressionProvider compressionProvider,
                                                  @Autowired(required = false) SecomEncryptionProvider encryptionProvider) {
        return new SecomReaderInterceptor(compressionProvider, encryptionProvider);
    }

    /**
     * Initialise the ContainerType Converter Provider bean.
     *
     * @return the ContainerType Converter Provider bean
     */
    @Bean
    ContainerTypeConverterProvider containerTypeConverterProvider() {
        return new ContainerTypeConverterProvider();
    }

    /**
     * Initialise the DigitalSignatureAlgorithmEnum Converter Provider bean.
     *
     * @return the DigitalSignatureAlgorithmEnum Converter Provider bean
     */
    @Bean
    DigitalSignatureAlgorithmConverterProvider digitalSignatureAlgorithmConverterProvider() {
        return new DigitalSignatureAlgorithmConverterProvider();
    }

    /**
     * Initialise the LocalDateTime Converter Provider bean.
     *
     * @return the LocalDateTime Converter Provider bean
     */
    @Bean
    LocalDateTimeConverterProvider localDateTimeConverterProvider() {
        return new LocalDateTimeConverterProvider();
    }

    /**
     * Register the required classes to the RESTEasy server.
     *
     * @return the set of classes to be registered.
     */
    @Override
    public Set<Class<?>> getClasses() {
        return Stream.of(OpenApiResource.class, AcceptHeaderOpenApiResource.class).collect(Collectors.toSet());
    }

    /**
     * Register the required singletons to the RESTEasy server.
     *
     * @return the set of singletons to be registered.
     */
    @Override
    public Set<Object> getSingletons() {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        corsFilter.setAllowCredentials(false);
        return Collections.singleton(corsFilter);
    }

}
