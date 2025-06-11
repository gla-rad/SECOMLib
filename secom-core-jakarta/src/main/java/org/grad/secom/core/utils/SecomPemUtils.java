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

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The PEM Certificate Utilities Class.
 * <p/>
 * According to SECOM, during the build of a payload JSON object and before
 * adding the public key to the same object, the original key should be
 * minified into a single line string. When receiving this, the opposite
 * conversion is required. This class provides this functionality, following
 * the examples provided in SECOM.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SecomPemUtils {

    /**
     * For multiple X509 certificates, this function will create an array
     * of minified PEM objects easily.
     *
     * @param certs     The array of X509 certificates
     * @return The array of minified certificates
     * @throws CertificateEncodingException Exception when the certificate encoding is false
     */
    public static String[] getMinifiedPemFromCerts(X509Certificate[] certs) throws CertificateEncodingException {
        //Initialise the minified PEM list
        final List<String> minifiedPemArray = new ArrayList<>();
        for(X509Certificate cert: certs) {
            minifiedPemArray.add(getMinifiedPemFromCert(cert));
        }
        return minifiedPemArray.toArray(new String[]{});
    }

    /**
     * During the build of a payload JSON object and before adding the public
     * certificate to the same object, the original key is minified into one
     * single line string by:
     * <ul>
     *     <li>Remove all line feed characters</li>
     *     <li>Remove header -----BEGIN CERTIFICATE-----</li>
     *     <li>Remove footer -----END CERTIFICATE-----</li>
     * </ul>
     * <p/>
     * For Java X509 certificates, this can be easily done by just retrieving
     * the encoded bit of the certificate object, which is exactly what we want.
     *
     * @param cert  The certificate to be minified
     * @return the original key minified into a single line string
     * @throws CertificateEncodingException When the certificate encoded provided is wrong
     */
    public static String getMinifiedPemFromCert(X509Certificate cert) throws CertificateEncodingException {
        return Base64.getEncoder().encodeToString(cert.getEncoded());
    }

    /**
     * During the build of a payload JSON object and before adding the public
     * certificate to the same object, the original certificate is minified into
     * one single line string by:
     * <ul>
     *     <li>Remove all line feed characters</li>
     *     <li>Remove header -----BEGIN CERTIFICATE-----</li>
     *     <li>Remove footer -----END CERTIFICATE-----</li>
     * </ul>
     * <p/>
     * For Java Strings, we need to perform these operations manually, by
     * replacing all the sub-string matches with empty strings.
     *
     * @param cert  The certificate string to be minified
     * @return the original key minified into a single line string
     * @throws CertificateEncodingException When the certificate encoded provided is wrong
     */
    public static String getMinifiedPemFromCertString(String cert) {
        return cert
            // 1. Remove all line feed characters
            .replaceAll(System.lineSeparator(), "")
            // 2. Remove header -----BEGIN CERTIFICATE-----
            .replaceAll("-----BEGIN CERTIFICATE-----", "")
            // 3. Remove footer -----END CERTIFICATE-----
            .replaceAll("-----END CERTIFICATE-----", "");
    }

    /**
     * During the build of a payload JSON object and before adding the public
     * key to the same object, the original key is minified into one single line
     * string by:
     * <ul>
     *     <li>Remove all line feed characters</li>
     *     <li>Remove header -----BEGIN PUBLIC KEY-----</li>
     *     <li>Remove footer -----END PUBLIC KEY-----</li>
     * </ul>
     * <p/>
     * For Java Public Keys, we need to perform these operations manually, by
     * replacing all the sub-string matches with empty strings.
     *
     * @param publicKey  The public key string to be minified
     * @return the original public key minified into a single line string
     * @throws CertificateEncodingException When the certificate encoded provided is wrong
     */
    public static String getMinifiedPemFromPublicKey(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * During the build of a payload JSON object and before adding the public
     * key to the same object, the original key is minified into one single line
     * string by:
     * <ul>
     *     <li>Remove all line feed characters</li>
     *     <li>Remove header -----BEGIN PUBLIC KEY-----</li>
     *     <li>Remove footer -----END PUBLIC KEY-----</li>
     * </ul>
     * <p/>
     * For Java Strings, we need to perform these operations manually, by
     * replacing all the sub-string matches with empty strings.
     *
     * @param publicKey  The public key string to be minified
     * @return the original public key minified into a single line string
     * @throws CertificateEncodingException When the certificate encoded provided is wrong
     */
    public static String getMinifiedPemFromPublicKeyString(String publicKey) {
        return publicKey
            // 1. Remove all line feed characters
            .replaceAll(System.lineSeparator(), "")
            // 2. Remove header -----BEGIN PUBLIC KEY-----
            .replaceAll("-----BEGIN PUBLIC KEY-----", "")
            // 3. Remove footer -----END PUBLIC KEY-----
            .replaceAll("-----END PUBLIC KEY-----", "");
    }

    /**
     * For multiple minified PEM certificates, this function will create an array
     * of X509 certificate objects easily.
     *
     * @param certsMinified     The minified PEM certificates
     * @return The array of retrieved X509 certiticate objects
     * @throws CertificateException Exception when the minified PEM certificates are invalid
     */
    public static X509Certificate[] getCertsFromPem(String[] certsMinified) throws CertificateException {
        //Initialise the X509 certificate list
        final List<X509Certificate> x509CertArray = new ArrayList<>();
        for(String certMinified: certsMinified) {
            x509CertArray.add(getCertFromPem(certMinified));
        }
        return x509CertArray.toArray(new X509Certificate[]{});
    }

    /**
     * For SECOM receivers, the conversion is the opposite. When consuming the
     * transferred payload, the public key is converted back to its original
     * format.
     * <ul>
     *     <li>Add header -----BEGIN CERTIFICATE-----</li>
     *     <li>Add OS specific line feed character</li>
     *     <li>Split the public key string from the payload into an array of max 64 characters per row</li>
     *     <li>For each element in the array
     *          <ul>
     *             <li>add element as new row</li>
     *             <li>add OS specific line feed character</li>
     *         </ul>
     *     </li>
     *     <li>Add footer -----END CERTIFICATE-----</li>
     *     <li>Add OS specific line feed character</li>
     * </ul>
     * <p/>
     * This implementation, as in the official SECOM guideline, returns the
     * reconstructed X509Certificate object.
     *
     * @param certMinified The minified certificate to be converted
     * @return the original X509Certificate object
     * @throws CertificateException When a certificate cannot be restored correctly
     */
    public static X509Certificate getCertFromPem(String certMinified) throws CertificateException {
        // Do the string conversion and reconstruct the X509Certificate object
        final ByteArrayInputStream ins = new ByteArrayInputStream(getCertStringFromPem(certMinified).getBytes(StandardCharsets.UTF_8));
        return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(ins);
    }

    /**
     * For SECOM receivers, the conversion is the opposite. When consuming the
     * transferred payload, the X509 certificate is converted back to its
     * original format.
     * <ul>
     *     <li>Add header -----BEGIN CERTIFICATE-----</li>
     *     <li>Add OS specific line feed character</li>
     *     <li>Split the public key string from the payload into an array of max 64 characters per row</li>
     *     <li>For each element in the array
     *          <ul>
     *             <li>add element as new row</li>
     *             <li>add OS specific line feed character</li>
     *         </ul>
     *     </li>
     *     <li>Add footer -----END CERTIFICATE-----</li>
     *     <li>Add OS specific line feed character</li>
     * </ul>
     * <p/>
     * This implementation returns a simple string, augmented with the PEM
     * headers and line separators.
     *
     * @param certMinified  The minified certificate to be converted
     * @return the original X509 certificate PEM string representation
     */
    public static String getCertStringFromPem(String certMinified) {
        final StringBuilder sb = new StringBuilder();
        // 1. Add header -----BEGIN CERTIFICATE-----
        sb.append("-----BEGIN CERTIFICATE-----");
        // 2. Add OS specific line feed character
        sb.append(System.lineSeparator());
        // 3. Split the public key string from the payload into an array of max 64 characters per row
        Arrays.stream(certMinified.split("(?<=\\G.{64})"))
                // 4. For each element in the array
                .forEach(row -> {
                    // 4a. add element as new row
                    sb.append(row);
                    // 4b. add OS specific line feed character
                    sb.append(System.lineSeparator());
                });
        // 5. Add footer -----END CERTIFICATE-----
        sb.append("-----END CERTIFICATE-----");
        // 6. Add OS specific line feed character
        sb.append(System.lineSeparator());

        // And finally return the reconstructed string
        return sb.toString();
    }

    /**
     * For SECOM receivers, the conversion is the opposite. When consuming the
     * transferred payload, the X509 public key is converted back to its
     * original format.
     * <ul>
     *     <li>Add header -----BEGIN PUBLIC KEY-----</li>
     *     <li>Add OS specific line feed character</li>
     *     <li>Split the public key string from the payload into an array of max 64 characters per row</li>
     *     <li>For each element in the array
     *          <ul>
     *             <li>add element as new row</li>
     *             <li>add OS specific line feed character</li>
     *         </ul>
     *     </li>
     *     <li>Add footer -----END PUBLIC KEY-----</li>
     *     <li>Add OS specific line feed character</li>
     * </ul>
     * <p/>
     * This implementation, as in the official SECOM guideline, returns the
     * reconstructed PublicKey object.
     *
     * @param algorithm             The algorithm to be used for the public key factory
     * @param publicKeyMinified     The minified X509 public key to be converted
     * @return the original PublicKey object
     */
    public static PublicKey getPublicKeFromPem(String algorithm, String publicKeyMinified) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Do the Base64 conversion and reconstruct the PublicKey object
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyMinified));
        return KeyFactory.getInstance(algorithm).generatePublic(keySpec);
    }

    /**
     * For SECOM receivers, the conversion is the opposite. When consuming the
     * transferred payload, the X509 public key is converted back to its
     * original format.
     * <ul>
     *     <li>Add header -----BEGIN PUBLIC KEY-----</li>
     *     <li>Add OS specific line feed character</li>
     *     <li>Split the public key string from the payload into an array of max 64 characters per row</li>
     *     <li>For each element in the array
     *          <ul>
     *             <li>add element as new row</li>
     *             <li>add OS specific line feed character</li>
     *         </ul>
     *     </li>
     *     <li>Add footer -----END PUBLIC KEY-----</li>
     *     <li>Add OS specific line feed character</li>
     * </ul>
     * <p/>
     * This implementation returns a simple string, augmented with the PEM
     * headers and line separators.
     *
     * @param publicKeyMinified     The minified X509 public key to be converted
     * @return the original PublicKey PEM string representation
     */
    public static String getPublicKeyStringFromPem(String publicKeyMinified) {
        final StringBuilder sb = new StringBuilder();
        // 1. Add header -----BEGIN PUBLIC KEY-----
        sb.append("-----BEGIN PUBLIC KEY-----");
        // 2. Add OS specific line feed character
        sb.append(System.lineSeparator());
        // 3. Split the public key string from the payload into an array of max 64 characters per row
        Arrays.stream(publicKeyMinified.split("(?<=\\G.{64})"))
                // 4. For each element in the array
                .forEach(row -> {
                    // 4a. add element as new row
                    sb.append(row);
                    // 4b. add OS specific line feed character
                    sb.append(System.lineSeparator());
                });
        // 5. Add footer -----END PUBLIC KEY-----
        sb.append("-----END PUBLIC KEY-----");
        // 6. Add OS specific line feed character
        sb.append(System.lineSeparator());

        // And finally return the reconstructed string
        return sb.toString();
    }

    /**
     * Generate the thumbprint of a certificate.
     * <p/>
     * A certificate's thumbprint (or fingerprint) is the unique identifier of
     * the certificate. It's not part of the certificate, but it's calculated
     * from it.
     *
     * @param cert      The X.509 certificate to generate the thumbprint from
     * @return the X.509 certificate thumbprint
     * @throws NoSuchAlgorithmException When the requests hashing algorithm does not exist
     * @throws CertificateEncodingException When the encoding of the provided certificate is wrong
     */
    public static String getCertThumbprint(X509Certificate cert, String algorithm) throws NoSuchAlgorithmException, CertificateEncodingException {
        final MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(cert.getEncoded());
        final ByteBuffer byteBuffer = ByteBuffer.wrap(md.digest());
        return Stream.generate(byteBuffer::get)
                .limit(byteBuffer.capacity())
                .map(b -> String.format("%02x", b))
                .collect(Collectors.joining());
    }

}
