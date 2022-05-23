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

/**
 * The SECOM Interfaces Object.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SecomInterfaces {

    // Class Variables
    private boolean upload;
    private boolean uploadLink;
    private boolean acknowledgment;
    private boolean get;
    private boolean getSummary;
    private boolean getByLink;
    private boolean subscription;
    private boolean subscriptionNotification;
    private boolean access;
    private boolean encryptionKey;

    /**
     * Is upload boolean.
     *
     * @return the boolean
     */
    public boolean isUpload() {
        return upload;
    }

    /**
     * Sets upload.
     *
     * @param upload the upload
     */
    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    /**
     * Is upload link boolean.
     *
     * @return the boolean
     */
    public boolean isUploadLink() {
        return uploadLink;
    }

    /**
     * Sets upload link.
     *
     * @param uploadLink the upload link
     */
    public void setUploadLink(boolean uploadLink) {
        this.uploadLink = uploadLink;
    }

    /**
     * Is acknowledgment boolean.
     *
     * @return the boolean
     */
    public boolean isAcknowledgment() {
        return acknowledgment;
    }

    /**
     * Sets acknowledgment.
     *
     * @param acknowledgment the acknowledgment
     */
    public void setAcknowledgment(boolean acknowledgment) {
        this.acknowledgment = acknowledgment;
    }

    /**
     * Is get boolean.
     *
     * @return the boolean
     */
    public boolean isGet() {
        return get;
    }

    /**
     * Sets get.
     *
     * @param get the get
     */
    public void setGet(boolean get) {
        this.get = get;
    }

    /**
     * Is get summary boolean.
     *
     * @return the boolean
     */
    public boolean isGetSummary() {
        return getSummary;
    }

    /**
     * Sets get summary.
     *
     * @param getSummary the get summary
     */
    public void setGetSummary(boolean getSummary) {
        this.getSummary = getSummary;
    }

    /**
     * Is get by link boolean.
     *
     * @return the boolean
     */
    public boolean isGetByLink() {
        return getByLink;
    }

    /**
     * Sets get by link.
     *
     * @param getByLink the get by link
     */
    public void setGetByLink(boolean getByLink) {
        this.getByLink = getByLink;
    }

    /**
     * Is subscription boolean.
     *
     * @return the boolean
     */
    public boolean isSubscription() {
        return subscription;
    }

    /**
     * Sets subscription.
     *
     * @param subscription the subscription
     */
    public void setSubscription(boolean subscription) {
        this.subscription = subscription;
    }

    /**
     * Is subscription notification boolean.
     *
     * @return the boolean
     */
    public boolean isSubscriptionNotification() {
        return subscriptionNotification;
    }

    /**
     * Sets subscription notification.
     *
     * @param subscriptionNotification the subscription notification
     */
    public void setSubscriptionNotification(boolean subscriptionNotification) {
        this.subscriptionNotification = subscriptionNotification;
    }

    /**
     * Is access boolean.
     *
     * @return the boolean
     */
    public boolean isAccess() {
        return access;
    }

    /**
     * Sets access.
     *
     * @param access the access
     */
    public void setAccess(boolean access) {
        this.access = access;
    }

    /**
     * Is encryption key boolean.
     *
     * @return the boolean
     */
    public boolean isEncryptionKey() {
        return encryptionKey;
    }

    /**
     * Sets encryption key.
     *
     * @param encryptionKey the encryption key
     */
    public void setEncryptionKey(boolean encryptionKey) {
        this.encryptionKey = encryptionKey;
    }
}
