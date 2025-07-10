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

package org.grad.secom.springboot3.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.grad.secom.core.base.*;
import org.grad.secom.core.components.*;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JAX-RS application
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Configuration
@ApplicationPath("/api/secom/")
public class SecomV1JaxrsApplication extends Application implements ApplicationContextAware {

    /**
     * The Springboot Application Context.
     */
    private ApplicationContext applicationContext;

    /**
     * Autowiring the Springboot Object Mapper
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Initialise the SECOM exception mapper.
     *
     * @return the SECOM exception mapper bean
     */
    @Bean("secomV1ExceptionMapper")
    SecomExceptionMapper secomExceptionMapper() {
        return new SecomExceptionMapper(this);
    }

    /**
     * Initialise the SECOM writer interceptor.
     *
     * @return the SECOM writer interceptor bean
     */
    @Bean("secomV1WriterInterceptor")
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
    @Bean("secomV1SignatureFilter")
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
    @Bean("secomV1ReaderInterceptor")
    SecomReaderInterceptor secomReaderInterceptor(@Autowired(required = false) SecomCompressionProvider compressionProvider,
                                                  @Autowired(required = false) SecomEncryptionProvider encryptionProvider) {
        return new SecomReaderInterceptor(compressionProvider, encryptionProvider);
    }

    /**
     * Register the required classes to the RESTEasy server.
     *
     * @return the set of classes to be registered.
     */
    @Override
    public Set<Class<?>> getClasses() {
        return Set.of(
                OpenApiResource.class,
                AcceptHeaderOpenApiResource.class,
                /*
                 * Add the JaxRS Application Providers.
                 */
                InstantToS100ConverterProvider.class,
                ContainerTypeConverterProvider.class,
                DigitalSignatureAlgorithmConverterProvider.class
        );
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
        return Set.of(
                corsFilter,
                /*
                 * Add the JaxRS Application Object Mapper.
                 */
                new SecomObjectMapperProvider(Optional.ofNullable(this.objectMapper)
                        .orElse(new ObjectMapper()))
        );
    }

    /**
     * Returns the properties of the JaxRS application. This is actually
     * also used internally to provide access to all registered RestEasy
     * Exception mappers.
     *
     * @return the set of properties to be registered
     */
    @Override
    public Map<String, Object> getProperties() {
        return this.applicationContext.getBeansOfType(ExceptionMapper.class)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Allows the retrieval of the Springboot application context.
     *
     * @return the Springboot application context
     */
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    /**
     * Implements the setApplicationContext() function of the Springboot
     * ApplicationContextAware interface so that the JaxRS application can
     * have access to the application context.
     *
     * @param applicationContext the Springboot application context
     * @throws BeansException if an exception on the bean generation is thrown
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
