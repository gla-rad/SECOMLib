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

package org.grad.secom.v2.core.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.grad.secom.v2.core.models.enums.AckRequestEnum;
import org.grad.secom.v2.core.models.enums.DigitalSignatureAlgorithmEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GetResponseObjectTest {

    // Class Variables
    private DigitalSignatureValueObject digitalSignatureValueObject;
    private SECOM_ServiceExchangeMetadataObject exchangeMetadata;
    private DataResponseObject dataResponseObject;
    private PaginationObject paginationObject;
    private GetResponseObject obj;
    private ObjectMapper mapper;

    /**
     * Set up some base data.
     */
    @BeforeEach
    void setup() {
        //Setup an object mapper
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());

        // Create a digital signature value
        this.digitalSignatureValueObject = new DigitalSignatureValueObject();
        this.digitalSignatureValueObject.setPublicRootCertificateThumbprint("thumbprint");
        this.digitalSignatureValueObject.setPublicCertificate("certificate");
        this.digitalSignatureValueObject.setDigitalSignature("signature");

        // Create SECOM exchange metadata
        this.exchangeMetadata = new SECOM_ServiceExchangeMetadataObject();
        this.exchangeMetadata.setDataProtection(Boolean.TRUE);
        this.exchangeMetadata.setProtectionScheme("SECOM");
        this.exchangeMetadata.setDigitalSignatureReference(DigitalSignatureAlgorithmEnum.DSA);
        this.exchangeMetadata.setDigitalSignatureValue(this.digitalSignatureValueObject);
        this.exchangeMetadata.setCompressionFlag(Boolean.FALSE);

        // Create a digital signature value
        this.dataResponseObject = new DataResponseObject();
        this.dataResponseObject.setData("data".getBytes(StandardCharsets.UTF_8));
        this.dataResponseObject.setExchangeMetadata(this.exchangeMetadata);
        this.dataResponseObject.setAckRequest(AckRequestEnum.NO_ACK_REQUESTED);

        // Create a pagination object
        this.paginationObject = new PaginationObject();
        this.paginationObject.setMaxItemsPerPage(100);
        this.paginationObject.setTotalItems(999);

        // Generate a new object
        this.obj = new GetResponseObject();
        this.obj.setDataResponseObject(Collections.singletonList(this.dataResponseObject));
        this.obj.setPagination(this.paginationObject);
    }

    /**
     * Test that we can translate correctly the object onto JSON and back again.
     */
    @Test
    void testJson() throws JsonProcessingException {
        // Get the JSON format of the object
        String jsonString = this.mapper.writeValueAsString(this.obj);
        GetResponseObject result = this.mapper.readValue(jsonString, GetResponseObject.class);

        // Make sure it looks OK
        assertNotNull(result);
        assertNotNull(result.getDataResponseObject());
        assertEquals(this.obj.getDataResponseObject().size(), result.getDataResponseObject().size());
        assertEquals(new String(this.obj.getDataResponseObject().get(0).getData()), new String(result.getDataResponseObject().get(0).getData()));
        assertNotNull(result.getDataResponseObject().get(0).getExchangeMetadata());
        assertEquals(this.obj.getDataResponseObject().get(0).getExchangeMetadata().getDataProtection(), result.getDataResponseObject().get(0).getExchangeMetadata().getDataProtection());
        assertEquals(this.obj.getDataResponseObject().get(0).getExchangeMetadata().getProtectionScheme(), result.getDataResponseObject().get(0).getExchangeMetadata().getProtectionScheme());
        assertEquals(this.obj.getDataResponseObject().get(0).getExchangeMetadata().getDigitalSignatureReference(), result.getDataResponseObject().get(0).getExchangeMetadata().getDigitalSignatureReference());
        assertNotNull(result.getDataResponseObject().get(0).getExchangeMetadata().getDigitalSignatureValue());
        assertEquals(this.obj.getDataResponseObject().get(0).getExchangeMetadata().getDigitalSignatureValue().getPublicRootCertificateThumbprint(), result.getDataResponseObject().get(0).getExchangeMetadata().getDigitalSignatureValue().getPublicRootCertificateThumbprint());
        assertEquals(this.obj.getDataResponseObject().get(0).getExchangeMetadata().getDigitalSignatureValue().getPublicCertificate(), result.getDataResponseObject().get(0).getExchangeMetadata().getDigitalSignatureValue().getPublicCertificate());
        assertEquals(this.obj.getDataResponseObject().get(0).getExchangeMetadata().getDigitalSignatureValue().getDigitalSignature(), result.getDataResponseObject().get(0).getExchangeMetadata().getDigitalSignatureValue().getDigitalSignature());
        assertEquals(this.obj.getDataResponseObject().get(0).getExchangeMetadata().getCompressionFlag(), result.getDataResponseObject().get(0).getExchangeMetadata().getCompressionFlag());
        assertEquals(this.obj.getDataResponseObject().get(0).getAckRequest(), result.getDataResponseObject().get(0).getAckRequest());
        assertNotNull(result.getPagination());
        assertEquals(this.obj.getPagination().getMaxItemsPerPage(), result.getPagination().getMaxItemsPerPage());
        assertEquals(this.obj.getPagination().getTotalItems(), result.getPagination().getTotalItems());
    }

}