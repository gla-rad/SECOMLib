/*
 * Copyright (c) 2025 GLA Research and Development Directorate
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.grad.secomv2.springboot4.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.ListenerBootstrap;
import org.jboss.resteasy.plugins.spring.SpringBeanProcessor;
import org.jboss.resteasy.spi.Dispatcher;
import org.jboss.resteasy.spi.Registry;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.springboot.ResteasyApplicationBuilder;
import org.jboss.resteasy.springboot.ResteasyBeanProcessorTomcat;
import org.jboss.resteasy.springboot.common.DeploymentCustomizer;
import org.jboss.resteasy.springboot.common.ResteasyBeanProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.webmvc.autoconfigure.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration(
        after = {WebMvcAutoConfiguration.class}
)
@EnableConfigurationProperties
public class ResteasyAutoConfiguration {
    private static Logger logger = LoggerFactory.getLogger(org.jboss.resteasy.springboot.ResteasyAutoConfiguration.class);

    @Bean
    public ServletContextListener resteasyBootstrapListener(@Qualifier("resteasySpringBeanProcessor") final SpringBeanProcessor resteasySpringBeanProcessor) {
        ServletContextListener servletContextListener = new ServletContextListener() {
            protected ResteasyDeployment deployment;

            public void contextInitialized(ServletContextEvent sce) {
                ServletContext servletContext = sce.getServletContext();
                this.deployment = (new ListenerBootstrap(servletContext)).createDeployment();
                DeploymentCustomizer.customizeRestEasyDeployment(resteasySpringBeanProcessor, this.deployment, this.deployment.isAsyncJobServiceEnabled());
                this.deployment.start();
                servletContext.setAttribute(ResteasyProviderFactory.class.getName(), this.deployment.getProviderFactory());
                servletContext.setAttribute(Dispatcher.class.getName(), this.deployment.getDispatcher());
                servletContext.setAttribute(Registry.class.getName(), this.deployment.getRegistry());
            }

            public void contextDestroyed(ServletContextEvent sce) {
                if (this.deployment != null) {
                    this.deployment.stop();
                }

            }
        };
        logger.debug("ServletContextListener has been created");
        return servletContextListener;
    }

    @Bean(
            name = {"JaxrsApplicationServletBuilder"}
    )
    public ResteasyApplicationBuilder resteasyApplicationBuilder() {
        return new ResteasyApplicationBuilder();
    }

    @Bean
    public static ResteasyBeanProcessorTomcat resteasyBeanProcessorTomcat() {
        return new ResteasyBeanProcessorTomcat();
    }

    @Bean({"resteasySpringBeanProcessor"})
    public static SpringBeanProcessor resteasySpringBeanProcessor() {
        return ResteasyBeanProcessorFactory.resteasySpringBeanProcessor();
    }
}
