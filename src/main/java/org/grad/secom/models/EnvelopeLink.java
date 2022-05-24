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

import org.grad.secom.models.enums.HashTypeEnum;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * The SECOM Envelope Link Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeLink {

    // Class Variables
    @NotNull
    private String transactionIdentifier;
    @NotNull
    private LocalDateTime timeToLive;
    @NotNull
    private ServiceExchangeMetadata exchangeMetadata;
    @NotNull
    private Integer size;
    @NotNull
    private HashTypeEnum envelopeSignatureHashType;
    @NotNull
    private String envelopeSignatureCertificate;

    /**
     * Gets transaction identifier.
     *
     * @return the transaction identifier
     */
    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    /**
     * Sets transaction identifier.
     *
     * @param transactionIdentifier the transaction identifier
     */
    public void setTransactionIdentifier(String transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }

    /**
     * Gets time to live.
     *
     * @return the time to live
     */
    public LocalDateTime getTimeToLive() {
        return timeToLive;
    }

    /**
     * Sets time to live.
     *
     * @param timeToLive the time to live
     */
    public void setTimeToLive(LocalDateTime timeToLive) {
        this.timeToLive = timeToLive;
    }

    /**
     * Gets exchange metadata.
     *
     * @return the exchange metadata
     */
    public ServiceExchangeMetadata getExchangeMetadata() {
        return exchangeMetadata;
    }

    /**
     * Sets exchange metadata.
     *
     * @param exchangeMetadata the exchange metadata
     */
    public void setExchangeMetadata(ServiceExchangeMetadata exchangeMetadata) {
        this.exchangeMetadata = exchangeMetadata;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Gets envelope signature hash type.
     *
     * @return the envelope signature hash type
     */
    public HashTypeEnum getEnvelopeSignatureHashType() {
        return envelopeSignatureHashType;
    }

    /**
     * Sets envelope signature hash type.
     *
     * @param envelopeSignatureHashType the envelope signature hash type
     */
    public void setEnvelopeSignatureHashType(HashTypeEnum envelopeSignatureHashType) {
        this.envelopeSignatureHashType = envelopeSignatureHashType;
    }

    /**
     * Gets envelope signature certificate.
     *
     * @return the envelope signature certificate
     */
    public String getEnvelopeSignatureCertificate() {
        return envelopeSignatureCertificate;
    }

    /**
     * Sets envelope signature certificate.
     *
     * @param envelopeSignatureCertificate the envelope signature certificate
     */
    public void setEnvelopeSignatureCertificate(String envelopeSignatureCertificate) {
        this.envelopeSignatureCertificate = envelopeSignatureCertificate;
    }
}
