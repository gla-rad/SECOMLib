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

package org.grad.secom.interfaces;

import org.grad.secom.models.EncryptionKeyResponse;
import org.grad.secom.models.EncryptionKeyRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The SECOM Encryption Interface Definition.
 * </p>
 * This interface definition can be used by the SECOM-compliant services in
 * order to direct the implementation of the relevant endpoint according to
 * the specified SECOM standard version.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface EncryptionInterface {

    /**
     * The Interface Endpoint Path.
     */
    public static final String CAPABILITY_INTERFACE_PATH = "/v1/capability";

    /**
     * POST /v1/encryptionkey : The purpose of the interface is to exchange a
     * temporary secret key.
     *
     * @return the encryption key response object
     */
    @PostMapping(CAPABILITY_INTERFACE_PATH)
    ResponseEntity<EncryptionKeyResponse> uploadEncryptionKey(@RequestBody EncryptionKeyRequest encryptionKeyRequest);

}
