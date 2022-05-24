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

package org.grad.secom.models;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * The Status Response Class
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class StatusResponse {

    // Class Variables
    @NotNull
    private String status;
    @NotNull
    private String version;
    private LocalDateTime lastPrivateInteractionTime;

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets last private interaction time.
     *
     * @return the last private interaction time
     */
    public LocalDateTime getLastPrivateInteractionTime() {
        return lastPrivateInteractionTime;
    }

    /**
     * Sets last private interaction time.
     *
     * @param lastPrivateInteractionTime the last private interaction time
     */
    public void setLastPrivateInteractionTime(LocalDateTime lastPrivateInteractionTime) {
        this.lastPrivateInteractionTime = lastPrivateInteractionTime;
    }
}
