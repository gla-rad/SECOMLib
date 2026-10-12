package org.grad.secomv2.core.models;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopePublicKeyFilterObjectTest {

    // Class Variables
    private EnvelopePublicKeyFilterObject obj;
    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Set up an object mapper
        this.mapper = new ObjectMapper();

        // Generate a new object
        this.obj = new EnvelopePublicKeyFilterObject();
        this.obj.setCertificateThumbprint("AB12CD34EF56AB78CD90EF12AB34CD56EF78AB90");
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
        EnvelopePublicKeyFilterObject result = this.mapper.readValue(jsonString, EnvelopePublicKeyFilterObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNull(result.getDataProtection());
        assertEquals(this.obj.getCertificateThumbprint(), result.getCertificateThumbprint());
        assertArrayEquals(this.obj.getEnvelopeSignatureCertificate(), result.getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), result.getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getEnvelopeSignatureTime(), result.getEnvelopeSignatureTime());
    }

    /**
     * Test that we can correctly generate the SECOM signature CSV.
     */
    @Test
    void testGetCsvString() {
        // Use the data protection flag as well to check the boolean conversion
        this.obj.setDataProtection(Boolean.TRUE);

        // Generate the signature CSV
        String signatureCSV = this.obj.getCsvString();

        // Match the individual entries of the string
        String[] csv = signatureCSV.split("\\.");
        assertEquals("true", csv[0]);
        assertEquals(this.obj.getCertificateThumbprint(), csv[1]);
        assertEquals(String.join(",", this.obj.getEnvelopeSignatureCertificate()), csv[2]);
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), csv[3]);
        assertEquals(String.valueOf(this.obj.getEnvelopeSignatureTime().getEpochSecond()), csv[4]);
    }

    /**
     * Test that obj extends AbstractEnvelope
     */
    @Test
    void testObjExtendsAbstractEnvelope() {
        assertTrue(AbstractEnvelope.class.isAssignableFrom(this.obj.getClass()));
    }
}
