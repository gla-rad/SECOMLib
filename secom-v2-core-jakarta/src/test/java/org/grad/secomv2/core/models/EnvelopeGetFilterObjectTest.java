package org.grad.secomv2.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.grad.secomv2.core.models.enums.ContainerTypeEnum;
import org.grad.secomv2.core.models.enums.SECOM_DataProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnvelopeGetFilterObjectTest {
    // Class Variables
    private DigitalSignatureValueObject digitalSignatureValueObject;
    private EnvelopeGetFilterObject obj;
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
        this.obj = new EnvelopeGetFilterObject();
        this.obj.setDataReference(UUID.randomUUID());
        this.obj.setContainerType(ContainerTypeEnum.S100_DataSet);
        this.obj.setDataProductType(SECOM_DataProductType.S101);
        this.obj.setProductVersion("version");
        this.obj.setGeometry("geometry");
        this.obj.setUnlocode("unlocode");
        this.obj.setValidFrom(Instant.now().minus(1, ChronoUnit.DAYS));
        this.obj.setValidTo(Instant.now().plus(1, ChronoUnit.DAYS));
        this.obj.setPage(0);
        this.obj.setPageSize(10);
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
        EnvelopeGetFilterObject result = this.mapper.readValue(jsonString, EnvelopeGetFilterObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertEquals(this.obj.getDataReference(), this.obj.getDataReference());
        assertEquals(this.obj.getContainerType(), this.obj.getContainerType());
        assertEquals(this.obj.getDataProductType(), result.getDataProductType());
        assertEquals(this.obj.getProductVersion(), result.getProductVersion());
        assertEquals(this.obj.getGeometry(), result.getGeometry());
        assertEquals(this.obj.getUnlocode(), result.getUnlocode());
        assertEquals(this.obj.getValidFrom(), result.getValidFrom());
        assertEquals(this.obj.getValidTo(), result.getValidTo());
        assertEquals(this.obj.getPage(), result.getPage());
        assertEquals(this.obj.getPageSize(), result.getPageSize());
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
        assertEquals(this.obj.getDataReference().toString(), csv[0]);
        assertEquals(String.valueOf(this.obj.getContainerType().getValue()), csv[1]);
        assertEquals(this.obj.getDataProductType().getValue(), csv[2]);
        assertEquals(this.obj.getProductVersion(), csv[3]);
        assertEquals(this.obj.getGeometry(), csv[4]);
        assertEquals(this.obj.getUnlocode(), csv[5]);
        assertEquals(String.valueOf(this.obj.getValidFrom().getEpochSecond()), csv[6]);
        assertEquals(String.valueOf(this.obj.getValidTo().getEpochSecond()), csv[7]);
        assertEquals(this.obj.getPage().toString(), csv[8]);
        assertEquals(this.obj.getPageSize().toString(), csv[9]);
        assertEquals(Arrays.toString(this.obj.getEnvelopeSignatureCertificate()), csv[10]);
        assertEquals(this.obj.getEnvelopeRootCertificateThumbprint(), csv[11]);
        assertEquals(String.valueOf(this.obj.getEnvelopeSignatureTime().getEpochSecond()), csv[12]);
    }

    /**
     * Test that obj extends AbstractEnvelope
     */
    @Test
    void testObjExtendsAbstractEnvelope() {
        assertInstanceOf(AbstractEnvelope.class, this.obj);
    }

}