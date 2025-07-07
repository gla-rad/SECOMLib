# SECOMLib

A Java library facilitating the development of SECOM-compliant web interfaces.
The implementation is based on the final IEC 63173-2 ED1 SECOM draft with
circulation date 2022-03-11.

## Development Setup

To start developing just open the repository with the IDE of your choice. The
original code has been generated using
[Intellij IDEA](https://www.jetbrains.com/idea). Just open it by going to:

    File -> New -> Project From Verson Control

Provide the URL of the current repository and the local directory you want.

You don't have to use it if you have another preference. Just make sure you
update the *.gitignore* file appropriately.

## Description

A SECOM-compliant service endpoints is quite standardised so a simple library
with the required data structures and endpoint definitions should be very handy
while developing these types of services. The first two digits of the version
of the library should follow the implemented version of the SECOM standard to
keep things tidy. For example, if the SECOM standard in at version 1.0 then the
library version should be at 1.0.X. The use of snapshots during the development
is also strongly encouraged.

## Structure

The library is built using Maven and is structured in different Maven modules
to make importing easier. For the time being the following modules have been
defined:

* secom-core
* secom-core-jakarta
* secom-springboot2
* secom-springboot3

As you can see there is native support for both Javax and Jakarta namespaces,
as well as support for both versions 2.0 and 3.0 of Springboot, at least for
the time being. It is strongly suggested to always use the latest version, as
support for Javax and the earlier Sprinboot versions might be dropped at any
point.

### The Core Module

The core functionality of the SECOM library is found under the **secom-core**
module. To keep the library as generic as possible and compatible with the widest
range of implementation frameworks, the
[JAX-RS](https://www.baeldung.com/jax-rs-spec-and-implementations) API is being
followed. This allows the additional definition of a set of JAX-RS provider
components that hook up on runtime and perform the SECOM message data payload
encryption, compression and encoding automatically. The library is also able to
automatically generate the message signatures when required by SECOM.

Having that in mind, the core package is structured in the following packages:

* **base**: Contains a set of basic classes and interfaces providing the basic
  background for the SECOM class implementations.
* **components**: Contains mainly a set the JAX-TS providers utilised to
  provide the signature generation mechanism, encoding, compression, encryption,
  exception handling and standardised field conversions.
* **exceptions**: Contains a list of all the exceptions defined by SECOM
* **interfaces**: Contains the definition of all SECOM interfaces as Java
  interfaces.
* **models**: Contains the definition of all the SECOM message classes and
  sub-classes.
* **utils**: Contains a set of static utility functions, mainly for loading and
  manipulating the security information (certificates, keys, etc.) used in
  SECOM.

### The Springboot Module

The whole idea behind SECOM is to standardise the implementation of digital
services in the maritime context, the widest number of implementation
frameworks should be supported. One of the most popular methods of implementing
service in Java is the Springboot framework. Therefore, this module provides
the ability of introducing the library into existing Springboot projects as
a simple Maven dependency. This can be achieved using the
[resteasy-spring-boot](https://github.com/resteasy/resteasy-spring-boot) starter
library.

The module is activated using the **JaxrsApplication** configuration and it
also provides a SECOM client implementation using the Springboot
[WebClient](https://www.baeldung.com/spring-5-webclient) utility. The client is
able to perform all calls described by SECOM onto an existing SECOM-compliant
service and returns the retrieved responses. The configuration of the client is
achieved via the **SecomConfigProperties** class. This class allows access to
the application's *application.properties* file.

## How to Use

For the time being, the library has only been tested on Springboot applications
and is compatible with Springboot version 2.5+. There is no reason however that
the core module won't be compatible with other frameworks such as the Redhat
[Quarkus](https://www.redhat.com/en/topics/cloud-native-apps/what-is-quarkus).

The rest of this section is assuming a Springboot use of the library.

### Importing the Library

First you will need to import the appropriate dependencies in your **pom.xml**.
The jars at the moment are available via the GRAD Nexus repository manager. To
access is add the following repositories in your pom.xml

```xml
<repository>
    <id>grad</id>
    <url>https://rnavlab.gla-rad.org/mvn/repository/grad/</url>
</repository>
<repository>
    <id>grad-snapshots</id>
    <url>https://rnavlab.gla-rad.org/mvn/repository/grad-snapshots/</url>
    <snapshots>
        <enabled>true</enabled>
        <updatePolicy>always</updatePolicy>
    </snapshots>
</repository>
```

Then you can add the following repositories:

```xml
<!-- SECOM -->
<dependency>
    <groupId>org.grad.secom</groupId>
    <artifactId>secom-core-jakarta</artifactId>
    <version>0.0.24</version>
</dependency>
<dependency>
    <groupId>org.grad.secom</groupId>
    <artifactId>secom-springboot3</artifactId>
    <version>0.0.24</version>
</dependency>
```

Once the core and springboot modules have been imported, the JAX-RS application
will be automatically picked up by Springboot. This means that the signature
generation and exception handing will now work out-of-the-box.

### Interface Definition

The library does allow some out-of-the-box functionality, but as expected the
actual interface implementation is left to the developer of each service. To
simplify this process however, the SECOM interfaces are already pre-defined as
Java interfaces that are left to be implemented. For example to implement tha
SECOM capability interface all you need to do is to create a new JAX-RS
interface that implements the **CapabilitySecomInterface** class.

```java
/**
 * The SECOM Capability Interface Controller.
 */
@Component
@Path("/") // <-- This should be the root path of the SECOM interfaces
@Validated
@Slf4j
public class CapabilitySecomController implements CapabilitySecomInterface {

    /**
     * GET /v1/capability : Returns the service instance capabilities.
     *
     * @return the SECOM-compliant service capabilities
     */
    @Tag(name = "SECOM")
    public CapabilityResponseObject capability() {
        // Populate the implemented SECOM interfaces
        ImplementedInterfaces implementedInterfaces = new ImplementedInterfaces();
        implementedInterfaces.setGetSummary(true);

        // Start building the capability entry
        CapabilityObject capabilityObject = new CapabilityObject();
        capabilityObject.setContainerType(ContainerTypeEnum.S100_DataSet);
        capabilityObject.setDataProductType(SECOM_DataProductType.S125);
        capabilityObject.setImplementedInterfaces(implementedInterfaces);

        // Start building the capability response
        CapabilityResponseObject capabilityResponseObject = new CapabilityResponseObject();

        capabilityResponseObject.setCapability(Collections.singletonList(capabilityObject));
        capabilityObject.setServiceVersion(this.appVersion);

        // And return the Capability Response Object
        return capabilityResponseObject;
    }
}
```

As you can see, all the required messages (in this case the
*CapabilityResponseObject*) are already provided, and the interface does not
even need to define the SECOM-compliant path for the capability interface. In
addition, all exception handing takes place in the background. If additional
errors are to be handled, simply raise an appropriate SECOM exception.

### SECOM Functionality Customisation

The actual implementations of the signature generation, the encryption and the
compression operations are left to the application developer. This is achieved
via a set of Java interfaces called **providers**, which are not to be confused
with the JAX-RS @Provider classes. These are just interfaces embedded into the
SECOM library operation that provide the required functionality. Here is a list
of the available provider classes:

* **SecomCertificateProvider**: This interface is mainly used during the
  signature generation operation to provide the certificate with which a
  SECOM message is to be signed.
* **SecomTrustStoreProvider**: This interface is used during the SECOM message
  validation operation to verify that a SECOM message is received by a trusted
  source.
* **SecomSignatureProvider**: This interface provides the main functionality
  for generating the signatures, based on the provided certificate and the
  data payload available.
* **SecomEncryptionProvider**: This interface provides the main functionality
  for encrypting the available data payload according to the SECOM standard.
* **SecomCompressionProvider**: This interface provides the main functionality
  for compressing the available data payload according to the SECOM standard.

The implementation of these providers is pretty self-explanatory and the
definition of each will introduce the respective functionality onto the SECOM
service implementation. For example, if a *SecomCompressionProvider* is
defined, then the library will produce compressed data.

As expected, the provision of the *SecomSignatureProvider* is mandatory for
SECOM to operate successfully, whether a provider or consumer of information. A
simple example for a SECOM client is the following:

```java
/**
 * A SECOM Signature Provider Implementation.
 */
@Component
@Slf4j
public class SecomSignatureProviderImpl implements SecomSignatureProvider {

    // TODO: Define a private key
    PrivateKey privateKey;

    /**
     * This function overrides the interface definition to provide the SECOM
     * signature provision operation. This can ve done locally, or by asking
     * another microservice to perform the actual singing for us.
     *
     * @param signatureCertificate  The digital signature certificate to be used for the signature generation
     * @param algorithm             The algorithm to be used for the signature generation
     * @param payload               The payload to be signed, (preferably Base64 encoded)
     * @return The signature generated
     */
    @Override
    public byte[] generateSignature(DigitalSignatureCertificate signatureCertificate, DigitalSignatureAlgorithmEnum algorithm, byte[] payload) {
        // Create a new signature to sign the provided content
        try {
            Signature sign = Signature.getInstance(algorithm.getValue());
            sign.initSign(this.privateKey);
            sign.update(payload);

            // Sign and return the signature
            return sign.sign();
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * The signature validation operation. This should support the provision
     * of the message content (preferably in a Base64 format) and the signature
     * to validate the content against.
     *
     * @param signatureCertificate  The digital signature certificate to be used for the signature generation
     * @param algorithm             The algorithm used for the signature generation
     * @param signature             The signature to validate the context against
     * @param content               The context (in Base64 format) to be validated
     * @return whether the signature validation was successful or not
     */
    @Override
    public boolean validateSignature(String signatureCertificate, DigitalSignatureAlgorithmEnum algorithm, byte[] signature, byte[] content) {
        // Create a new signature to sign the provided content
        try {
            Signature sign = Signature.getInstance(algorithm.getValue());
            sign.initVerify(SecomPemUtils.getCertFromPem(signatureCertificate));
            sign.update(content);

            // Sign and return the signature
            return sign.verify(signature);
        } catch (NoSuchAlgorithmException | CertificateException | SignatureException | InvalidKeyException ex) {
            log.error(ex.getMessage());
            return false;
        }
    }
}
```

Another example could be an implementation for a **SecomCompressionProvider**:

```java
/**
 * A SECOM Compression Provider Implementation.
 */
@Component
@Slf4j
public class SecomCompressionProviderImpl implements SecomCompressionProvider {

    /**
     * A simple implementation of compressing the SECOM payload data using
     * the standard GZIP method, as suggested by the SECOM standard.
     *
     * @param compressionAlgorithmEnum  The algorithm to be used for the compression operation
     * @param bytes                      The payload to be compressed
     * @return the compressed payload
     */
    @Override
    public byte[] compress(CompressionAlgorithmEnum compressionAlgorithmEnum, byte[] bytes) {
        final byte[] compressed;
        if (compressionAlgorithmEnum == CompressionAlgorithmEnum.ZIP && !isCompressed(bytes)) {
            if ((bytes == null) || (bytes.length == 0)) {
                return null;
            }
            try {
                final ByteArrayOutputStream obj = new ByteArrayOutputStream();
                final GZIPOutputStream gzip = new GZIPOutputStream(obj);
                gzip.write(bytes);
                gzip.flush();
                gzip.close();
                compressed = obj.toByteArray();
            } catch (IOException ex) {
                throw new SecomGenericException(ex.getMessage());
            }
        } else {
            compressed = bytes;
        }
        return compressed;
    }

    /**
     * A simple implementation of decompressing the SECOM payload data using
     * the standard GZIP method, as suggested by the SECOM standard.
     *
     * @param compressionAlgorithmEnum  The algorithm used for the compression
     * @param bytes                     The compressed data
     * @return the decompressed payload
     */
    @Override
    public byte[] decompress(CompressionAlgorithmEnum compressionAlgorithmEnum, byte[] bytes) {
        final byte[] decompressed;
        if (compressionAlgorithmEnum == CompressionAlgorithmEnum.ZIP && isCompressed(bytes)) {
            if ((bytes == null) || (bytes.length == 0)) {
                return null;
            }
            try {
                final ByteArrayInputStream obj = new ByteArrayInputStream(bytes);
                final GZIPInputStream gis = new GZIPInputStream(obj);
                decompressed = gis.readAllBytes();
            } catch (IOException ex) {
                throw new SecomGenericException(ex.getMessage());
            }
        } else {
            decompressed = bytes;
        }
        return decompressed;
    }

    private boolean isCompressed(final byte[] compressed) {
        return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }
}
```

### SECOM Client Configuration

As mentioned previously, the **application.properties** file can be used to
provide some configuration details to the SECOM client. An example of a correct
configuration is the following:

```properties
# SECOM Configuration Properties
secom.security.ssl.keystore=keystore.jks
secom.security.ssl.keystore-type=jks
secom.security.ssl.keystore-password=<KEYSTORE-PASSWORD>
secom.security.ssl.truststore=truststore.jks
secom.security.ssl.truststore-type=jks
secom.security.ssl.truststore-password=<TRUSTORE-PASSWORD>
secom.security.ssl.insecureSslPolicy=false
```

This allows the client to access a keystore (pkcs12 and jks formats supported)
in order to provide a certificate to the called service, validate the received
service certificate via a truststore (pkcs12 and jks formats supported), or
allow for an insecure policy where all certificates are accepted.

### OpenAPI Specification

Now The library also supports the generation of an OpenAPI JSON file out of
the box, using the swagger library. To achieve this a special endpoint is
loaded onto Springboot service udner the path ***/v2/openapi.json***.

The implementing services can append the OpenAPI information using another
supplied provider, namely the **SecomV2OpenApiInfoProvider**. Here is an example
of the operarion:

```java
@Component
public class SecomV2OpenApiInfoProviderImpl implements SecomV2OpenApiInfoProvider {

    /**
     * Returns the OpenAPI documentation details.
     *
     * @return The OpenAPI documentation details
     */
    @Override
    public OpenAPI getOpenApiInfo() {
        return new OpenAPI().schema("secom-v1", new Schema<>().$schema("openapi.json"))
                .info(new Info().title("My Service - SECOM v1.0 Interfaces")
                        .description("The SECOM interfaces of the My Service")
                        .termsOfService("https://test.org/")
                        .version("v0.0.1")
                        .contact(new Contact().email("name@test.org"))
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(Arrays.asList(new Server[]{
                        new Server().url("http://localhost:8766/api/secom2")
                }))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }
}
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first to
discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

Distributed under the Apache License. See [LICENSE](./LICENSE) for more
information.

## Contact

Nikolaos Vastardis - Nikolaos.Vastardis@gla-rad.org
