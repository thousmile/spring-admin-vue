package com.xaaef.robin.config.props;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.0
 * @date 2020/7/2515:30
 */


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "customize.swagger")
public class CustomizeSwaggerProperties {

    /**
     * 标题
     */
    private String title;

    /**
     * 文档描述
     */
    private String description;

    /**
     * 版本
     */
    private String version = "3.0";

    /**
     * 名称
     */
    private String name;

    /**
     * URL地址
     */
    private String url;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 服务地址
     */
    private String serviceUrl;

}
