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

package org.grad.secomv2.springboot3.config;

import jakarta.servlet.http.HttpServletRequest;
import org.grad.secomv2.core.base.SecomConstants;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * The SECOM Request Logging Configuration Class.
 *
 * This class registers a filter registration bean for the "/api/secom" URL
 * interfaces of SECOM, so that the incoming requests and responses are logged.
 * Note that by default, in the INFO level, only the requests will be logged
 * while on the DEBUG level the responses will also be recorded.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Configuration
public class SecomRequestLoggingFilterConfig {

    /**
     * Registers the secomRequestLogging bean using the built-in functionality
     * of the CommonsRequestLoggingFilter to log the requests. The registered
     * bean will be active for all "/api/secom" URL interfaces.
     *
     * @return the secomRequestLogging filter registration bean
     */
    @Bean(name = "secomV2RequestLogging")
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
        secomRequestLogging.addUrlPatterns("/api/secom2/" + SecomConstants.SECOM_VERSION + "/*");
        return secomRequestLogging;
    }

}
