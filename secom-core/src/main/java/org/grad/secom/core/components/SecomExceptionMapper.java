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

package org.grad.secom.core.components;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.grad.secom.core.interfaces.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import static org.grad.secom.core.interfaces.AccessNotificationServiceInterface.ACCESS_NOTIFICATION_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.AccessServiceInterface.ACCESS_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.AcknowledgementServiceInterface.ACKNOWLEDGMENT_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.CapabilityServiceInterface.CAPABILITY_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.GetPublicKeyServiceInterface.GET_PUBLIC_KEY_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.SearchServiceServiceInterface.SEARCH_SERVICE_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.EncryptionKeyNotifyServiceInterface.ENCRYPTION_KEY_NOTIFY_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.EncryptionKeyServiceInterface.ENCRYPTION_KEY_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.GetByLinkServiceInterface.GET_BY_LINK_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.GetServiceInterface.GET_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.GetSummaryServiceInterface.GET_SUMMARY_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.PingServiceInterface.PING_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.SubscriptionNotificationServiceInterface.SUBSCRIPTION_NOTIFICATION_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.SubscriptionServiceInterface.SUBSCRIPTION_INTERFACE_PATH;

/**
 * The SECOM Exception Manager Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Provider
public class SecomExceptionMapper implements ExceptionMapper<Exception> {

    /**
     * The Request Context.
     */
    @Context
    private HttpServletRequest request;

    /**
     * The Request Header.
     */
    @Context
    private HttpHeaders headers;

    /**
     * Generate the response based on the exceptions thrown by the respective
     * SECOM endpoint called. This can be extracted by the request context.
     *
     * @param ex the exception that was thrownn
     * @return the response to be returned
     */
    @Override
    public Response toResponse(Exception ex) {
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
        if(Optional.ofNullable(this.request).map(HttpServletRequest::getPathInfo).isPresent()) {
            switch(this.request.getPathInfo()) {
                case ACCESS_INTERFACE_PATH:
                    return AccessServiceInterface.handleAccessInterfaceExceptions(ex, this.request, null);
                case ACCESS_NOTIFICATION_INTERFACE_PATH:
                    return AccessNotificationServiceInterface.handleAccessNotificationInterfaceExceptions(ex, this.request, null);
                case ACKNOWLEDGMENT_INTERFACE_PATH:
                    return AcknowledgementServiceInterface.handleAcknowledgementInterfaceExceptions(ex, this.request, null);
                case CAPABILITY_INTERFACE_PATH:
                    return CapabilityServiceInterface.handleCapabilityInterfaceExceptions(ex, this.request, null);
                case SEARCH_SERVICE_INTERFACE_PATH:
                    return SearchServiceServiceInterface.handleSearchServiceInterfaceExceptions(ex, this.request, null);
                case ENCRYPTION_KEY_INTERFACE_PATH:
                    return EncryptionKeyServiceInterface.handleEncryptionInterfaceExceptions(ex, this.request, null);
                case ENCRYPTION_KEY_NOTIFY_INTERFACE_PATH:
                    return EncryptionKeyNotifyServiceInterface.handleEncryptionKeyNotifyInterfaceExceptions(ex, this.request, null);
                case GET_BY_LINK_INTERFACE_PATH: // Also for upload
                    if(Objects.equals(this.request.getMethod(), "GET")) {
                        return GetByLinkServiceInterface.handleGetByLinkInterfaceExceptions(ex, this.request, null);
                    } else if(Objects.equals(this.request.getMethod(), "POST")) {
                        return UploadLinkServiceInterface.handleUploadLinkInterfaceExceptions(ex, this.request, null);
                    }
                case GET_INTERFACE_PATH: // Also for upload
                    if(Objects.equals(this.request.getMethod(), "GET")) {
                        return GetServiceInterface.handleGetInterfaceExceptions(ex, this.request, null);
                    } else if(Objects.equals(this.request.getMethod(), "POST")) {
                        return UploadServiceInterface.handleUploadInterfaceExceptions(ex, this.request, null);
                    }
                case GET_SUMMARY_INTERFACE_PATH:
                    return GetSummaryServiceInterface.handleGetSummaryInterfaceExceptions(ex, this.request, null);
                case PING_INTERFACE_PATH:
                    return PingServiceInterface.handlePingInterfaceExceptions(ex, this.request, null);
                case SUBSCRIPTION_INTERFACE_PATH: // Also for remove subscription
                    if(Objects.equals(this.request.getMethod(), "POST")) {
                        return SubscriptionServiceInterface.handleSubscriptionInterfaceExceptions(ex, this.request, null);
                    } else if(Objects.equals(this.request.getMethod(), "DELETE")) {
                        return RemoveSubscriptionServiceInterface.handleRemoveSubscriptionInterfaceExceptions(ex, this.request, null);
                    }
                case SUBSCRIPTION_NOTIFICATION_INTERFACE_PATH:
                    return SubscriptionNotificationServiceInterface.handleSubscriptionNotificationInterfaceExceptions(ex, this.request, null);
                case GET_PUBLIC_KEY_INTERFACE_PATH:
                    if(Objects.equals(this.request.getMethod(), "GET")) {
                        return GetPublicKeyServiceInterface.handleGetPublicKeyExceptions(ex, this.request, null);
                    } else if(Objects.equals(this.request.getMethod(), "POST")) {
                        return PostPublicKeyServiceInterface.handlePostPublicKeyInterfaceExceptions(ex, this.request, null);
                    }
                default:
                    //Nothing to do, continue with the generic rules
            }
        }

        // For everything else, just return an internal server error
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
    }

}
