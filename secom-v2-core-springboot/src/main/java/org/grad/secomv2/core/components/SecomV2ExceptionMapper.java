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

package org.grad.secomv2.core.components;

import org.apache.commons.lang3.exception.ExceptionUtils;

import jakarta.servlet.http.HttpServletRequest;
import org.grad.secomv2.core.base.SecomConstants;
import org.grad.secomv2.core.interfaces.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;
import java.util.logging.Logger;

import static org.grad.secomv2.core.interfaces.AccessNotificationServiceInterface.ACCESS_NOTIFICATION_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.AccessServiceInterface.ACCESS_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.AcknowledgementServiceInterface.ACKNOWLEDGMENT_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.CapabilityServiceInterface.CAPABILITY_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.EncryptionKeyRequestServiceInterface.ENCRYPTION_KEY_REQUEST_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.PostGetByLinkServiceInterface.POST_GET_BY_LINK_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.PostGetServiceInterface.POST_GET_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.RetrieveResultServiceInterface.RETRIEVE_RESULT_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.SearchServiceServiceInterface.SEARCH_SERVICE_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.EncryptionKeyServiceInterface.ENCRYPTION_KEY_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.GetByLinkServiceInterface.GET_BY_LINK_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.GetServiceInterface.GET_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.GetSummaryServiceInterface.GET_SUMMARY_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.PostGetSummaryServiceInterface.POST_GET_SUMMARY_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.PingServiceInterface.PING_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.SubscriptionNotificationServiceInterface.SUBSCRIPTION_NOTIFICATION_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.SubscriptionServiceInterface.SUBSCRIPTION_INTERFACE_PATH;
import static org.grad.secomv2.core.interfaces.GetPublicKeyServiceInterface.GET_PUBLIC_KEY_INTERFACE_PATH;


/**
 * The SECOM Exception Manager Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@RestControllerAdvice
public class SecomV2ExceptionMapper {


    /**
     * Generate the response based on the exceptions thrown by the respective
     * SECOM endpoint called. This can be extracted by the request context.
     *
     * @param ex the exception that was thrown
     * @return the response to be returned
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request) throws Exception {
        // Get the path and method for the request
        String path = request.getRequestURI();
        String method = request.getMethod();

        // If exception is not for this version, throw it again for the other exception handler
        if(!path.startsWith("/" + SecomConstants.SECOM_VERSION)) {
            throw ex;
        }

        //First log the message
        final Logger secomLogger = Logger.getLogger(Optional.of(ex)
                        .map(Exception::getCause)
                        .map(Throwable::toString)
                        .orElse("SecomExceptionMapper.class"));
        secomLogger.severe(Optional.of(ex)
                        .map(Exception::getMessage)
                        .orElse("Unknown error..."));
        secomLogger.fine(Optional.of(ex)
                .map(ExceptionUtils::getStackTrace)
                .orElse("Unknown stacktrace..."));

        // Then handle
        switch(path) {
            case ACCESS_INTERFACE_PATH:
                return AccessServiceInterface.handleAccessInterfaceExceptions(ex, request);
            case ACCESS_NOTIFICATION_INTERFACE_PATH:
                return AccessNotificationServiceInterface.handleAccessNotificationInterfaceExceptions(ex, request);
            case ACKNOWLEDGMENT_INTERFACE_PATH:
                return AcknowledgementServiceInterface.handleAcknowledgementInterfaceExceptions(ex, request);
            case CAPABILITY_INTERFACE_PATH:
                return CapabilityServiceInterface.handleCapabilityInterfaceExceptions(ex, request);
            case SEARCH_SERVICE_INTERFACE_PATH:
                return SearchServiceServiceInterface.handleSearchServiceInterfaceExceptions(ex, request);
            case ENCRYPTION_KEY_INTERFACE_PATH:
                return EncryptionKeyServiceInterface.handleEncryptionInterfaceExceptions(ex, request);
            case ENCRYPTION_KEY_REQUEST_INTERFACE_PATH:
                return EncryptionKeyRequestServiceInterface.handleEncryptionKeyRequestInterfaceExceptions(ex, request);
            case GET_BY_LINK_INTERFACE_PATH: // Also for upload
                if("GET".equals(method)) {
                    return GetByLinkServiceInterface.handleGetByLinkInterfaceExceptions(ex, request);
                } else if("POST".equals(method)) {
                    return UploadLinkServiceInterface.handleUploadLinkInterfaceExceptions(ex, request);
                }
            case POST_GET_BY_LINK_INTERFACE_PATH:
                return PostGetByLinkServiceInterface.handleGetByLinkInterfaceExceptions(ex, request);
            case GET_INTERFACE_PATH: // Also for upload
                if("GET".equals(method)) {
                    return GetServiceInterface.handleGetInterfaceExceptions(ex, request);
                } else if("POST".equals(method)) {
                    return UploadServiceInterface.handleUploadInterfaceExceptions(ex, request);
                }
            case POST_GET_INTERFACE_PATH:
                return PostGetServiceInterface.handleGerInterfaceException(ex, request);
            case GET_SUMMARY_INTERFACE_PATH:
                return GetSummaryServiceInterface.handleGetSummaryInterfaceExceptions(ex, request);
            case POST_GET_SUMMARY_INTERFACE_PATH:
                return PostGetSummaryServiceInterface.handleGetSummaryInterfaceExceptions(ex, request);
            case PING_INTERFACE_PATH:
                return PingServiceInterface.handlePingInterfaceExceptions(ex, request);
            case SUBSCRIPTION_INTERFACE_PATH: // Also for remove subscription
                if("POST".equals(method)) {
                    return SubscriptionServiceInterface.handleSubscriptionInterfaceExceptions(ex, request);
                } else if("DELETE".equals(method)) {
                    return RemoveSubscriptionServiceInterface.handleRemoveSubscriptionInterfaceExceptions(ex, request);
                }
            case SUBSCRIPTION_NOTIFICATION_INTERFACE_PATH:
                return SubscriptionNotificationServiceInterface.handleSubscriptionNotificationInterfaceExceptions(ex, request);
            case RETRIEVE_RESULT_INTERFACE_PATH:
                return RetrieveResultServiceInterface.handleRetrieveResultInterfaceExceptions(ex, request);
            case GET_PUBLIC_KEY_INTERFACE_PATH:
                if("GET".equals(method)) {
                    return GetPublicKeyServiceInterface.handleGetPublicKeyExceptions(ex, request);
                } else if("POST".equals(method)) {
                    return PostPublicKeyServiceInterface.handlePostPublicKeyInterfaceExceptions(ex, request);
                }
            default:
                //Nothing to do, continue with the generic rules
        }


        // For everything else, just return an internal server error
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error");
    }

}
