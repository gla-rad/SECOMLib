# SECOMLib

A Java library facilitating the development of SECOM-compliant web interfaces.
The implementation found here is based on the second edition of the IEC 63173-2
SECOM standard (SECOM ed2.0), as published by the IEC.

## Development Setup

To start developing just open the repository with the IDE of your choice. The
original code has been generated using
[Intellij IDEA](https://www.jetbrains.com/idea). Just open it by going to:

    File -> New -> Project From Verson Control

Provide the URL of the current repository and the local directory you want.

You don't have to use it if you have another preference. Just make sure you
update the *.gitignore* file appropriately.

## Description

A SECOM-compliant service endpoint is quite standardised, so a simple library
with the required data structures and endpoint definitions should be very handy
while developing these types of services.

In order to allow multiple versions of the library being used simultaneously,
each SECOMLib implementation of a SECOM standard version will be packaged as
a separate set of artifacts, which include the version in the artifact name.
For example:

* SECOM v1.0 --> Packages: secom-core, secom-springboot...
* SECOM v2.0 --> Packages: secom-v2-core, secom-v2-springboot... etc.

This repository currently provides the **SECOM v2.0** (ed2.0) implementation.

## Structure

The library is built using Maven and is structured in different Maven modules
to make importing easier. For the SECOM v2.0 implementation the following
modules have been defined:

* secom-v2-core
* secom-v2-core-jakarta
* secom-v2-core-springboot
* secom-v2-springboot2
* secom-v2-springboot3
* secom-v2-springboot4

As you can see there is native support for both the Javax and the Jakarta
namespaces (via the [JAX-RS](https://www.baeldung.com/jax-rs-spec-and-implementations)
API), as well as a brand new **native Spring Web** implementation that does not
rely on JAX-RS at all. On the Springboot side, versions 2.x, 3.x and the latest
4.0 are supported. It is strongly suggested to always use the latest version, as
support for Javax and the earlier Springboot versions might be dropped at any
point.

### The Core Modules

The core functionality of the SECOM library is provided by three alternative
base modules. They all share the same package layout and provide the same set of
SECOM models, interfaces, providers, exceptions and utilities; what differs is
the underlying web API they are built against:

* **secom-v2-core**: The [JAX-RS](https://www.baeldung.com/jax-rs-spec-and-implementations)
  implementation built against the legacy `javax.ws.rs` / `javax.servlet`
  namespace.
* **secom-v2-core-jakarta**: The JAX-RS implementation built against the modern
  `jakarta.ws.rs` / `jakarta.servlet` namespace.
* **secom-v2-core-springboot**: The new base module that provides the same
  models and SECOM interfaces using the **native Spring Web** stack (e.g.
  `@RequestMapping`, `@GetMapping`, `@RestController` and `ResponseEntity`)
  instead of JAX-RS. This module *replaces* the JAX-RS core for services that
  prefer to stay entirely within the Spring ecosystem and is the foundation of
  the Springboot 4.0 support.

The JAX-RS based modules follow the JAX-RS API to keep the library as generic
and compatible with the widest possible range of implementation frameworks. This
allows the additional definition of a set of JAX-RS provider components that hook
up at runtime and perform the SECOM message data payload encryption, compression
and encoding automatically. The native Spring Web module achieves the same
functionality using the equivalent Spring MVC mechanisms. In all cases the
library is also able to automatically generate the message signatures when
required by SECOM.

Having that in mind, every core module is structured in the following packages:

* **base**: Contains a set of basic classes and interfaces providing the basic
  background for the SECOM class implementations (including the SECOM provider
  interfaces).
* **components**: Contains the provider components utilised to provide the
  signature generation mechanism, encoding, compression, encryption, exception
  handling and standardised field conversions.
* **exceptions**: Contains a list of all the exceptions defined by SECOM.
* **interfaces**: Contains the definition of all SECOM interfaces as Java
  interfaces.
* **models**: Contains the definition of all the SECOM message classes and
  sub-classes.
* **utils**: Contains a set of static utility functions, mainly for loading and
  manipulating the security information (certificates, keys, etc.) used in
  SECOM.

The `secom-v2-core-springboot` module additionally provides a **config** package
with the Spring auto-configuration glue used by the Springboot 4.0 module.

### The Springboot Modules

The whole idea behind SECOM is to standardise the implementation of digital
services in the maritime context, so the widest number of implementation
frameworks should be supported. One of the most popular methods of implementing
services in Java is the Springboot framework. Therefore, these modules provide
the ability of introducing the library into existing Springboot projects as a
simple Maven dependency. Three flavours are available:

* **secom-v2-springboot2**: Targets Springboot 2.x and builds on top of the
  JAX-RS `secom-v2-core` (javax) module. The JAX-RS endpoints are exposed using
  the [resteasy-spring-boot](https://github.com/resteasy/resteasy-spring-boot)
  starter and are activated via the **SecomV2JaxrsApplication** configuration.
* **secom-v2-springboot3**: Targets Springboot 3.x and builds on top of the
  JAX-RS `secom-v2-core-jakarta` module. As with the Springboot 2.x module it
  relies on the resteasy-spring-boot starter and the **SecomV2JaxrsApplication**
  configuration.
* **secom-v2-springboot4**: Targets the latest Springboot 4.0 and builds on top
  of the new native `secom-v2-core-springboot` module. As it uses the native
  Spring Web stack, it no longer requires the resteasy starter or a JAX-RS
  application. Instead the module is wired in automatically through Spring Boot
  auto-configuration (**SecomV2AutoConfiguration**).

All three modules also provide a SECOM client implementation using the Springboot
[WebClient](https://www.baeldung.com/spring-5-webclient) utility. The client is
able to perform all calls described by SECOM onto an existing SECOM-compliant
service and returns the retrieved responses. The configuration of the client is
achieved via the **SecomConfigProperties** class, which exposes the relevant
entries of the application's *application.properties* file.

## How to Use

The library is compatible with Springboot 2.5+ (via the Springboot 2.x and 3.x
modules) and with the latest Springboot 4.0 (via the Springboot 4.x module).
There is no reason however that the core modules won't be compatible with other
frameworks such as the Redhat
[Quarkus](https://www.redhat.com/en/topics/cloud-native-apps/what-is-quarkus).

The rest of this section assumes a Springboot use of the library.

### Importing the Library

First you will need to import the appropriate dependencies in your **pom.xml**.
The jars at the moment are available via the GRAD Nexus repository manager. To
access it add the following repositories in your pom.xml:

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

Then you can add the dependencies that match your target Springboot version.

For a modern **Springboot 3.x** (JAX-RS / Jakarta) service:

```xml
<!-- SECOM -->
<dependency>
    <groupId>org.grad.secom</groupId>
    <artifactId>secom-v2-core-jakarta</artifactId>
    <version>0.0.7</version>
</dependency>
<dependency>
    <groupId>org.grad.secom</groupId>
    <artifactId>secom-v2-springboot3</artifactId>
    <version>0.0.7</version>
</dependency>
```

For a **Springboot 4.0** (native Spring Web) service:

```xml
<!-- SECOM -->
<dependency>
    <groupId>org.grad.secom</groupId>
    <artifactId>secom-v2-core-springboot</artifactId>
    <version>0.0.7</version>
</dependency>
<dependency>
    <groupId>org.grad.secom</groupId>
    <artifactId>secom-v2-springboot4</artifactId>
    <version>0.0.7</version>
</dependency>
```

For the JAX-RS based modules (Springboot 2.x and 3.x), once the core and
springboot modules have been imported, the JAX-RS application will be
automatically picked up by Springboot through the **SecomV2JaxrsApplication**
configuration. For the native Springboot 4.0 module, the
**SecomV2AutoConfiguration** is registered automatically via Spring Boot
auto-configuration. In both cases the signature generation and exception
handling will now work out-of-the-box.

### Interface Definition

The library does allow some out-of-the-box functionality, but as expected the
actual interface implementation is left to the developer of each service. To
simplify this process however, the SECOM interfaces are already pre-defined as
Java interfaces that are left to be implemented. The interfaces also pre-define
the SECOM-compliant paths, so the implementation does not even need to declare
them. All endpoints are served under the SECOM API root (`/api/secom/`) and
versioned path (`/v2/...`), as mandated by the standard.

For a JAX-RS based service (Springboot 2.x / 3.x), to implement the SECOM
capability interface all you need to do is create a new JAX-RS controller that
implements the **CapabilityServiceInterface**:

```java
/**
 * The SECOM Capability Interface Controller.
 */
@Component
@Path("/") // <-- This should be the root path of the SECOM interfaces
@Validated
@Slf4j
public class CapabilityController implements CapabilityServiceInterface {

    /**
     * GET /v2/capability : Returns the service instance capabilities.
     *
     * @return the SECOM-compliant service capabilities
     */
    @Tag(name = "SECOM")
    public CapabilityResponseObject capability() {
        // Populate the implemented SECOM interfaces
        ImplementedInterfaces implementedInterfaces = new ImplementedInterfaces();
        implementedInterfaces.setGet(true);
        implementedInterfaces.setGetSummary(true);
        implementedInterfaces.setSubscription(true);
        
        // Start building the capability entry
        CapabilityObject capabilityObject = new CapabilityObject();
        capabilityObject.setContainerType(ContainerTypeEnum.S100_DataSet);
        capabilityObject.setDataProductType(SECOM_DataProductType.S125);
        capabilityObject.setProductSchemaUrl(productSchemaUrl);
        capabilityObject.setImplementedInterfaces(implementedInterfaces);
        
        // Start building the capability response
        CapabilityResponseObject capabilityResponseObject = new CapabilityResponseObject();
        capabilityResponseObject.setCapability(Collections.singletonList(capabilityObject));
        capabilityObject.setServiceVersion(this.serviceInformationConfig.version());
        
        // And return the Capability Response Object
        return capabilityResponseObject;
    }
}
```

For a native Springboot 4.0 service, the same interface is implemented using the
Spring Web stack. The interface methods return a `ResponseEntity` and the
controller is a standard `@RestController`:

```java
/**
 * The SECOM Capability Interface Controller.
 */
@RestController
@Validated
@Slf4j
public class CapabilityController implements CapabilityServiceInterface {

    /**
     * GET /v2/capability : Returns the service instance capabilities.
     *
     * @return the SECOM-compliant service capabilities
     */
    @Override
    @Tag(name = "SECOM")
    public ResponseEntity<CapabilityResponseObject> capability() {
        // Populate the implemented SECOM interfaces
        ImplementedInterfaces implementedInterfaces = new ImplementedInterfaces();
        implementedInterfaces.setGet(true);
        implementedInterfaces.setGetSummary(true);
        implementedInterfaces.setSubscription(true);

        // Start building the capability entry
        CapabilityObject capabilityObject = new CapabilityObject();
        capabilityObject.setContainerType(ContainerTypeEnum.S100_DataSet);
        capabilityObject.setDataProductType(SECOM_DataProductType.S125);
        capabilityObject.setProductSchemaUrl(productSchemaUrl);
        capabilityObject.setImplementedInterfaces(implementedInterfaces);

        // Start building the capability response
        CapabilityResponseObject capabilityResponseObject = new CapabilityResponseObject();
        capabilityResponseObject.setCapability(Collections.singletonList(capabilityObject));
        capabilityObject.setServiceVersion(this.serviceInformationConfig.version());

        // And return the Capability Response Object
        return ResponseEntity.ok(capabilityResponseObject);
    }
}
```

As you can see, all the required messages (in this case the
*CapabilityResponseObject*) are already provided, and the interface does not
even need to define the SECOM-compliant path for the capability interface. In
addition, all exception handling takes place in the background. If additional
errors are to be handled, simply raise an appropriate SECOM exception.

In line with the SECOM ed2.0 specification, the library provides the full set of
SECOM v2.0 interfaces, including (amongst others) the *Capability*, *Get*,
*GetSummary*, *GetByLink*, the corresponding *POST* variants, *Upload*,
*UploadLink*, *Subscription*, *RemoveSubscription*, *SubscriptionNotification*,
*Access*, *AccessNotification*, *Acknowledgement*, *EncryptionKey*,
*EncryptionKeyRequest*, *GetPublicKey*, *UploadPublicKey* and *Ping* interfaces.

### SECOM Functionality Customisation

The actual implementations of the signature generation, the encryption and the
compression operations are left to the application developer. This is achieved
via a set of Java interfaces called **providers**, which are not to be confused
with the JAX-RS @Provider classes. These are just interfaces embedded into the
SECOM library operation that provide the required functionality. Here is a list
of the available provider interfaces:

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

The library also supports the generation of an OpenAPI definition out of the box.

For the JAX-RS based modules (Springboot 2.x / 3.x), a dedicated endpoint is
loaded onto the Springboot service under the path ***/v2/openapi.json*** via the
**SecomV2OpenApiEndpoint**. For the native Springboot 4.0 module, the OpenAPI
definition is contributed through the **SecomV2OpenApiConfiguration** using the
[springdoc](https://springdoc.org/) library and exposed through the standard
springdoc / Swagger-UI endpoints.

In all cases the implementing services can append their own OpenAPI information
using the supplied **SecomV2OpenApiInfoProvider** provider. Here is an example
of the operation:

```java
@Component
public class SecomV2OpenApiInfoProviderImpl implements SecomV2OpenApiInfoProvider {

    /**
     * Returns the OpenAPI documentation details.
     *
     * @return The OpenAPI documentation details
     */
    @Override
    public OpenAPI getSecomOpenApiInfo() {
        return new OpenAPI().schema("secom-v2", new Schema<>().$schema("openapi.json"))
                .info(new Info().title("My Service - SECOM v2.0 Interfaces")
                        .description("The SECOM V2 interfaces of the My Service")
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