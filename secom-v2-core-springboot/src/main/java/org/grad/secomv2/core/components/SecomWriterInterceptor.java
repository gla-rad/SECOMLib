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
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.util.Base64;

/**
 * The SECOM Writer Interceptor.
 * <p>
 * This is a SECOM writer interceptor which is able to access the SECOM messages
 * and generate/validate the metadata signatures when required. This, according
 * to SECOM is supported for the following message types:
 * <ul>
 *     <il>GetResponseObject</il>
 *     <il>GetByLink</il>
 * </ul>
 * </p>
 * <p>
 * The method used for this case is a GET and the server is supposed to generate
 * a signature and attach it to the message. This takes place automatically
 * using the Springboot ControllerAdvice annotation.
 * </p>
 * <p>
 * In addition to this functionality, this class is also able to
 * encrypt and compress the data payloads, as long as the necessary SECOM
 * providers have been provided.
 * </p>
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@ControllerAdvice
public class SecomWriterInterceptor implements ResponseBodyAdvice<Object> {

    // Class Variables
    private final SecomCompressionProvider compressionProvider;
    private final SecomEncryptionProvider encryptionProvider;
    private final SecomCertificateProvider certificateProvider;
    private final SecomSignatureProvider signatureProvider;

    /**
     * The Class Constructor.
     *
     * @param compressionProvider   The SECOM compression provider
     * @param encryptionProvider    The SECOM encryption provider
     * @param certificateProvider   The SECOM certificate provider
     * @param signatureProvider     The SECOM signature provider
     */
    public SecomWriterInterceptor(SecomCompressionProvider compressionProvider,
                                  SecomEncryptionProvider encryptionProvider,
                                  SecomCertificateProvider certificateProvider,
                                  SecomSignatureProvider signatureProvider) {
        this.compressionProvider = compressionProvider;
        this.encryptionProvider = encryptionProvider;
        this.certificateProvider = certificateProvider;
        this.signatureProvider = signatureProvider;
    }

    /**
     * Specify the types of data the ResponseBodyAdvice applies to
     *
     * @param returnType the class type of the body
     * @param converterType class converter type
     * @return boolean indicating if the class is applicable to the returnType
     */
    @NullMarked
    @Override
    public boolean supports(MethodParameter returnType,
                            Class converterType) {

        Class<?> paramType = returnType.getParameterType();

        return DigitalSignatureCollectionBearer.class.isAssignableFrom(paramType) || byte[].class.isAssignableFrom(paramType);
    }

    /**
     * Automatically signs the payload if it is a DigitalSignatureBearer class and optionally
     * encrypts/compresses the data if an EncryptionProvider/CompressionProvider are available.
     *
     * @param body The object returned from the controller (after execution, before serialization)
     * @param returnType The declared return type of the controller method
     * @param selectedContentType The selected media type for the response (e.g. application/json)
     * @param selectedConverterType The HttpMessageConverter type used to serialize the response
     * @param request The current request
     * @param response The response wrapper used to write the output
     * @return The processed object to be serialized and returned
     */
    @NullMarked
    @Override
    public @Nullable Object beforeBodyWrite(@Nullable Object body,
                                            MethodParameter returnType,
                                            MediaType selectedContentType,
                                            Class selectedConverterType,
                                            ServerHttpRequest request,
                                            ServerHttpResponse response) {
        // Get request path
        ServletServerHttpRequest servletRequest =
                (ServletServerHttpRequest) request;

        String path = servletRequest.getServletRequest().getRequestURI();

        if (!path.startsWith("/" + SecomConstants.SECOM_VERSION)) {
            return body;
        }

        if (body == null) {
            return null;
        }

        if (body instanceof DigitalSignatureCollectionBearer digitalSignatureCollectionBearer) {
            digitalSignatureCollectionBearer.prepareMetadata(this.signatureProvider)
                    .signData(this.certificateProvider, this.signatureProvider)
                    .encryptData(this.encryptionProvider)
                    .compressData(this.compressionProvider)
                    .encodeData();
        }
        /*
         * For plain binary data, we can also try to encrypt and compress if
         * possible. This can be used in the following cases:
         *  1. GetByLink
         */
        else if (body instanceof byte[] payload) {
            if(this.encryptionProvider != null) {
                payload = this.encryptionProvider.encrypt(this.encryptionProvider.getEncryptionAlgorithm(),
                        this.encryptionProvider.getEncryptionKey(), payload);
            }
            if(this.compressionProvider != null) {
                payload = this.compressionProvider.compress(this.compressionProvider.getCompressionAlgorithm(),
                        payload);
            }
            body = Base64.getEncoder().encode(payload);
        }

        return body;

    }
}
