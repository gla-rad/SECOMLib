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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static org.junit.jupiter.api.Assertions.*;

class SecomPemUtilsTest {

    // Test Variables
    protected String resourceCertString;
    protected X509Certificate resourceCert;
    protected String resourcePublicKey;
    protected String resourceMinifiedCert;
    protected String resourceMinifiedPublicKey;

    @BeforeEach
    void init() throws CertificateException, IOException {
        // Retrieve the certificate from the resources
        final InputStream certInputStream = getClass().getClassLoader().getResourceAsStream("cert.pem");
        final CertificateFactory cf = CertificateFactory.getInstance("X.509");
        this.resourceCertString = new String(certInputStream.readAllBytes(), StandardCharsets.UTF_8);
        this.resourceCert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(this.resourceCertString.getBytes()));

        // Retrieve the public key from the resources
        final InputStream publicKeyInputStream = getClass().getClassLoader().getResourceAsStream("publicKey.pem");
        this.resourcePublicKey = new String(publicKeyInputStream.readAllBytes(), StandardCharsets.UTF_8);

        // Retrieve the minified certificate string from the resources
        final InputStream minifiedCertInputStream = getClass().getClassLoader().getResourceAsStream("minifiedCert.txt");
        this.resourceMinifiedCert = new String(minifiedCertInputStream.readAllBytes(), StandardCharsets.UTF_8);

        // Retrieve the minified public key from the resources
        final InputStream minifiedPublicKeyInputStream = getClass().getClassLoader().getResourceAsStream("minifiedPublicKey.txt");
        this.resourceMinifiedPublicKey = new String(minifiedPublicKeyInputStream.readAllBytes(), StandardCharsets.UTF_8);
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
     * Test that we can correctly minify a String PEM representation of an X.509
     * certificate public key, so that the headers and line separators are
     * removed.
     */
    @Test
    void testGetMinifiedPemFromPublicKeyString() {
        // Minify the certificate
        final String minifiedPemFromPublicKey = SecomPemUtils.getMinifiedPemFromPublicKeyString(this.resourcePublicKey);

        // Assert that it's correct
        assertNotNull(minifiedPemFromPublicKey);
        assertEquals(this.resourceMinifiedPublicKey, minifiedPemFromPublicKey);
    }

    /**
     * Test that we can reconstruct an X509Certificate object from the provided
     * minified PEM version.
     * @throws CertificateException
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
    void testGetCertFromPemString() throws CertificateException {
        // Restore the minified public key to an X.509 PEM String
        final String stringCertFromPem = SecomPemUtils.getCertStringFromPem(this.resourceMinifiedCert);

        // Assert that it's correct
        assertNotNull(stringCertFromPem);
        assertEquals(this.resourceCertString, stringCertFromPem);
    }

    /**
     * Test that we can generate the thumbprint of an X.509  certificate
     * correclty.
     *
     * @throws CertificateEncodingException When the certificate encoding is wrong
     * @throws NoSuchAlgorithmException When the provided thumbprint argorithm does not exist
     */
    @Test
    void testGetCertThumbprint() throws CertificateEncodingException, NoSuchAlgorithmException {
        // Get the thumbprint
        final String thumbprint = SecomPemUtils.getCertThumbprint(this.resourceCert, "SHA-1");

        // Assert it's correct
        assertNotNull(thumbprint);
        assertEquals("4328a4dc335c1ce73d8d8cdce4ed5afa8c1caa53", thumbprint);
    }

}