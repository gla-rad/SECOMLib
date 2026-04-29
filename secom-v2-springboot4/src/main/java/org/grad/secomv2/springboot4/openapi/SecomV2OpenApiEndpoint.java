package org.grad.secomv2.springboot4.openapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;
import org.grad.secomv2.core.base.SecomConstants;
import org.grad.secomv2.core.interfaces.GenericSecomInterface;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The SECOM OpenAPI Endpoint
 * <p/>
 * Provides the OpenAPI definition for all registered controllers that
 * implement GenericSecomInterface. Uses SpringDoc's OpenAPI bean
 * (populated by scanning Spring MVC annotations) and filters it to
 * only the paths handled by SECOM controllers.
 */
@RestController
@RequestMapping("/api/secom/")
public class SecomV2OpenApiEndpoint {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired(required = false)
    private SecomV2OpenApiInfoProvider secomV2OpenApiInfoProvider;

    /**
     * SpringDoc's fully-built OpenAPI model — injected automatically when
     * springdoc-openapi-starter-webmvc-ui is on the classpath.
     */
    @Autowired
    private OpenAPI openAPI;

    /**
     * Spring MVC's handler mapping — used to find which URL paths belong
     * to our SECOM controllers.
     */
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    /**
     * GET /v2/openapi.json : returns the OpenAPI JSON filtered to only the
     * endpoints implemented by GenericSecomInterface beans.
     */
    @GetMapping(
            value = "/" + SecomConstants.SECOM_VERSION + "/openapi.json",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> getOpenApiV2() throws JsonProcessingException {

        // 1. Find all SECOM controller classes
        Set<Class<?>> secomControllerClasses = applicationContext
                .getBeansOfType(GenericSecomInterface.class)
                .values()
                .stream()
                .map(AopUtils::getTargetClass)
                .collect(Collectors.toSet());

        // 2. Find all URL paths that map to those controllers
        Set<String> secomPaths = requestMappingHandlerMapping
                .getHandlerMethods()
                .entrySet()
                .stream()
                .filter(e -> secomControllerClasses.contains(
                        e.getValue().getBeanType()))
                .flatMap(e -> {
                    RequestMappingInfo info = e.getKey();
                    return info.getPatternValues().stream();
                })
                .collect(Collectors.toSet());

        // 3. Clone the SpringDoc OpenAPI and apply SECOM info if provided
        OpenAPI filtered = new OpenAPI();
        OpenAPI openAPI = Optional.ofNullable(secomV2OpenApiInfoProvider)
                .map(SecomV2OpenApiInfoProvider::getOpenApiInfo)
                .orElseGet(SecomV2OpenApiInfoProvider::defaultOpenAPIInfo);
        filtered.setInfo(openAPI.getInfo());
        filtered.setServers(openAPI.getServers());
        filtered.setExternalDocs(openAPI.getExternalDocs());
        filtered.setComponents(openAPI.getComponents());

        // 4. Filter paths down to only SECOM-handled ones
        Paths filteredPaths = new Paths();
        if (openAPI.getPaths() != null) {
            openAPI.getPaths().forEach((path, item) -> {
                if (secomPaths.contains(path)) {
                    filteredPaths.addPathItem(path, item);
                }
            });
        }
        filtered.setPaths(filteredPaths);

        // 5. Serialise with the same settings as the original
        ObjectMapper mapper = objectMapper.copy()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(new JavaTimeModule());

        return ResponseEntity.ok(mapper.writeValueAsString(filtered));
    }
}