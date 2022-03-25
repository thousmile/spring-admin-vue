package com.xaaef.robin.jwt;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
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
@ConfigurationProperties(prefix = "jwt.token")
public class JwtTokenProperties {

    /**
     * token 在请求头中的名称
     */
    private String tokenHeader = "Authorization";

    /**
     * token 类型
     */
    private String tokenType = "Bearer ";

    /**
     * token 缓存 过期时间  单位(秒)
     */
    private Integer tokenExpired = 3600;

    /**
     * 短信验证码过期时间 单位(秒)
     */
    private Integer smsCodeExpired = 600;

    /**
     * 用户被挤下线，提示的过期时间 单位(秒)
     */
    private Integer promptExpired = 600;

    /**
     * 秘钥
     */
    private String secret = "2N321lIkh$*!IfNt4&5!YZykD$7@ApaM8r@b@r@&4CZ7eqKe!s";

    /**
     * 单点登录，是否启用
     */
    private Boolean sso = Boolean.TRUE;

    /**
     * 需要排除的URL
     */
    private String[] excludePath;

}
