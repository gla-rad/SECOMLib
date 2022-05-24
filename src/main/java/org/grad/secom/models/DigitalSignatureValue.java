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
 * The SECOM Digital Signature Value Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class DigitalSignatureValue {

    // Class Variables
    private String signedPublicKeyID;
    private String signedPublicKeyRootKey;
    private String signedPublicKey;
    private String digitalSignature;

    /**
     * Gets signed public key id.
     *
     * @return the signed public key id
     */
    public String getSignedPublicKeyID() {
        return signedPublicKeyID;
    }

    /**
     * Sets signed public key id.
     *
     * @param signedPublicKeyID the signed public key id
     */
    public void setSignedPublicKeyID(String signedPublicKeyID) {
        this.signedPublicKeyID = signedPublicKeyID;
    }

    /**
     * Gets signed public key root key.
     *
     * @return the signed public key root key
     */
    public String getSignedPublicKeyRootKey() {
        return signedPublicKeyRootKey;
    }

    /**
     * Sets signed public key root key.
     *
     * @param signedPublicKeyRootKey the signed public key root key
     */
    public void setSignedPublicKeyRootKey(String signedPublicKeyRootKey) {
        this.signedPublicKeyRootKey = signedPublicKeyRootKey;
    }

    /**
     * Gets signed public key.
     *
     * @return the signed public key
     */
    public String getSignedPublicKey() {
        return signedPublicKey;
    }

    /**
     * Sets signed public key.
     *
     * @param signedPublicKey the signed public key
     */
    public void setSignedPublicKey(String signedPublicKey) {
        this.signedPublicKey = signedPublicKey;
    }

    /**
     * Gets digital signature.
     *
     * @return the digital signature
     */
    public String getDigitalSignature() {
        return digitalSignature;
    }

    /**
     * Sets digital signature.
     *
     * @param digitalSignature the digital signature
     */
    public void setDigitalSignature(String digitalSignature) {
        this.digitalSignature = digitalSignature;
    }
}
