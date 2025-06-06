/*
 * Copyright (c) 2025 GLA Research and Development Directorate
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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The SECOM Generic Signature Bearer Interface.
 *
 * This class can be used to identify all SECOM objects that bear a signature
 * for data security.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface GenericSignatureBearer {

    /**
     * This is the main function that sets the digital signature onto a SECOM
     * signature bearer message.
     *
     * @param digitalSignature  The digital signature to be set
     */
    @JsonIgnore
    void setDigitalSignature(String digitalSignature);
}
