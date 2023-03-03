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

package org.grad.secom.core.base;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * The SECOM Constants class
 *
 * This class is used to define a list of constants defined by SECOM.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SecomConstants {

    /**
     * The SECOM Data Protection Scheme.
     */
    public static final String SECOM_PROTECTION_SCHEME = "SECOM";

    /**
     * The Algorithm to be used for generating signatures.
     */
    public static final String CERTIFICATE_THUMBPRINT_HASH = "SHA-1";

    /**
     * The SECOM Maximum Payload Size in KiloBytes.
     */
    public static final int MAX_PAYLOAD_SIZE_IN_KB = 350;

    /**
     * The SECOM DATE, TIME and DATE_TIME formats.
     */
    public static final String SECOM_DATE_FORMAT = "yyyyMMdd";
    public static final String SECOM_TIME_FORMAT = "HHmmss";
    public static final String SECOM_DATE_TIME_FORMAT = SECOM_DATE_FORMAT + "'T'" + SECOM_TIME_FORMAT;
    public static final DateTimeFormatter SECOM_DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(SECOM_DATE_TIME_FORMAT)
            .parseLenient()
            .appendOffset("+HHmm", "Z")
            .toFormatter();

}
