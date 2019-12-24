package com.ifsaid.shark.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * swagger2 RESTful接口文档配置
 * 文档URL: http://localhost:8090/doc.html
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket createRestfulApi() {
        //api文档实例
        //文档类型：DocumentationType.SWAGGER_2
        return new Docket(DocumentationType.SWAGGER_2)
                //api信息
                .apiInfo(apiInfo())
                //构建api选择器
                .select()
                //api选择器选择api的包
                .apis(RequestHandlerSelectors.basePackage("com.ifsaid.shark.controller"))
                //api选择器选择包路径下任何api显示在文档中
                .paths(PathSelectors.any())
                //创建文档
                .build();
    }

    /**
     * 接口的相关信息
     *
     * @return ApiInfo
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:16
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("哈哈后台 Swagger2 构建RESTful接口 ")
                .description("接口描述")
                .termsOfServiceUrl("termsOfServiceUrl")
                .version("1.0")
                .license("http://springfox.github.io/springfox/docs/current/")
                .licenseUrl("http://springfox.github.io/springfox/docs/current/")
                .build();
    }

}