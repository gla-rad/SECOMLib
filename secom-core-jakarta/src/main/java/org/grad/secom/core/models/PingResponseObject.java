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

package org.grad.secom.core.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.grad.secom.core.base.InstantDeserializer;
import org.grad.secom.core.base.InstantSerializer;

import java.time.Instant;

/**
 * The SECOM Ping Response Object Class
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class PingResponseObject {

    // Class Variables
    @Schema(description = "The last private interaction date-time", type = "string", example = "19850412T101530", pattern = "(\\d{8})T(\\d{6})(Z|\\+\\d{4})?")
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant lastPrivateInteractionTime;

    /**
     * Gets last private interaction time.
     *
     * @return the last private interaction time
     */
    public Instant getLastPrivateInteractionTime() {
        return lastPrivateInteractionTime;
    }

    /**
     * Sets last private interaction time.
     *
     * @param lastPrivateInteractionTime the last private interaction time
     */
    public void setLastPrivateInteractionTime( Instant lastPrivateInteractionTime) {
        this.lastPrivateInteractionTime = lastPrivateInteractionTime;
    }

}
