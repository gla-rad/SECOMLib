package org.grad.secomv2.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.grad.secomv2.core.base.EnvelopeSignatureBearer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class PublicKeyFilterObjectTest {

    // Class Variables
    private EnvelopePublicKeyFilterObject envelopePublicKeyFilterObject;
    private PublicKeyFilterObject obj;

    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Set up an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Create a new envelope public key filter object
        this.envelopePublicKeyFilterObject = new EnvelopePublicKeyFilterObject();
        this.envelopePublicKeyFilterObject.setDataProtection(Boolean.TRUE);
        this.envelopePublicKeyFilterObject.setEnvelopeSignatureCertificate(new String[]{"envelopeCertificate"});
        this.envelopePublicKeyFilterObject.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.envelopePublicKeyFilterObject.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));

        // Generate a new object
        this.obj = new PublicKeyFilterObject();
        this.obj.setEnvelope(this.envelopePublicKeyFilterObject);
        this.obj.setEnvelopeSignature("signature");
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        PublicKeyFilterObject result = this.mapper.readValue(jsonString, PublicKeyFilterObject.class);

        // Make sure the validation helper is not part of the JSON
        assertFalse(jsonString.contains("filterValid"));

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getEnvelope());
        assertEquals(this.obj.getEnvelope().getDataProtection(), result.getEnvelope().getDataProtection());
        assertEquals(this.obj.getEnvelope().getCertificateThumbprint(), result.getEnvelope().getCertificateThumbprint());
        assertArrayEquals(this.obj.getEnvelope().getEnvelopeSignatureCertificate(), result.getEnvelope().getEnvelopeSignatureCertificate());
        assertEquals(this.obj.getEnvelope().getEnvelopeRootCertificateThumbprint(), result.getEnvelope().getEnvelopeRootCertificateThumbprint());
        assertEquals(this.obj.getEnvelope().getEnvelopeSignatureTime(), result.getEnvelope().getEnvelopeSignatureTime());
        assertEquals(this.obj.getEnvelopeSignature(), result.getEnvelopeSignature());
    }

    /**
     * Test that the filter is only valid when either the dataProtection or
     * the certificateThumbprint attribute is provided, but not both.
     */
    @Test
    void testFilterValid() {
        // Only data protection
        assertTrue(this.obj.isFilterValid());

        // Both data protection and certificate thumbprint
        this.envelopePublicKeyFilterObject.setCertificateThumbprint("AB12CD34EF56AB78CD90EF12AB34CD56EF78AB90");
        assertFalse(this.obj.isFilterValid());

        // Only certificate thumbprint
        this.envelopePublicKeyFilterObject.setDataProtection(null);
        assertTrue(this.obj.isFilterValid());

        // Neither of them
        this.envelopePublicKeyFilterObject.setCertificateThumbprint(null);
        assertFalse(this.obj.isFilterValid());
    }

    /**
     * Test that obj implements EnvelopeSignatureBearer
     */
    @Test
    void testObjImplementsEnvelopeSignatureBearer() {
        assertTrue(EnvelopeSignatureBearer.class.isAssignableFrom(this.obj.getClass()));
    }

}
