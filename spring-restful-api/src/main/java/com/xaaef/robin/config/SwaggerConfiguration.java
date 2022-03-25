package com.xaaef.robin.config;

import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import com.xaaef.robin.config.props.CustomizeSwaggerProperties;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.web.util.UrlUtils;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;


/**
 * <p>
 * Swagger 文档  http://localhost:18888/doc.html
 * </p>
 *
 * @author WangChenChen
 * @version 1.0.1
 * @date 2021/10/21 11:29
 */


@Slf4j
@Configuration
@EnableSwagger2
@AllArgsConstructor
@Import(BeanValidatorPluginsConfiguration.class)
@EnableConfigurationProperties(CustomizeSwaggerProperties.class)
public class SwaggerConfiguration {

    private final CustomizeSwaggerProperties props;

    private final ServerProperties sp;

    @Bean(value = "orderApi")
    @Order(value = 1)
    public Docket groupRestApi() {
        String requestURI = "/doc.html";
        if (StringUtils.isNotBlank(sp.getServlet().getContextPath())) {
            requestURI = sp.getServlet().getContextPath() + requestURI;
        }
        String docUrl = UrlUtils.buildFullRequestUrl(
                sp.getSsl() == null ? "http" : "https",
                "localhost",
                sp.getPort(),
                requestURI,
                null
        );
        log.info("Knife4j Swagger2 API Doc {}", docUrl);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()));
    }


    private ApiInfo groupApiInfo() {
        return new ApiInfoBuilder()
                .title(props.getTitle())
                .description(props.getDescription())
                .contact(new Contact(props.getName(), props.getUrl(), props.getEmail()))
                .termsOfServiceUrl(props.getServiceUrl())
                .version(props.getVersion())
                .build();
    }


    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build();
    }


    List<SecurityReference> defaultAuth() {
        var scopes = new AuthorizationScope[]{
                new AuthorizationScope("global", "accessEverything")
        };
        return CollectionUtils.newArrayList(new SecurityReference("BearerToken", scopes));
    }

}
