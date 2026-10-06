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

import jakarta.validation.constraints.NotNull;

/**
 * The SECOM Implemented Interfaces Object.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class ImplementedInterfaces {

    // Class Variables
    @NotNull
    private Boolean upload;
    @NotNull
    private Boolean uploadLink;
    @NotNull
    private Boolean get;
    @NotNull
    private Boolean getSummary;
    @NotNull
    private Boolean getByLink;
    @NotNull
    private Boolean subscription;
    @NotNull
    private Boolean access;
    @NotNull
    private Boolean encryptionKey;
    @NotNull
    private Boolean publicKey;

    /**
     * Instantiates a new Secom interfaces.
     */
    public ImplementedInterfaces() {
        this.upload = Boolean.FALSE;
        this.uploadLink = Boolean.FALSE;
        this.get = Boolean.FALSE;
        this.getSummary = Boolean.FALSE;
        this.getByLink = Boolean.FALSE;
        this.subscription = Boolean.FALSE;
        this.access = Boolean.FALSE;
        this.encryptionKey = Boolean.FALSE;
        this.publicKey = Boolean.FALSE;
    }

    /**
     * Gets upload.
     *
     * @return the upload
     */
    public Boolean getUpload() {
        return upload;
    }

    /**
     * Sets upload.
     *
     * @param upload the upload
     */
    public void setUpload(Boolean upload) {
        this.upload = upload;
    }

    /**
     * Gets upload link.
     *
     * @return the upload link
     */
    public Boolean getUploadLink() {
        return uploadLink;
    }

    /**
     * Sets upload link.
     *
     * @param uploadLink the upload link
     */
    public void setUploadLink(Boolean uploadLink) {
        this.uploadLink = uploadLink;
    }

    /**
     * Gets get.
     *
     * @return the get
     */
    public Boolean getGet() {
        return get;
    }

    /**
     * Sets get.
     *
     * @param get the get
     */
    public void setGet(Boolean get) {
        this.get = get;
    }

    /**
     * Gets get summary.
     *
     * @return the get summary
     */
    public Boolean getGetSummary() {
        return getSummary;
    }

    /**
     * Sets get summary.
     *
     * @param getSummary the get summary
     */
    public void setGetSummary(Boolean getSummary) {
        this.getSummary = getSummary;
    }

    /**
     * Gets get by link.
     *
     * @return the get by link
     */
    public Boolean getGetByLink() {
        return getByLink;
    }

    /**
     * Sets get by link.
     *
     * @param getByLink the get by link
     */
    public void setGetByLink(Boolean getByLink) {
        this.getByLink = getByLink;
    }

    /**
     * Gets subscription.
     *
     * @return the subscription
     */
    public Boolean getSubscription() {
        return subscription;
    }

    /**
     * Sets subscription.
     *
     * @param subscription the subscription
     */
    public void setSubscription(Boolean subscription) {
        this.subscription = subscription;
    }

    /**
     * Gets access.
     *
     * @return the access
     */
    public Boolean getAccess() {
        return access;
    }

    /**
     * Sets access.
     *
     * @param access the access
     */
    public void setAccess(Boolean access) {
        this.access = access;
    }

    /**
     * Gets encryption key.
     *
     * @return the encryption key
     */
    public Boolean getEncryptionKey() {
        return encryptionKey;
    }

    /**
     * Sets encryption key.
     *
     * @param encryptionKey the encryption key
     */
    public void setEncryptionKey(Boolean encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    /**
     * Get public key
     *
     * @return public key
     */
    public Boolean getPublicKey() { return publicKey; }

    /**
     * Set public key
     *
     * @param publicKey the public key
     */
    public void setPublicKey(Boolean publicKey) { this.publicKey = publicKey; }
}
