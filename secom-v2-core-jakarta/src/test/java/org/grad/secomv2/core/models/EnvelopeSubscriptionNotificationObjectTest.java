package org.grad.secomv2.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.grad.secomv2.core.models.enums.SubscriptionEventEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopeSubscriptionNotificationObjectTest {
    // Class Variables
    private DigitalSignatureValueObject digitalSignatureValueObject;
    private EnvelopeSubscriptionNotificationObject obj;
    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());

        // Create a digital signature value
        this.digitalSignatureValueObject = new DigitalSignatureValueObject();
        this.digitalSignatureValueObject.setPublicRootCertificateThumbprint("thumbprint");
        this.digitalSignatureValueObject.setPublicCertificate(new String[]{"certificate"});
        this.digitalSignatureValueObject.setDigitalSignature("signature");

        // Generate a new object
        this.obj = new EnvelopeSubscriptionNotificationObject();
        this.obj.setSubscriptionIdentifier(UUID.randomUUID());
        this.obj.setEventEnum(SubscriptionEventEnum.SUBSCRIPTION_CREATED);
        this.obj.setEnvelopeSignatureCertificate(new String[]{"envelopeCertificate"});
        this.obj.setEnvelopeRootCertificateThumbprint("envelopeThumbprint");
        this.obj.setEnvelopeSignatureTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        EnvelopeSubscriptionNotificationObject result = this.mapper.readValue(jsonString, EnvelopeSubscriptionNotificationObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getSubscriptionIdentifier(), result.getSubscriptionIdentifier());
        assertEquals(this.obj.getEventEnum(), result.getEventEnum());
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
        assertEquals(String.valueOf(this.obj.getEventEnum().getValue()), csv[1]);
        assertEquals(Arrays.toString(this.obj.getEnvelopeSignatureCertificate()), csv[2]);
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), csv[3]);
        assertEquals(String.valueOf(this.obj.getEnvelopeSignatureTime().getEpochSecond()), csv[4]);
    }

}