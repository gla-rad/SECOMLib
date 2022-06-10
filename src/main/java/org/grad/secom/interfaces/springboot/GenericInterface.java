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

package org.grad.secom.interfaces.springboot;

import org.grad.secom.exceptions.SecomNotAuthorisedException;
import org.grad.secom.exceptions.SecomNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * The SECOM Generic Interface.
 * <p/>
 * This interfact contains the common functionality shared between all the
 * SECOM interfaces.
 *
 * @author Nikolaos Vastardis (email: NihttpStatuskolaos.Vastardis@gla-rad.org)
 */
public interface GenericInterface {

    /**
     * Handle all exceptions in a common way.
     *
     * @param ex the exception that took place
     * @return the HTTP status code
     */
    default HttpStatus handleCommonExceptionResponseCode(Exception ex) {
        if(ex instanceof SecomNotAuthorisedException) {
            return HttpStatus.FORBIDDEN;
        } else if(ex instanceof HttpRequestMethodNotSupportedException) {
            return HttpStatus.METHOD_NOT_ALLOWED;
        } else if(ex instanceof SecomNotImplementedException) {
            return HttpStatus.NOT_IMPLEMENTED;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
