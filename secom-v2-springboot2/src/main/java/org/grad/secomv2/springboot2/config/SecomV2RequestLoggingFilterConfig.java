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

package org.grad.secomv2.springboot2.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * The SECOM Request Logging Filter Configuration Class.
 *
 * This class registered a filter that logs all the incoming requests to the
 * service's SECOM interfaces. This allows traceability for the service
 * operations and will link tha incoming requests with the data provided based
 * on the request timestamps.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Configuration
public class SecomV2RequestLoggingFilterConfig {

    /**
     * Registers the secomRequestLogging bean using the built-in functionality
     * of the CommonsRequestLoggingFilter to log the requests. The registered
     * bean will be active for all "/api/secom" URL interfaces.
     *
     * @return the secomRequestLogging filter registration bean
     */
    @Bean(name = "secomRequestLogging")
    public FilterRegistrationBean<CommonsRequestLoggingFilter> secomRequestLogging() {
        // Setup a commons request logging filter
        final CommonsRequestLoggingFilter secomRequestLoggingFilter = new CommonsRequestLoggingFilter() {
            @Override
            public boolean shouldLog(HttpServletRequest request) {
                return this.logger.isInfoEnabled();
            }
            @Override
            public void beforeRequest(HttpServletRequest request, String message) {
                this.logger.info(message);
            }
            @Override
            public void afterRequest(HttpServletRequest request, String message) {
                this.logger.debug(message);
            }
        };
        secomRequestLoggingFilter.setIncludeClientInfo(true);
        secomRequestLoggingFilter.setIncludeHeaders(true);
        secomRequestLoggingFilter.setIncludeQueryString(true);
        secomRequestLoggingFilter.setIncludePayload(true);
        secomRequestLoggingFilter.setMaxPayloadLength(4096);
        secomRequestLoggingFilter.setBeforeMessagePrefix("SECOM REQUEST DATA: ");
        secomRequestLoggingFilter.setAfterMessagePrefix("SECOM REQUEST DATA AFTER PROCESSING: ");

        // Now register the bean just for the SECOM interfaces
        final FilterRegistrationBean<CommonsRequestLoggingFilter> secomRequestLogging = new FilterRegistrationBean<>();
        secomRequestLogging.setFilter(secomRequestLoggingFilter);
        secomRequestLogging.addUrlPatterns("/api/secom/*");
        return secomRequestLogging;
    }
    
}
