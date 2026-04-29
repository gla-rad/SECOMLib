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

package org.grad.secomv2.core.interfaces;

import org.grad.secomv2.core.exceptions.SecomNotAuthorisedException;
import org.grad.secomv2.core.exceptions.SecomNotFoundException;
import org.grad.secomv2.core.exceptions.SecomNotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

/**
 * The Generic SECOM Interface.
 * <p/>
 * This interface contains the common functionality shared between all the
 * SECOM interfaces.
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
@RestController
@RequestMapping("/api/secom/")
public interface GenericSecomInterface {

    /**
     * Handle all exceptions in a common way.
     *
     * @param ex the exception that took place
     * @return the HTTP status code
     */

    static HttpStatus handleCommonExceptionResponseCode(Exception ex) {
        if(ex instanceof SecomNotAuthorisedException) {
            return HttpStatus.FORBIDDEN;
        } else if(ex instanceof HttpClientErrorException.MethodNotAllowed) {
            return HttpStatus.METHOD_NOT_ALLOWED;
        } else if(ex instanceof SecomNotImplementedException) {
            return HttpStatus.NOT_IMPLEMENTED;
        } else if (ex instanceof SecomNotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
