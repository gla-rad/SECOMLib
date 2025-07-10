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

package org.grad.secom.core.components;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.grad.secom.core.base.SecomConstants;
import org.grad.secom.core.interfaces.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import static org.grad.secom.core.interfaces.AccessNotificationSecomInterface.ACCESS_NOTIFICATION_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.AccessSecomInterface.ACCESS_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.AcknowledgementSecomInterface.ACKNOWLEDGMENT_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.CapabilitySecomInterface.CAPABILITY_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.SearchServiceSecomInterface.SEARCH_SERVICE_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.EncryptionKeyNotifySecomInterface.ENCRYPTION_KEY_NOTIFY_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.EncryptionKeySecomInterface.ENCRYPTION_KEY_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.GetByLinkSecomInterface.GET_BY_LINK_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.GetSecomInterface.GET_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.GetSummarySecomInterface.GET_SUMMARY_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.PingSecomInterface.PING_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.SubscriptionNotificationSecomInterface.SUBSCRIPTION_NOTIFICATION_INTERFACE_PATH;
import static org.grad.secom.core.interfaces.SubscriptionSecomInterface.SUBSCRIPTION_INTERFACE_PATH;

/**
 * The SECOM Exception Manager Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Provider
public class SecomExceptionMapper implements ExceptionMapper<Exception>, ContextResolver<ExceptionMapper<Exception>> {

    // Class Variables
    private Application application;

    /**
     * The JAX-RS Providers Context.
     */
    @Context
    private Providers providers;

    /**
     * The Request Context.
     */
    @Context
    private HttpServletRequest request;

    /**
     * The Request URI Information.
     */
    @Context
    private UriInfo uriInfo;

    /**
     * A constructor to return a reference to the application being served.
     *
     * @param application the application being served.
     */
    public SecomExceptionMapper(Application application) {
        this.application = application;
    }

    /**
     * Generate the response based on the exceptions thrown by the respective
     * SECOM endpoint called. This can be extracted by the request context.
     *
     * @param ex the exception that was thrown
     * @return the response to be returned
     */
    @Override
    public Response toResponse(Exception ex) {
        // This is not our error, propagate
        if(!this.uriInfo.getPath().startsWith("/" + SecomConstants.SECOM_VERSION)) {
            final PathSegment secomVersion = uriInfo.getPathSegments().getFirst();
            final ExceptionMapper<Exception> secomExceptionMapper = Optional.ofNullable(secomVersion)
                    .map(PathSegment::toString)
                    .map(String::toUpperCase)
                    .map(v -> String.format("secom%sExceptionMapper", v))
                    .map(this.application.getProperties()::get)
                    .map(obj -> (ExceptionMapper<Exception>)obj)
                    .orElseThrow(() -> new RuntimeException(ex));
            return secomExceptionMapper.toResponse(ex);
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
        if(Optional.ofNullable(this.request).map(HttpServletRequest::getPathInfo).isPresent()) {
            switch(this.request.getPathInfo()) {
                case ACCESS_INTERFACE_PATH:
                    return AccessSecomInterface.handleAccessInterfaceExceptions(ex, this.request, null);
                case ACCESS_NOTIFICATION_INTERFACE_PATH:
                    return AccessNotificationSecomInterface.handleAccessNotificationInterfaceExceptions(ex, this.request, null);
                case ACKNOWLEDGMENT_INTERFACE_PATH:
                    return AcknowledgementSecomInterface.handleAcknowledgementInterfaceExceptions(ex, this.request, null);
                case CAPABILITY_INTERFACE_PATH:
                    return CapabilitySecomInterface.handleCapabilityInterfaceExceptions(ex, this.request, null);
                case SEARCH_SERVICE_INTERFACE_PATH:
                    return SearchServiceSecomInterface.handleSearchServiceInterfaceExceptions(ex, this.request, null);
                case ENCRYPTION_KEY_INTERFACE_PATH:
                    return EncryptionKeySecomInterface.handleEncryptionInterfaceExceptions(ex, this.request, null);
                case ENCRYPTION_KEY_NOTIFY_INTERFACE_PATH:
                    return EncryptionKeyNotifySecomInterface.handleEncryptionKeyNotifyInterfaceExceptions(ex, this.request, null);
                case GET_BY_LINK_INTERFACE_PATH: // Also for upload
                    if(Objects.equals(this.request.getMethod(), "GET")) {
                        return GetByLinkSecomInterface.handleGetByLinkInterfaceExceptions(ex, this.request, null);
                    } else if(Objects.equals(this.request.getMethod(), "POST")) {
                        return UploadLinkSecomInterface.handleUploadLinkInterfaceExceptions(ex, this.request, null);
                    }
                case GET_INTERFACE_PATH: // Also for upload
                    if(Objects.equals(this.request.getMethod(), "GET")) {
                        return GetSecomInterface.handleGetInterfaceExceptions(ex, this.request, null);
                    } else if(Objects.equals(this.request.getMethod(), "POST")) {
                        return UploadSecomInterface.handleUploadInterfaceExceptions(ex, this.request, null);
                    }
                case GET_SUMMARY_INTERFACE_PATH:
                    return GetSummarySecomInterface.handleGetSummaryInterfaceExceptions(ex, this.request, null);
                case PING_INTERFACE_PATH:
                    return PingSecomInterface.handlePingInterfaceExceptions(ex, this.request, null);
                case SUBSCRIPTION_INTERFACE_PATH: // Also for remove subscription
                    if(Objects.equals(this.request.getMethod(), "POST")) {
                        return SubscriptionSecomInterface.handleSubscriptionInterfaceExceptions(ex, this.request, null);
                    } else if(Objects.equals(this.request.getMethod(), "DELETE")) {
                        return RemoveSubscriptionSecomInterface.handleRemoveSubscriptionInterfaceExceptions(ex, this.request, null);
                    }
                case SUBSCRIPTION_NOTIFICATION_INTERFACE_PATH:
                    return SubscriptionNotificationSecomInterface.handleSubscriptionNotificationInterfaceExceptions(ex, this.request, null);
                default:
                    //Nothing to do, continue with the generic rules
            }
        }

        // For everything else, just return an internal server error
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
    }

    /**
     * Return the established exception mapper when required.
     *
     * @param type the type of the class to return the object mapper for
     * @return the appropriate object mapper
     */
    @Override
    public ExceptionMapper<Exception> getContext(Class<?> type) {
        if (type.isInstance(ExceptionMapper.class)) {
            return this;
        }
        return null;
    }

}
