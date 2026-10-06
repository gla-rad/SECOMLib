/*
 * Copyright (c) 2026 AIVeNautics
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

package org.grad.secomv2.core.models;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

/**
 * The SECOM Get By Link Response Object Class.
 *
 * @author Junyeon Won (email: junyeon.won@aivenautics.com)
 */
public class GetByLinkResponseObject {
    // Class Variables
    @Schema(description = "Data as Base64 encoded binary format")
    @NotNull
    private byte[] data;

    /**
     * Gets data.
     *
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(byte[] data) {
        this.data = data;
    }
}
