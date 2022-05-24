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

import org.grad.secom.models.enums.AckRequestEnum;
import org.grad.secom.models.enums.DataTypeEnum;
import org.grad.secom.models.enums.HashTypeEnum;

import javax.validation.constraints.NotNull;
import java.net.URL;

/**
 * The SECOM Envelope Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class Envelope {

    // Class Variables
    @NotNull
    private byte[] data;
    @NotNull
    private DataTypeEnum dataType;
    @NotNull
    private ServiceExchangeMetadata exchangeMetadata;
    private Boolean fromSubscription;
    private AckRequestEnum ackRequest;
    private URL ackEndpoint;
    @NotNull
    private String transactionIdentifier;
    @NotNull
    private HashTypeEnum envelopeSignatureHashType;
    @NotNull
    private String envelopeSignatureCertificate;

    /**
     * Get data byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Gets data type.
     *
     * @return the data type
     */
    public DataTypeEnum getDataType() {
        return dataType;
    }

    /**
     * Sets data type.
     *
     * @param dataType the data type
     */
    public void setDataType(DataTypeEnum dataType) {
        this.dataType = dataType;
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
     * Gets ack endpoint.
     *
     * @return the ack endpoint
     */
    public URL getAckEndpoint() {
        return ackEndpoint;
    }

    /**
     * Sets ack endpoint.
     *
     * @param ackEndpoint the ack endpoint
     */
    public void setAckEndpoint(URL ackEndpoint) {
        this.ackEndpoint = ackEndpoint;
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
