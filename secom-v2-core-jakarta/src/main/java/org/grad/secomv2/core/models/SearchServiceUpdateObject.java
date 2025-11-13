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
package org.grad.secomv2.core.models;


/**
 * Object used in MSR service update operations
 *
 * @author Lawrence Hughes (email: Lawrence.Hughes@gla-rad.org)
 */
public class SearchServiceUpdateObject {

    private String[] certificates;
    private String version;
    private String endpointUri;
    private String apiDoc;
    private String statusEndpoint;

    /**
     * Gets certificate array
     *
     * @return the certificate array
     */
    public String[] getCertificates() {
        return certificates;
    }

    /**
     * Sets the certificate array
     *
     * @param certificates the certificate array
     */
    public void setCertificates(String[] certificates) {
        this.certificates = certificates;
    }

    /**
     * Gets the version
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version
     *
     * @param version the version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets endpoint uri
     *
     * @return the endpointUri
     */
    public String getEndpointUri() {
        return endpointUri;
    }

    /**
     * Sets the endpoint Uri
     *
     * @param endpointUri the certificate array
     */
    public void setEndpointUri(String endpointUri) {
        this.endpointUri = endpointUri;
    }

    /**
     * Gets the ApiDoc url
     *
     * @return the apiDoc
     */
    public String getApiDoc() {
        return apiDoc;
    }

    /**
     * Sets the apiDoc
     *
     * @param apiDoc the certificate array
     */
    public void setApiDoc(String apiDoc) {
        this.apiDoc = apiDoc;
    }

    /**
     * Gets the endpoint status url
     *
     * @return the statusEndpoint
     */
    public String getStatusEndpoint() {
        return statusEndpoint;
    }

    /**
     * Sets the setStatusEndpoint
     *
     * @param statusEndpoint the certificate array
     */
    public void setStatusEndpoint(String statusEndpoint) {
        this.statusEndpoint = statusEndpoint;
    }

}
