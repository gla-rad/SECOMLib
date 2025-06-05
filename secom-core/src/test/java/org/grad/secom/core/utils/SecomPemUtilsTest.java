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

import org.grad.secom.core.base.SecomConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class SecomPemUtilsTest {

    // Test Variables
    protected String resourceCertString;
    protected X509Certificate resourceCert;
    protected String resourcePublicKeyString;
    protected PublicKey resourcePublicKey;
    protected String resourceMinifiedCert;
    protected String resourceMinifiedPublicKey;

    @BeforeEach
    void init() throws CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        // Initialise a certificate factory
        final CertificateFactory cf = CertificateFactory.getInstance("X.509");
        final KeyFactory kf = KeyFactory.getInstance("RSA");

        // Retrieve the certificate from the resources
        final InputStream certInputStream = getClass().getClassLoader().getResourceAsStream("cert.pem");
        this.resourceCertString = new String(certInputStream.readAllBytes(), StandardCharsets.UTF_8).replaceAll("\n",System.lineSeparator());
        this.resourceCert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(this.resourceCertString.getBytes()));

        // Retrieve the public key from the resources
        final InputStream publicKeyInputStream = getClass().getClassLoader().getResourceAsStream("publicKey.pem");
        this.resourcePublicKeyString = new String(publicKeyInputStream.readAllBytes(), StandardCharsets.UTF_8).replaceAll("\n",System.lineSeparator());

        // Retrieve the minified certificate string from the resources
        final InputStream minifiedCertInputStream = getClass().getClassLoader().getResourceAsStream("minifiedCert.txt");
        this.resourceMinifiedCert = new String(minifiedCertInputStream.readAllBytes(), StandardCharsets.UTF_8);

        // Retrieve the minified public key from the resources
        final InputStream minifiedPublicKeyInputStream = getClass().getClassLoader().getResourceAsStream("minifiedPublicKey.txt");
        this.resourceMinifiedPublicKey = new String(minifiedPublicKeyInputStream.readAllBytes(), StandardCharsets.UTF_8);
        this.resourcePublicKey = kf.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(resourceMinifiedPublicKey)));
    }

    /**
     * Test that we can correctly minify an X509Certificate object, so the
     * headers and line separators are removed.
     *
     * @throws CertificateException
     */
    @Test
    void testGetMinifiedPemFromCert() throws CertificateException {
        // Minify the certificate
        final String minifiedPemFromCert = SecomPemUtils.getMinifiedPemFromCert(this.resourceCert);

        // Assert that it's correct
        assertNotNull(minifiedPemFromCert);
        assertEquals(this.resourceMinifiedCert, minifiedPemFromCert);
    }

    /**
     * Test that we can correctly minify a String PEM representation of an X.509
     * certificate, so that the headers and line separators are removed.
     */
    @Test
    void testGetMinifiedPemFromCertString() {
        // Minify the certificate
        final String minifiedPemFromCert = SecomPemUtils.getMinifiedPemFromCertString(this.resourceCertString);

        // Assert that it's correct
        assertNotNull(minifiedPemFromCert);
        assertEquals(this.resourceMinifiedCert, minifiedPemFromCert);
    }

    /**
     * Test that we can correctly minify a PublicKey object of an X.509
     * certificate, so that the headers and line separators are removed.
     */
    @Test
    void testGetMinifiedPemFromPublicKey() {
        // Minify the certificate
        final String minifiedPemFromPublicKey = SecomPemUtils.getMinifiedPemFromPublicKey(this.resourcePublicKey);

        // Assert that it's correct
        assertNotNull(minifiedPemFromPublicKey);
        assertEquals(this.resourceMinifiedPublicKey, minifiedPemFromPublicKey);
    }

    /**
     * Test that we can correctly minify a String PEM representation of an X.509
     * certificate public key, so that the headers and line separators are
     * removed.
     */
    @Test
    void testGetMinifiedPemFromPublicKeyString() {
        // Minify the certificate
        final String minifiedPemFromPublicKey = SecomPemUtils.getMinifiedPemFromPublicKeyString(this.resourcePublicKeyString);

        // Assert that it's correct
        assertNotNull(minifiedPemFromPublicKey);
        assertEquals(this.resourceMinifiedPublicKey, minifiedPemFromPublicKey);
    }

    /**
     * Test that we can reconstruct an X509Certificate object from the provided
     * minified PEM version.
     */
    @Test
    void testGetCertFromPem() throws CertificateException {
        // Restore the minified public key to an X.509 certificate
        final X509Certificate certFromPem = SecomPemUtils.getCertFromPem(this.resourceMinifiedCert);

        // Assert that it's correct
        assertNotNull(certFromPem);
        assertEquals(this.resourceCert.getSerialNumber(), certFromPem.getSerialNumber());
        assertEquals(this.resourceCert.getNotAfter(), certFromPem.getNotAfter());
        assertEquals(this.resourceCert.getNotBefore(), certFromPem.getNotBefore());
        assertEquals(this.resourceCert.getSigAlgName(), certFromPem.getSigAlgName());
        assertEquals(this.resourceCert.getSigAlgOID(), certFromPem.getSigAlgOID());

        // Also validate the signature to be extra safe
        for(int i=0; i<certFromPem.getSignature().length; i++) {
            assertEquals(this.resourceCert.getSignature()[i], certFromPem.getSignature()[i]);
        }
    }

    /**
     * Test that we can reconstruct a PEM String representation of an X.509
     * certificate from the provided minified PEM version.
     */
    @Test
    void testGetCertStringFromPem() {
        // Restore the minified certificate to an X.509 PEM String
        final String stringCertFromPem = SecomPemUtils.getCertStringFromPem(this.resourceMinifiedCert);

        // Assert that it's correct
        assertNotNull(stringCertFromPem);
        assertEquals(this.resourceCertString, stringCertFromPem);
    }

    /**
     * Test that we can reconstruct an PublicKey object from the provided
     * minified PEM version.
     */
    @Test
    void testGetPublicKeyFromPem() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Restore the minified public key to an X.509 PEM String
        final PublicKey publicKeyFromPem = SecomPemUtils.getPublicKeFromPem("RSA", this.resourceMinifiedPublicKey);

        // Assert that it's correct
        assertNotNull(publicKeyFromPem);
        assertEquals(this.resourcePublicKey, publicKeyFromPem);
    }

    /**
     * Test that we can reconstruct a PEM String representation of an X.509
     * public key from the provided minified PEM version.
     */
    @Test
    void testGetPublicKeyStringFromPem() {
        // Restore the minified public key to an X.509 PEM String
        final String stringPublicKeyFromPem = SecomPemUtils.getPublicKeyStringFromPem(this.resourceMinifiedPublicKey);

        // Assert that it's correct
        assertNotNull(stringPublicKeyFromPem);
        assertEquals(this.resourcePublicKeyString, stringPublicKeyFromPem);
    }

    /**
     * Test that we can generate the thumbprint of an X.509  certificate
     * correctly.
     *
     * @throws CertificateEncodingException When the certificate encoding is wrong
     * @throws NoSuchAlgorithmException When the provided thumbprint argorithm does not exist
     */
    @Test
    void testGetCertThumbprint() throws CertificateEncodingException, NoSuchAlgorithmException {
        // Get the thumbprint
        final String thumbprint = SecomPemUtils.getCertThumbprint(this.resourceCert, SecomConstants.CERTIFICATE_THUMBPRINT_HASH);

        // Assert it's correct
        assertNotNull(thumbprint);
        assertEquals("7f0d6f9f66c0cde0963f4996992b74a45f1dd605ab20998d14f9154a51d90eb0", thumbprint);
    }

}