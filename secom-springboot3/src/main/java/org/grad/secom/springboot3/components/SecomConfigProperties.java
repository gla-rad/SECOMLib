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

package org.grad.secom.springboot3.components;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * The SECOM Configuration properties.
 *
 * @author
 */
@ConfigurationProperties(prefix = "secom.security.ssl")
public class SecomConfigProperties {

    // Configuration Variables
    private String keystore;
    private String keystoreType;
    private String keystorePassword;
    private String truststore;
    private String truststoreType;
    private String truststorePassword;
    private Boolean insecureSslPolicy;

    /**
     * Gets keystore.
     *
     * @return the keystore
     */
    public String getKeystore() {
        return keystore;
    }

    /**
     * Sets keystore.
     *
     * @param keystore the keystore
     */
    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

    /**
     * Gets keystore type.
     *
     * @return the keystore type
     */
    public String getKeystoreType() {
        return keystoreType;
    }

    /**
     * Sets keystore type.
     *
     * @param keystoreType the keystore type
     */
    public void setKeystoreType(String keystoreType) {
        this.keystoreType = keystoreType;
    }

    /**
     * Gets keystore password.
     *
     * @return the keystore password
     */
    public String getKeystorePassword() {
        return keystorePassword;
    }

    /**
     * Sets keystore password.
     *
     * @param keystorePassword the keystore password
     */
    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    /**
     * Gets truststore.
     *
     * @return the truststore
     */
    public String getTruststore() {
        return truststore;
    }

    /**
     * Sets truststore.
     *
     * @param truststore the truststore
     */
    public void setTruststore(String truststore) {
        this.truststore = truststore;
    }

    /**
     * Gets truststore type.
     *
     * @return the truststore type
     */
    public String getTruststoreType() {
        return truststoreType;
    }

    /**
     * Sets truststore type.
     *
     * @param truststoreType the truststore type
     */
    public void setTruststoreType(String truststoreType) {
        this.truststoreType = truststoreType;
    }

    /**
     * Gets truststore password.
     *
     * @return the truststore password
     */
    public String getTruststorePassword() {
        return truststorePassword;
    }

    /**
     * Sets truststore password.
     *
     * @param truststorePassword the truststore password
     */
    public void setTruststorePassword(String truststorePassword) {
        this.truststorePassword = truststorePassword;
    }

    /**
     * Gets insecure ssl policy.
     *
     * @return the insecure ssl policy
     */
    public Boolean getInsecureSslPolicy() {
        return insecureSslPolicy;
    }

    /**
     * Sets insecure ssl policy.
     *
     * @param insecureSslPolicy the insecure ssl policy
     */
    public void setInsecureSslPolicy(Boolean insecureSslPolicy) {
        this.insecureSslPolicy = insecureSslPolicy;
    }
}