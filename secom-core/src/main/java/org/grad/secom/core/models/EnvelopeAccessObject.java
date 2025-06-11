package org.grad.secom.core.models;

import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.ReasonEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * The SECOM Envelope Access Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class EnvelopeAccessObject extends AbstractEnvelope {

    // Class Variables
    @NotNull
    private String reason;
    @NotNull
    private ReasonEnum reasonEnum;
    private ContainerTypeEnum containerType;
    private SECOM_DataProductType dataProductType;
    private UUID dataReference;
    private String productVersion;
    private String callbackEndpoint;

    /**
     * Gets reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets reason.
     *
     * @param reason the reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Gets reason enum.
     *
     * @return the reason enum
     */
    public ReasonEnum getReasonEnum() {
        return reasonEnum;
    }

    /**
     * Sets reason enum.
     *
     * @param reasonEnum the reason enum
     */
    public void setReasonEnum(ReasonEnum reasonEnum) {
        this.reasonEnum = reasonEnum;
    }

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
     * Gets data reference.
     *
     * @return the data reference
     */
    public UUID getDataReference() {
        return dataReference;
    }

    /**
     * Sets data reference.
     *
     * @param dataReference the data reference
     */
    public void setDataReference(UUID dataReference) {
        this.dataReference = dataReference;
    }

    /**
     * Gets product version.
     *
     * @return the product version
     */
    public String getProductVersion() {
        return productVersion;
    }

    /**
     * Sets product version.
     *
     * @param productVersion the product version
     */
    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    /**
     * Gets callback endpoint.
     *
     * @return the callback endpoint
     */
    public String getCallbackEndpoint() {
        return callbackEndpoint;
    }

    /**
     * Sets callback endpoint.
     *
     * @param callbackEndpoint the callback endpoint
     */
    public void setCallbackEndpoint(String callbackEndpoint) {
        this.callbackEndpoint = callbackEndpoint;
    }

    /**
     * This method should be implemented by all envelop objects to allow the
     * generation of the signature CSV attribute array
     *
     * @return the generated signature CSV attribute array
     */
    @Override
    public Object[] getAttributeArray() {
        return new Object[] {
                reason,
                reasonEnum,
                containerType,
                dataProductType,
                dataReference,
                productVersion,
                callbackEndpoint,
                envelopeSignatureCertificate,
                envelopeRootCertificateThumbprint,
                envelopeSignatureTime,
                digitalSignatureReference
        };
    }
}
