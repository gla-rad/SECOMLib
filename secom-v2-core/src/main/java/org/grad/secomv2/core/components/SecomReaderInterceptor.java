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

package org.grad.secomv2.core.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grad.secomv2.core.base.*;
import org.grad.secomv2.core.models.AbstractEnvelope;
import org.grad.secomv2.core.models.GetResponseObject;
import org.grad.secomv2.core.models.UploadObject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
 * should be encoded and potentially compressed and encrypted. Therefore it
 * makes sense to automatically handle these operations and if successful to
 * provide the user with the "clean" data to be used directly into the
 * application.
 * </p>
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Provider
public class SecomReaderInterceptor implements ReaderInterceptor {

    /**
     * The JAX-RS Providers Context.
     */
    @Context
    Providers providers;

    // Class Variables
    private SecomCompressionProvider compressionProvider;
    private SecomEncryptionProvider encryptionProvider;

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
     * The SecomReaderInterceptor aroundReadFrom function implementation.
     *
     * @param ctx   The reader interceptor context
     * @return the updated context
     * @throws IOException  When IO exceptions occur while accessing the context
     * @throws WebApplicationException For web-application failures
     */
    @Override
    public Object aroundReadFrom(ReaderInterceptorContext ctx) throws IOException, WebApplicationException {
        // Start with an empty object
        InputStream is = ctx.getInputStream();
        Object obj = null;

        // For the Get Response Messages
        if (ctx.getType().isAssignableFrom(GetResponseObject.class)){
            obj = this.parseRequestBody(is, ctx.getMediaType(), GetResponseObject.class);
        }
        // For the Upload Object Messages
        else if (ctx.getType().isAssignableFrom(UploadObject.class)) {
            obj = this.parseRequestBody(is, ctx.getMediaType(), UploadObject.class);
        }
        // For Get By Link Byte Array Messages
        else if(ctx.getType().isAssignableFrom(byte[].class)) {
            obj = this.parseRequestBody(is, ctx.getMediaType(), byte[].class);
        }
        // For everything else just proceed
        else {
            return ctx.proceed();
        }

        /*
         * Use this interceptor for generic data bearer objects such as:
         *  1. GetResponseObject
         */
        if(obj instanceof final DigitalSignatureCollectionBearer digitalSignatureCollectionBearer) {
            digitalSignatureCollectionBearer
                    .decodeData()
                    .decompressData(this.compressionProvider)
                    .decryptData(this.encryptionProvider);
        }
        /*
         * Use this interceptor for envelope signature bearer objects such as:
         *  1. UploadObject
         */
        else if(obj instanceof final EnvelopeSignatureBearer envelopeSignatureBearer) {
            AbstractEnvelope abstractEnvelope = envelopeSignatureBearer.getEnvelope();
            if(abstractEnvelope instanceof final GenericDataBearer genericDataBearer) {
                genericDataBearer
                        .decodeData()
                        .decompressData(this.compressionProvider)
                        .decryptData(this.encryptionProvider);
            }
        }
        /*
         * For plain binary data, we can also try to decompress and decrypt if
         * possible. This can be used in the following cases:
         *  1. GetByLink
         */
        else if(obj instanceof byte[] byteArray) {
            if(this.encryptionProvider != null) {
                byteArray = this.encryptionProvider.decrypt(this.encryptionProvider.getEncryptionAlgorithm(), this.encryptionProvider.getEncryptionKey(), byteArray);
            }
            if(this.compressionProvider != null) {
                byteArray = this.compressionProvider.decompress(this.compressionProvider.getCompressionAlgorithm(), byteArray);
            }
            obj = byteArray;
        }

        // Update with context with the processed result
        final byte[] processed = providers.getContextResolver(ObjectMapper.class, ctx.getMediaType())
                .getContext(UploadObject.class)
                .writeValueAsBytes(obj);
        ctx.setInputStream(new ByteArrayInputStream(processed));

        // And proceed
        return ctx.proceed();
    }

    /**
     * A helper method that parses the data in the body of the incoming request.
     * One issue is that after we read that data, the request input stream is
     * left empty, so we need to recreate it with the original data once more.
     * <p/>
     * Here is a pointer: https://github.com/quarkusio/quarkus/issues/17430
     * <p/>
     * Once we have the request we can use the provided object mapper to
     * translate the JSON string into an actual SECOM object.
     *
     * @param is            The incoming input stream
     * @param mediaType     The incoming media type
     * @param clazz         The class to map the request body into
     * @return the mapped object, populated by the request body
     * @param <T> the generic class to use for the mapping
     * @throws IOException for any IO exceptions while reading the data
     */
    private <T> T parseRequestBody(InputStream is, MediaType mediaType, Class<T> clazz) throws IOException {
        // Get the request input stream and read the data
        final byte[] data = is.readAllBytes();
        final String body = new String(data, StandardCharsets.UTF_8);

        // Get the JAX-RS registered object mapper and map the data to the object
        return providers.getContextResolver(ObjectMapper.class, mediaType)
                .getContext(clazz)
                .readValue(body, clazz);
    }
}
