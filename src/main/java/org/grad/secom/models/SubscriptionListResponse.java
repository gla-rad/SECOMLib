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

import java.util.List;

/**
 * The SECOM Subscription List Response Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SubscriptionListResponse {

    // Class Variables
    private List<SubscriptionList> subscriptionList;
    private String responseText;

    /**
     * Gets subscription list.
     *
     * @return the subscription list
     */
    public List<SubscriptionList> getSubscriptionList() {
        return subscriptionList;
    }

    /**
     * Sets subscription list.
     *
     * @param subscriptionList the subscription list
     */
    public void setSubscriptionList(List<SubscriptionList> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

    /**
     * Gets response text.
     *
     * @return the response text
     */
    public String getResponseText() {
        return responseText;
    }

    /**
     * Sets response text.
     *
     * @param responseText the response text
     */
    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

}
