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

package org.grad.secom.core.interfaces;

import org.grad.secom.core.exceptions.SecomNotAuthorisedException;
import org.grad.secom.core.exceptions.SecomNotImplementedException;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;

/**
 * The Generic SECOM Interface.
 * <p/>
 * This interface contains the common functionality shared between all the
 * SECOM interfaces.
 *
 * @author Nikolaos Vastardis (email: NihttpStatuskolaos.Vastardis@gla-rad.org)
 */
public interface GenericSecomInterface {

    /**
     * Handle all exceptions in a common way.
     *
     * @param ex the exception that took place
     * @return the HTTP status code
     */
    static Response.Status handleCommonExceptionResponseCode(Exception ex) {
        if(ex instanceof SecomNotAuthorisedException) {
            return Response.Status.FORBIDDEN;
        } else if(ex instanceof NotAllowedException) {
            return Response.Status.METHOD_NOT_ALLOWED;
        } else if(ex instanceof SecomNotImplementedException) {
            return Response.Status.NOT_IMPLEMENTED;
        } else {
            return Response.Status.INTERNAL_SERVER_ERROR;
        }
    }

}
