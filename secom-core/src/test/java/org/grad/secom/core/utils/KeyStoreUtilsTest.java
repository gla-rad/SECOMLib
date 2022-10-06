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

package org.grad.secom.core.utils;

import org.junit.jupiter.api.Test;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static org.junit.jupiter.api.Assertions.*;

class KeyStoreUtilsTest {

    /**
     * Test that we can load the keystore provide successfully.
     */
    @Test
    void testGetKeyStore() throws CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException {
        // Load the test trust store
        KeyStore keyStore = KeyStoreUtils.getKeyStore("keystore.jks","password", "JKS");

        // Assert that it was loaded correctly
        assertNotNull(keyStore);
        assertNotNull(keyStore.getCertificate("test-cert"));
        assertEquals("X.509", keyStore.getCertificate("test-cert").getType());
        assertNotNull(keyStore.getCertificate("test-cert").getPublicKey());
        assertEquals("RSA", keyStore.getCertificate("test-cert").getPublicKey().getAlgorithm());
    }

    /**
     * Test that we can load the keystore provided into a keystore manager
     * successfully.
     */
    @Test
    void testGetKeyManagerFactory() throws CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
        // Load the test trust store
        KeyManagerFactory keyManagerFactory = KeyStoreUtils.getKeyManagerFactory("keystore.jks","password", "JKS", "PKIX");

        // Assert that it was loaded correctly
        assertNotNull(keyManagerFactory);
        assertEquals(1, keyManagerFactory.getKeyManagers().length);
        assertTrue(keyManagerFactory.getKeyManagers()[0] instanceof X509KeyManager);
    }

    /**
     * Test that we can load the truststore provided into a truststore manager
     * successfully.
     */
    @Test
    void testGetTrustManagerFactory() throws CertificateException, KeyStoreException, NoSuchAlgorithmException, IOException {
        // Load the test trust store
        TrustManagerFactory trustManagerFactory = KeyStoreUtils.getTrustManagerFactory("truststore.jks","password", "JKS", "PKIX");

        // Assert that it was loaded correctly
        assertNotNull(trustManagerFactory);
        assertEquals(1, trustManagerFactory.getTrustManagers().length);
        assertTrue(trustManagerFactory.getTrustManagers()[0] instanceof X509TrustManager);
    }
}