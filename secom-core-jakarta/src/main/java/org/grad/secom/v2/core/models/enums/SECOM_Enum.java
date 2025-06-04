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

package org.grad.secom.v2.core.models.enums;

/**
 * THe SECOM Enum Interface.
 *
 * This interface is used to describe and identify Java enumerations used in
 * SECOM. It also includes some common functionality that these enums should
 * have, such as the conversion to a string representation.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface SECOM_Enum {

    /**
     * The conversion to a string operation.
     *
     * @return the SECOM string representation of the enum
     */
    String asString();

}
