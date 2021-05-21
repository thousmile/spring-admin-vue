package com.xaaef.shark.common.jwt;

import com.xaaef.shark.constant.LoginConstant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * jwt 工具类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenUtils implements Serializable {

    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 过期时间
     *
     * @date 2019/12/11 21:53
     */
    private Long expiration;

    private String tokenHeader;

    private String tokenHead;

    /**
     * 单点登录，是否启用
     *
     * @date 2019/12/11 21:53
     */
    private Boolean sso = false;

    /**
     * 从数据声明生成令牌
     *
     * @param id 数据声明
     * @return 令牌
     */
    public String createToken(String id) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration * 60000);
        return Jwts.builder()
                .setSubject(id)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    /**
     * 从数据声明生成令牌
     *
     * @param id 数据声明
     * @return 令牌
     */
    public String createLoginId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getIdFromToken(String token) throws JwtException {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                throw new JwtException("身份认证已经过期，请重新登录！");
            }
            return claims.getSubject();
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    /**
     * 截取完整的token，根据前缀 "Bearer "开头
     *
     * @return 新令牌
     */
    public String getTokenValue(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenHead)) {
            return bearerToken.substring(tokenHead.length());
        }
        return null;
    }

}
