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

package org.grad.secom.core.base;

import java.security.KeyStore;

/**
 * The SECOM Trust Store Provider Interface.
 *
 * This interface dictates the implementation of the SECOM trust store provider.
 * This is required for the SECOM library to be able to automatically pick up
 * the keystore that contains the trusted SECOM CA certificate chain.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public interface SecomTrustStoreProvider {

    /**
     * Returns the alias of the root certificate as it is contained in the
     * provided trust store. This can be used to pinpoint the actual root
     * certificate entry.
     *
     * @return the alias of the root certificate
     */
    default String getCARootCertificateAlias() {
        return "root";
    }

    /**
     * Returns the trust store that contains the trusted SECOm certificate chain.
     * This is required to validate the received certificate for every applicable
     * request.
     *
     * @return the SECOM trust store
     */
    KeyStore getTrustStore();

}
