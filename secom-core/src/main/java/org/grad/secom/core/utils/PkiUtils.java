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

package org.grad.secom.core.utils;

import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.*;

/**
 * The SECOM PKI Utility Class.
 * <p>
 * This utility class contains useful functions handing the PKI operations
 * such as a certificate validation, including the revocation status.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class PkiUtils {

    /**
     * Verify a single SEVCOM X.509 certificate against trust chain in the
     * keystore. If the certificate is invalid a CertPathValidatorException is
     * thrown. Also checks certificate validity and revocation status.
     *
     * @param certificate   The certificate to verify
     * @param ks            The truststore that contains the trust chain
     * @return true if valid.
     * @throws KeyStoreException if keystore loading fails
     * @throws NoSuchAlgorithmException if PKIX initialization fails
     * @throws CertificateException if certificate cannot be loaded
     * @throws InvalidAlgorithmParameterException if keystore loading fails
     * @throws CertPathValidatorException if certificate is invalid.
     */
    public static boolean verifyCertificateChain(X509Certificate certificate, KeyStore ks)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, InvalidAlgorithmParameterException, CertPathValidatorException {
        // Create the certificate path to verify - in this case just the given certificate
        final List<Certificate> certList = Collections.singletonList(certificate);
        final CertificateFactory cf = CertificateFactory.getInstance("X.509");
        final CertPath certPath = cf.generateCertPath(certList);

        // Create validator and revocation checker
        final CertPathValidator validator = CertPathValidator.getInstance("PKIX");
        final PKIXRevocationChecker rc = (PKIXRevocationChecker)validator.getRevocationChecker();
        rc.setOptions(EnumSet.of(PKIXRevocationChecker.Option.SOFT_FAIL));

        // Configure the PKIX certificate path validator algorithm parameters
        final PKIXParameters pkixParams = new PKIXParameters(ks);
        pkixParams.addCertPathChecker(rc);
        pkixParams.setRevocationEnabled(true);

        // To check digitalSignature and keyEncipherment bits
        final X509CertSelector keyUsageSelector = new X509CertSelector();
        keyUsageSelector.setKeyUsage(new boolean[]{true, false, true});
        pkixParams.setTargetCertConstraints(keyUsageSelector);

        // Do the actual validation!
        PKIXCertPathValidatorResult pcpvr = (PKIXCertPathValidatorResult)validator.validate(certPath, pkixParams);
        return (pcpvr != null);
    }

    /**
     * This function uses the CertPathBuilder method to build the certificate
     * path, including the SECOM X.509 certificate received with this request
     * to verify that it's valid and a part of the chain that includes the
     * root certificate of the SECOM service. If the verification operation
     * fails, it will throw GeneralSecurityException.
     *
     * @param cert                  The certificate to be validated
     * @param trustedRootCerts      The list of trusted certificates
     * @param intermediateCerts     The list of intermediate certificates
     * @return true if valid
     * @throws InvalidAlgorithmParameterException if certificate store loading fails
     * @throws NoSuchAlgorithmException if PKIX initialization fails
     * @throws CertPathBuilderException if certificate is invalid.
     */
    public static boolean verifyCertificateChain(X509Certificate cert, Set<X509Certificate> trustedRootCerts, Set<X509Certificate> intermediateCerts)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, CertPathBuilderException {
        // Create the selector that specifies the starting certificate
        final X509CertSelector selector = new X509CertSelector();
        selector.setCertificate(cert);

        // Create the trust anchors (set of root CA certificates)
        final Set<TrustAnchor> trustAnchors = new HashSet<>();
        for (X509Certificate trustedRootCert : trustedRootCerts) {
            trustAnchors.add(new TrustAnchor(trustedRootCert, null));
        }

        // Specify a list of intermediate certificates
        // certificate itself has to be added to the list
        intermediateCerts.add(cert);

        // Create the certificate store from all the intermediate certificates
        final CertStore intermediateCertStore = CertStore.getInstance("Collection",
                new CollectionCertStoreParameters(intermediateCerts));

        // Build and verify the certification chain
        final CertPathBuilder builder = CertPathBuilder.getInstance("PKIX");
        PKIXRevocationChecker rc = (PKIXRevocationChecker)builder.getRevocationChecker();
        rc.setOptions(EnumSet.of(PKIXRevocationChecker.Option.SOFT_FAIL));

        // Configure the PKIX certificate builder algorithm parameters
        final PKIXBuilderParameters pkixParams = new PKIXBuilderParameters(trustAnchors, selector);
        pkixParams.addCertPathChecker(rc);
        pkixParams.setRevocationEnabled(true);
        pkixParams.addCertStore(intermediateCertStore);

        // To check digitalSignature and keyEncipherment bits
        final X509CertSelector keyUsageSelector = new X509CertSelector();
        keyUsageSelector.setKeyUsage(new boolean[]{true, false, true});
        pkixParams.setTargetCertConstraints(keyUsageSelector);

        // Do the actual validation!
        PKIXCertPathBuilderResult pcpbr = (PKIXCertPathBuilderResult) builder.build(pkixParams);
        return pcpbr != null;
    }

}
