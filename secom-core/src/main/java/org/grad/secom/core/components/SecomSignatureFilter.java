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

import org.grad.secom.core.base.SecomCertificateProvider;
import org.grad.secom.core.exceptions.SecomSignatureVerificationException;
import org.grad.secom.core.base.SecomSignatureValidator;
import org.grad.secom.core.interfaces.UploadLinkSecomInterface;
import org.grad.secom.core.interfaces.UploadSecomInterface;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class SecomSignatureFilter implements ContainerResponseFilter {

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
     * @param rspnCtx   The filter's response context
     * @throws IOException When IO Exceptions occur
     */
    @Override
    public void filter(ContainerRequestContext rqstCtx, ContainerResponseContext rspnCtx) throws IOException {
        // For the Upload Interface Requests, validate the signature
//        if(rqstCtx.getRequest().getMethod().equals("POST")
//                && rqstCtx.getUriInfo().getPath().endsWith(UploadSecomInterface.UPLOAD_INTERFACE_PATH)) {
//            if(this.signatureValidator != null) {
//                try {
//                    JSONObject jsonObject = (JSONObject) new JSONParser().parse(
//                            new InputStreamReader(rqstCtx.getEntityStream(), StandardCharsets.UTF_8));
//                } catch (ParseException ex) {
//                    throw new SecomSignatureVerificationException(ex.getMessage());
//                }
//            }
//        }
        // For the Upload Link Interface Requests, validate the signature
//        else if(rqstCtx.getRequest().getMethod().equals("POST")
//                && rqstCtx.getUriInfo().getPath().endsWith(UploadLinkSecomInterface.UPLOAD_LINK_INTERFACE_PATH)) {
//            if(this.signatureValidator != null) {
//                try {
//                    JSONObject jsonObject = (JSONObject)new JSONParser().parse(
//                            new InputStreamReader(rqstCtx.getEntityStream(), StandardCharsets.UTF_8));
//                } catch (ParseException ex) {
//                    throw new SecomSignatureVerificationException(ex.getMessage());
//                }
//            }
//        }
        // For everything else just move one
    }
}
