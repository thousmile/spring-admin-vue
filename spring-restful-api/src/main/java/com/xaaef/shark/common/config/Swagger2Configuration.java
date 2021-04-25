package com.xaaef.shark.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * swagger2 RESTful接口文档配置
 * 文档URL: http://localhost:8090/doc.html
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */


@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Configuration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("哈哈后台 Swagger2 构建RESTful接口")
                        .description("哈哈后台 Swagger2 构建RESTful接口")
                        .termsOfServiceUrl("http://www.xaaef.com/")
                        .contact(new Contact("王逗逗",
                                "https://github.com/thousmile",
                                "932560435@qq.com")
                        )
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("2.0版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.xaaef.shark.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

}