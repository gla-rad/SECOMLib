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

import org.grad.secomv2.core.base.*;
import org.grad.secomv2.core.models.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * The SECOM Reader Interceptor
 * <p>
 * This is a SECOM reader interceptor which is able to access the SECOM messages
 * and decode, decrypt and finally decompress the data contained. This, according
 * to SECOM is supported for the following message types that contain data:
 * <ul>
 *     <il>GetResponseObject</il>
 *     <il>UploadObject</il>
 *     <il>GetByLink</il>
 * </ul>
 * </p>
 * <p>
 * This method is used when a client (or a server) receives one of the
 * applicable messages that contain payload data. This data according to SECOM
 * should be encoded and potentially compressed and encrypted. Therefore, it
 * makes sense to automatically handle these operations and if successful to
 * provide the user with the "clean" data to be used directly into the
 * application.
 * </p>
 * @author Lawrence Hughes (email: lawrence.hughes@gla-rad.org)
 */
@ControllerAdvice
public class SecomReaderInterceptor implements RequestBodyAdvice {

    // Class Variables
    private final SecomCompressionProvider compressionProvider;
    private final SecomEncryptionProvider encryptionProvider;

    /**
     * The Class Constructor.
     *
     * @param compressionProvider   The SECOM compression provider
     * @param encryptionProvider    The SECOM encryption provider
     */
    public SecomReaderInterceptor(SecomCompressionProvider compressionProvider,
                                  SecomEncryptionProvider encryptionProvider) {
        this.compressionProvider = compressionProvider;
        this.encryptionProvider = encryptionProvider;
    }

    /**
     * @param methodParameter   the method parameter of the controller method
     * @param targetType        the target type, not necessarily the same as the method
     *                          parameter type (e.g. for {@code HttpEntity<String>})
     * @param converterType     the selected converter type
     * @return true
     */
    @NullMarked
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * We don't need to alter the input message so this function just returns the input
     *
     * @param inputMessage      the request
     * @param parameter         the method parameter
     * @param targetType        the target type, not necessarily the same as the method
     *                          parameter type (e.g. for {@code HttpEntity<String>})
     * @param converterType     the converter type selected to read the body
     * @return the input request or a new instance (never {@code null})
     * @throws IOException      if an I/O error occurs
     */
    @NullMarked
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    /**
     * Invoked after the request body is read and converted into an Object. This function will
     * decode, decrypt and decompress the payload data
     *
     * @param body              the object converted from the request body
     * @param inputMessage      the request
     * @param parameter         the method parameter
     * @param targetType        the target type, not necessarily the same as the method
     *                          parameter type (e.g. for {@code HttpEntity<String>})
     * @param converterType     the converter type selected to read the body
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @NullMarked
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Get request path
        ServletServerHttpRequest servletRequest =
                (ServletServerHttpRequest) inputMessage;

        String path = servletRequest.getServletRequest().getRequestURI();

        if (!path.startsWith("/" + SecomConstants.SECOM_VERSION)) {
            return body;
        }

        Object obj = null;

        // For the Get Response Messages
        if (body instanceof GetResponseObject getResponseObject){
            obj = getResponseObject;
        }
        // For the Upload Object Messages
        else if (body instanceof UploadObject uploadObject) {
            obj = uploadObject;
        }
        // For the Search Filter Object
        else if (body instanceof SearchFilterObject searchFilterObject) {
            obj = searchFilterObject;
        }
        // For Get By Link Byte Array Messages
        else if(body instanceof byte[] data) {
            obj = data;
        }

        if (body instanceof DigitalSignatureCollectionBearer digitalSignatureCollectionBearer) {
            digitalSignatureCollectionBearer
                    .decodeData()
                    .decompressData(compressionProvider)
                    .decryptData(encryptionProvider);
        }
        else if (body instanceof EnvelopeSignatureBearer envelopeSignatureBearer) {
            AbstractEnvelope abstractEnvelope = envelopeSignatureBearer.getEnvelope();
            if (abstractEnvelope instanceof GenericDataBearer genericDataBearer) {
                genericDataBearer
                        .decodeData()
                        .decompressData(compressionProvider)
                        .decryptData(encryptionProvider);
            }
        }
        else if (body instanceof byte[] byteArray) {
            if (encryptionProvider != null) {
                byteArray = encryptionProvider.decrypt(
                        encryptionProvider.getEncryptionAlgorithm(),
                        encryptionProvider.getEncryptionKey(),
                        byteArray
                );
            }
            if (compressionProvider != null) {
                byteArray = compressionProvider.decompress(
                        compressionProvider.getCompressionAlgorithm(),
                        byteArray
                );
            }
            return byteArray;
        }

        return body;
    }

    /**
     * This method just returns the body without altering
     *
     * @param body              the object converted from the request body
     * @param inputMessage      the request
     * @param parameter         the method parameter
     * @param targetType        the target type, not necessarily the same as the method
     *                          parameter type (e.g. for {@code HttpEntity<String>})
     * @param converterType     the converter type selected to read the body
     * @return the body that was passed in
     */
    @NullMarked
    @Override
    public @Nullable Object handleEmptyBody(@Nullable Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
