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

package org.grad.secom.core.models;

import org.grad.secom.core.models.enums.AckRequestEnum;
import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * The SECOM Envelope Link Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeLinkObject {

    // Class Variables
    @NotNull
    private ContainerTypeEnum containerType;
    @NotNull
    private SECOM_DataProductType dataProductType;
    @NotNull
    private SECOM_ExchangeMetadataObject exchangeMetadata;
    @NotNull
    private Boolean fromSubscription;
    @NotNull
    private AckRequestEnum ackRequest;
    @NotNull
    private String transactionIdentifier;
    @NotNull
    private String envelopeSignatureCertificate;
    @NotNull
    private String envelopeRootCertificateThumbprint;
    @NotNull
    private Integer size;
    @NotNull
    private LocalDateTime timeToLive;
    @NotNull
    private LocalDateTime envelopeSignatureTime;

    /**
     * Gets container type.
     *
     * @return the container type
     */
    public ContainerTypeEnum getContainerType() {
        return containerType;
    }

    /**
     * Sets container type.
     *
     * @param containerType the container type
     */
    public void setContainerType(ContainerTypeEnum containerType) {
        this.containerType = containerType;
    }

    /**
     * Gets data product type.
     *
     * @return the data product type
     */
    public SECOM_DataProductType getDataProductType() {
        return dataProductType;
    }

    /**
     * Sets data product type.
     *
     * @param dataProductType the data product type
     */
    public void setDataProductType(SECOM_DataProductType dataProductType) {
        this.dataProductType = dataProductType;
    }

    /**
     * Gets exchange metadata.
     *
     * @return the exchange metadata
     */
    public SECOM_ExchangeMetadataObject getExchangeMetadata() {
        return exchangeMetadata;
    }

    /**
     * Sets exchange metadata.
     *
     * @param exchangeMetadata the exchange metadata
     */
    public void setExchangeMetadata(SECOM_ExchangeMetadataObject exchangeMetadata) {
        this.exchangeMetadata = exchangeMetadata;
    }

    /**
     * Gets from subscription.
     *
     * @return the from subscription
     */
    public Boolean getFromSubscription() {
        return fromSubscription;
    }

    /**
     * Sets from subscription.
     *
     * @param fromSubscription the from subscription
     */
    public void setFromSubscription(Boolean fromSubscription) {
        this.fromSubscription = fromSubscription;
    }

    /**
     * Gets ack request.
     *
     * @return the ack request
     */
    public AckRequestEnum getAckRequest() {
        return ackRequest;
    }

    /**
     * Sets ack request.
     *
     * @param ackRequest the ack request
     */
    public void setAckRequest(AckRequestEnum ackRequest) {
        this.ackRequest = ackRequest;
    }

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

    /**
     * Gets envelope root certificate thumbprint.
     *
     * @return the envelope root certificate thumbprint
     */
    public String getEnvelopeRootCertificateThumbprint() {
        return envelopeRootCertificateThumbprint;
    }

    /**
     * Sets envelope root certificate thumbprint.
     *
     * @param envelopeRootCertificateThumbprint the envelope root certificate thumbprint
     */
    public void setEnvelopeRootCertificateThumbprint(String envelopeRootCertificateThumbprint) {
        this.envelopeRootCertificateThumbprint = envelopeRootCertificateThumbprint;
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
     * Gets envelope signature time.
     *
     * @return the envelope signature time
     */
    public LocalDateTime getEnvelopeSignatureTime() {
        return envelopeSignatureTime;
    }

    /**
     * Sets envelope signature time.
     *
     * @param envelopeSignatureTime the envelope signature time
     */
    public void setEnvelopeSignatureTime(LocalDateTime envelopeSignatureTime) {
        this.envelopeSignatureTime = envelopeSignatureTime;
    }
}
