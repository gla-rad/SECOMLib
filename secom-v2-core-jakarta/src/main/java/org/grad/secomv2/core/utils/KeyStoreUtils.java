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

package org.grad.secomv2.core.utils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Optional;

/**
 * The SECOM KeyStore Utilities Class.
 *
 * This class contains some useful functionality to deal with Java key-stores
 * and trust-stores. For example loading those into the required factories used
 * by the SecomClient class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class KeyStoreUtils {

    /**
     * Loads the specified key-store, using the provided algorithm type and
     * password. The keystore will first be checked if it exists as a file in
     * the filesystem, and if not, it will be loaded directly from the
     * classpath.
     *
     *
     * @param keystore              The location of the keystore to be loaded
     * @param keystorePassword      The password for the keystore to be loaded
     * @param keystoreType          The type of the keystore to be loaded
     * @return the loaded KeyManagerFactory object
     * @throws KeyStoreException When the keystore loading operation fails
     * @throws NoSuchAlgorithmException  If the keystore algorithm provided does not exist
     * @throws IOException When the keystore data cannot be loaded
     * @throws CertificateException If the certificated loaded are invalid
     */
    public static KeyStore getKeyStore(String keystore,
                                       String keystorePassword,
                                       String keystoreType) throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException {
        Path keystorePath = Paths.get(keystore);
        KeyStore clientKeyStore = KeyStore.getInstance(Optional.ofNullable(keystoreType).orElse(KeyStore.getDefaultType()));

        // Check if keystore exists as a file, otherwise get it from the classpath
        try (InputStream ksFileInputStream = Files.exists(keystorePath) ? Files.newInputStream(keystorePath) : KeyStoreUtils.class.getClassLoader().getResourceAsStream(keystore)) {
            clientKeyStore.load(ksFileInputStream, keystorePassword.toCharArray());
            return clientKeyStore;
        }
    }

    /**
     * Loads the specified key-store, using the provided algorithm type and
     * password into a KeyManagerFactory object.
     *
     * @param keystore              The location of the keystore to be loaded
     * @param keystorePassword      The password for the keystore to be loaded
     * @param keystoreType          The type of the keystore to be loaded
     * @param algorithm             The algorithm to be used for loading the keystore
     * @return the loaded KeyManagerFactory object
     * @throws KeyStoreException When the keystore loading operation fails
     * @throws NoSuchAlgorithmException  If the keystore algorithm provided does not exist
     * @throws IOException When the keystore data cannot be loaded
     * @throws CertificateException If the certificated loaded are invalid
     * @throws UnrecoverableKeyException When the keystore manager factory fails to initialise
     */
    public static KeyManagerFactory getKeyManagerFactory(String keystore,
                                                         String keystorePassword,
                                                         String keystoreType,
                                                         String algorithm) throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException, UnrecoverableKeyException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(Optional.ofNullable(algorithm).orElse(TrustManagerFactory.getDefaultAlgorithm()));
        keyManagerFactory.init(getKeyStore(keystore, keystorePassword, keystoreType), keystorePassword.toCharArray());
        return  keyManagerFactory;
    }

    /**
     * Loads the specified trust-store, using the provided algorithm type and
     * password into a TrustManagerFactory object.
     *
     * @param truststore            The location of the truststore to be loaded
     * @param truststorePassword    The password for the truststore to be loaded
     * @param truststoreType        The type of the truststore to be loaded
     * @param algorithm             The algorithm to be used for loading the truststore
     * @return the loaded TrustManagerFactory object
     * @throws KeyStoreException When the truststore loading operation fails
     * @throws NoSuchAlgorithmException  If the truststore algorithm provided does not exist
     * @throws IOException When the truststore data cannot be loaded
     * @throws CertificateException If the certificated loaded are invalid
     */
    public static TrustManagerFactory getTrustManagerFactory(String truststore,
                                                             String truststorePassword,
                                                             String truststoreType,
                                                             String algorithm) throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(Optional.ofNullable(algorithm).orElse(TrustManagerFactory.getDefaultAlgorithm()));
        trustManagerFactory.init(getKeyStore(truststore, truststorePassword, truststoreType));
        return trustManagerFactory;
    }

}
