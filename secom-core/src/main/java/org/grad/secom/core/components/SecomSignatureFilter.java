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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grad.secom.core.base.SecomCertificateProvider;
import org.grad.secom.core.base.SecomSignatureValidator;
import org.grad.secom.core.interfaces.UploadLinkSecomInterface;
import org.grad.secom.core.interfaces.UploadSecomInterface;
import org.grad.secom.core.models.UploadLinkObject;
import org.grad.secom.core.models.UploadObject;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * The SECOM Signature Filter
 *
 * When receiving a SECOM request, upload (and upload linke) messages might
 * contain a signature. These need to be verified to ensure that the request
 * has not been altered by unauthorised entities.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Provider
@PreMatching
public class SecomSignatureFilter implements ContainerRequestFilter {

    /**
     * The JAX-RS Providers Context.
     */
    @Context
    Providers providers;

    // Class Variables
    private SecomCertificateProvider certificateProvider;
    private SecomSignatureValidator signatureValidator;

    /**
     * The Class Constructor.
     *
     * @param signatureValidator    The signature validator
     */
    public SecomSignatureFilter(SecomCertificateProvider certificateProvider, SecomSignatureValidator signatureValidator) {
        this.certificateProvider = certificateProvider;
        this.signatureValidator = signatureValidator;
    }

    /**
     * The ContainerResponseFilter filter function implementation.
     *
     * @param rqstCtx   The filter's request context
     * @throws IOException When IO Exceptions occur
     */
    @Override
    public void filter(ContainerRequestContext rqstCtx) throws IOException {
        // For the Upload Interface Requests, validate the signature
        if(rqstCtx.getRequest().getMethod().equals("POST") && rqstCtx.getUriInfo().getPath().endsWith(UploadSecomInterface.UPLOAD_INTERFACE_PATH)) {
            if(this.signatureValidator != null) {
                final UploadObject obj = this.parseRequestBody(rqstCtx, UploadObject.class);

                // If we have an object, validate the signature
                if(obj != null) {
                    this.signatureValidator.validateSignature(this.certificateProvider.getDigitalSignatureCertificate(), obj.getEnvelope().getData(), obj.getEnvelopeSignature());
                }
            }
        }
        // For the Upload Link Interface Requests, validate the signature
        else if(rqstCtx.getRequest().getMethod().equals("POST")  && rqstCtx.getUriInfo().getPath().endsWith(UploadLinkSecomInterface.UPLOAD_LINK_INTERFACE_PATH)) {
            if(this.signatureValidator != null) {
                UploadLinkObject obj = this.parseRequestBody(rqstCtx, UploadLinkObject.class);

                // If we have an object, validate the signature
                if(obj != null) {
                    this.signatureValidator.validateSignature(this.certificateProvider.getDigitalSignatureCertificate(), obj.getEnvelope().getTransactionIdentifier().toString(), obj.getEnvelopeSignature());
                }
            }
        }

        // For everything else just move one
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
     * @param rqstCtx       The incoming request context
     * @param clazz         The class to map the request body into
     * @return the mapped object, populated by the request body
     * @param <T> the generic class to use for the mapping
     * @throws IOException for any IO exceptions while reading the data
     */
    private <T> T parseRequestBody(ContainerRequestContext rqstCtx, Class<T> clazz) throws IOException {
        // Get the request input stream and read the data
        final InputStream is = rqstCtx.getEntityStream();
        final byte[] data = is.readAllBytes();
        final String body = new String(data, StandardCharsets.UTF_8);

        // Update the input stream with a new one to re-initialise it
        rqstCtx.setEntityStream(new ByteArrayInputStream(data));

        // Get the JAX-RS registered object mapper and map the data to the object
        return providers.getContextResolver(ObjectMapper.class, rqstCtx.getMediaType())
                .getContext(UploadObject.class)
                .readValue(body, clazz);

    }

}
