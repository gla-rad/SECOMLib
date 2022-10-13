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

import org.grad.secom.core.base.DigitalSignatureBearer;
import org.grad.secom.core.base.DigitalSignatureCertificate;
import org.grad.secom.core.base.SecomCertificateProvider;
import org.grad.secom.core.base.SecomSignatureProvider;
import org.grad.secom.core.models.*;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.*;
import java.io.IOException;

/**
 * The SECOM Signature Interceptor.
 * <p/>
 * This is a SECOM server interceptor which is able to access the SECOM messages
 * and generate/validate the metadata signatures when required. This, according
 * to SECOM is supported for the following message types:
 * <ul>
 *     <il>GetResponseObject</il>
 * </ul>
 * <p/>
 * The method used for this case is a GET and the server is supposed to generate
 * a signature and attach it to the message. This takes place automatically
 * using this JAX-RS interceptor.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
@Provider
public class SecomSignatureInterceptor implements WriterInterceptor {

    // Class Variables
    private SecomCertificateProvider certificateProvider;
    private SecomSignatureProvider signatureProvider;

    /**
     * The Class Constructor.
     *
     * @param signatureProvider The SECOM signature provider
     */
    public SecomSignatureInterceptor(SecomCertificateProvider certificateProvider, SecomSignatureProvider signatureProvider) {
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
        // Sanity Checks
        if(this.signatureProvider == null) {
            ctx.proceed();
            return;
        }

        // First pick up the interceptor context
        final Object entity = ctx.getEntity();

        /*
         * Only use this interceptor for data signature bearer objects such as:
         *  1. GetResponseObject
         */
        if(entity instanceof DigitalSignatureBearer) {
            ((DigitalSignatureBearer)entity).signData(this.certificateProvider, this.signatureProvider);
        }

        // Proceed
        ctx.proceed();
    }

}
