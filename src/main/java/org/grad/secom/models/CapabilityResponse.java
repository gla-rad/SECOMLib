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

import java.net.URL;

/**
 * The SECOM Capability Response Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class CapabilityResponse {

    // Class Variables
    String payloadName;
    String payloadVersion;
    URL payloadSchemaUrl;
    SecomInterfaces implementedInterfaces;

    /**
     * Gets payload name.
     *
     * @return the payload name
     */
    public String getPayloadName() {
        return payloadName;
    }

    /**
     * Sets payload name.
     *
     * @param payloadName the payload name
     */
    public void setPayloadName(String payloadName) {
        this.payloadName = payloadName;
    }

    /**
     * Gets payload version.
     *
     * @return the payload version
     */
    public String getPayloadVersion() {
        return payloadVersion;
    }

    /**
     * Sets payload version.
     *
     * @param payloadVersion the payload version
     */
    public void setPayloadVersion(String payloadVersion) {
        this.payloadVersion = payloadVersion;
    }

    /**
     * Gets payload schema url.
     *
     * @return the payload schema url
     */
    public URL getPayloadSchemaUrl() {
        return payloadSchemaUrl;
    }

    /**
     * Sets payload schema url.
     *
     * @param payloadSchemaUrl the payload schema url
     */
    public void setPayloadSchemaUrl(URL payloadSchemaUrl) {
        this.payloadSchemaUrl = payloadSchemaUrl;
    }

    /**
     * Gets implemented interfaces.
     *
     * @return the implemented interfaces
     */
    public SecomInterfaces getImplementedInterfaces() {
        return implementedInterfaces;
    }

    /**
     * Sets implemented interfaces.
     *
     * @param implementedInterfaces the implemented interfaces
     */
    public void setImplementedInterfaces(SecomInterfaces implementedInterfaces) {
        this.implementedInterfaces = implementedInterfaces;
    }
}
