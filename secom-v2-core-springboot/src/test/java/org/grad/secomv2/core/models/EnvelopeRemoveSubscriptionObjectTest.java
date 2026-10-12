package org.grad.secomv2.core.models;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopeRemoveSubscriptionObjectTest {

    // Class Variables
    private EnvelopeRemoveSubscriptionObject obj;
    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Set up an object mapper
        this.mapper = new ObjectMapper();

        // Generate a new object
        this.obj = new EnvelopeRemoveSubscriptionObject();
        this.obj.setSubscriptionIdentifier(UUID.randomUUID());
        this.obj.setEnvelopeSignatureCertificate(new String[]{"envelopeCertificate"});
        this.obj.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.obj.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JacksonException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EnvelopeRemoveSubscriptionObject result = this.mapper.readValue(jsonString, EnvelopeRemoveSubscriptionObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getSubscriptionIdentifier(), result.getSubscriptionIdentifier());
        assertArrayEquals(this.obj.getEnvelopeSignatureCertificate(), result.getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), result.getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getEnvelopeSignatureTime(), result.getEnvelopeSignatureTime());
    }

    /**
     * Test that we can correctly generate the SECOM signature CSV.
     */
    @Test
    void testGetCsvString() {
        // Generate the signature CSV
        String signatureCSV = this.obj.getCsvString();

        // Match the individual entries of the string
        String[] csv = signatureCSV.split("\\.");
        assertEquals(this.obj.getSubscriptionIdentifier().toString(), csv[0]);
        assertEquals(String.join(",", this.obj.getEnvelopeSignatureCertificate()), csv[1]);
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), csv[2]);
        assertEquals(String.valueOf(this.obj.getEnvelopeSignatureTime().getEpochSecond()), csv[3]);
    }

    /**
     * Test that obj extends AbstractEnvelope
     */
    @Test
    void testObjExtendsAbstractEnvelope() {
        assertTrue(AbstractEnvelope.class.isAssignableFrom(this.obj.getClass()));
    }
}
