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

package org.grad.secomv2.core.base;

/**
 * The SECOM Base64 String Array Record.
 *
 * Marks a string array whose elements are already Base64 encoded, e.g. the
 * minified PEM certificates, so that the envelope CSV string generation does
 * not encode them a second time, as described in the SECOM standard, section
 * 7.3.4. Plain string arrays are always Base64 encoded element by element.
 *
 * @param values    The already Base64 encoded string array values
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public record Base64StringArray(String[] values) {

    /**
     * Wraps the provided already Base64 encoded string array. Null arrays
     * are returned as null so that they are converted to empty strings.
     *
     * @param values    The already Base64 encoded string array values
     * @return the wrapped string array, or null if the array is null
     */
    public static Base64StringArray of(String[] values) {
        return values == null ? null : new Base64StringArray(values);
    }

}
