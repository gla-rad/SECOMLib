/*
 * Copyright (c) 2025 GLA Research and Development Directorate
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

import org.grad.secom.core.models.enums.ContainerTypeEnum;
import org.grad.secom.core.models.enums.SECOM_DataProductType;

import jakarta.validation.constraints.NotNull;
import java.net.URL;

/**
 * The SECOM Capability Object Class.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class CapabilityObject {

    // Class Variables
    @NotNull
    private ContainerTypeEnum containerType;
    @NotNull
    private SECOM_DataProductType dataProductType;
    @NotNull
    private URL productSchemaUrl;
    @NotNull
    private ImplementedInterfaces implementedInterfaces;
    @NotNull
    private String serviceVersion;

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
     * Gets product schema url.
     *
     * @return the product schema url
     */
    public URL getProductSchemaUrl() {
        return productSchemaUrl;
    }

    /**
     * Sets product schema url.
     *
     * @param productSchemaUrl the product schema url
     */
    public void setProductSchemaUrl(URL productSchemaUrl) {
        this.productSchemaUrl = productSchemaUrl;
    }

    /**
     * Gets implemented interfaces.
     *
     * @return the implemented interfaces
     */
    public ImplementedInterfaces getImplementedInterfaces() {
        return implementedInterfaces;
    }

    /**
     * Sets implemented interfaces.
     *
     * @param implementedInterfaces the implemented interfaces
     */
    public void setImplementedInterfaces(ImplementedInterfaces implementedInterfaces) {
        this.implementedInterfaces = implementedInterfaces;
    }

    /**
     * Gets service version.
     *
     * @return the service version
     */
    public String getServiceVersion() {
        return serviceVersion;
    }

    /**
     * Sets service version.
     *
     * @param serviceVersion the service version
     */
    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }
}
