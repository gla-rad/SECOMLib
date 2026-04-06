
package org.grad.secomv2.core.models;


import org.grad.secomv2.core.base.EnvelopeSignatureBearer;

import javax.validation.constraints.NotNull;

/**
 * {Description}
 *
 * @author Jakob Svenningsen (email: jakob@dmc.international)
 */
public class RetrieveResultObject implements EnvelopeSignatureBearer {

    @NotNull
    private EnvelopeRetrieveResultObject envelope;
    @NotNull
    private String envelopeSignature;

    /**
     * Get the envelope
     * @return envelope
     */
    public EnvelopeRetrieveResultObject getEnvelope() {
        return envelope;
    }

    /**
     * Sets the envelope
     *
     * @param envelope the envelope search filter object
     */
    public void setEnvelope(EnvelopeRetrieveResultObject envelope) {
        this.envelope = envelope;
    }

    /**
     * Gets the envelope signature
     *
     * @return envelopeSignature
     */
    public String getEnvelopeSignature() {
        return envelopeSignature;
    }

    /**
     * Sets the envelope signature
     *
     * @param envelopeSignature the envelope signature array
     */
    public void setEnvelopeSignature(String envelopeSignature) {
        this.envelopeSignature = envelopeSignature;
    }

}
