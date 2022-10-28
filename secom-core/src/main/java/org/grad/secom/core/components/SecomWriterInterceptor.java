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

import org.grad.secom.core.base.*;
import org.grad.secom.core.models.SECOM_ExchangeMetadataObject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

/**
 * The SECOM Writer Interceptor.
 * <p>
 * This is a SECOM writer interceptor which is able to access the SECOM messages
 * and generate/validate the metadata signatures when required. This, according
 * to SECOM is supported for the following message types:
 * <ul>
 *     <il>GetResponseObject</il>
 * </ul>
 * </p>
 * <p>
 * The method used for this case is a GET and the server is supposed to generate
 * a signature and attach it to the message. This takes place automatically
 * using this JAX-RS interceptor.
 * </p>
 * <p>
 * In addition to this functionality, the writer interceptor is also able to
 * encrypt and compress the data payloads, as long as the necessary SECOM
 * providers have been provided.
 * </p>
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Provider
public class SecomWriterInterceptor implements WriterInterceptor {

    // Class Variables
    private SecomCompressionProvider compressionProvider;
    private SecomEncryptionProvider encryptionProvider;
    private SecomCertificateProvider certificateProvider;
    private SecomSignatureProvider signatureProvider;

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
     * The WriterInterceptor aroundWriteTo function implementation.
     *
     * @param ctx   The writer interceptor context
     * @throws IOException When IO exceptions occur while accessing the context
     * @throws WebApplicationException For web-application failures
     */
    public void aroundWriteTo(WriterInterceptorContext ctx) throws IOException, WebApplicationException {
        // First pick up the interceptor context
        final Object entity = ctx.getEntity();

        // For empty just move on
        if (entity == null) {
            ctx.proceed();
            return;
        }

        /*
         * Only use this interceptor for data signature bearer objects such as:
         *  1. GetResponseObject
         */
        if (entity instanceof DigitalSignatureBearer digitalSignatureBearer) {
            digitalSignatureBearer.prepareMetadata(this.signatureProvider, this.encryptionProvider, this.compressionProvider)
                    .signData(this.certificateProvider, this.signatureProvider)
                    .encryptData(this.encryptionProvider)
                    .compressData(this.compressionProvider)
                    .encodeData();
        }
        /*
         * For plain binary data, we can also try to encrypt if possible. This
         * can be used in the following cases:
         *  1. GetByLink
         */
        else if (ctx.getEntity() instanceof byte[] payload) {
            if(this.encryptionProvider != null) {
                payload = this.encryptionProvider.encrypt(this.encryptionProvider.getEncryptionAlgorithm(), this.encryptionProvider.getEncryptionKey(), payload);
            }
            if(this.compressionProvider != null) {
                payload = this.compressionProvider.compress(this.compressionProvider.getCompressionAlgorithm(), payload);
            }
            ctx.setEntity(Base64.getEncoder().encode(payload));
            ctx.setEntity(payload);
        }

        // Proceed
        ctx.proceed();
    }

}
