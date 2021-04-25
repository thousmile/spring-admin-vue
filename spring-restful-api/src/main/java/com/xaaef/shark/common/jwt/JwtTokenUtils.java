package com.xaaef.shark.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;

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
     * 从数据声明生成令牌
     *
     * @param id 数据声明
     * @return 令牌
     */
    private String generateToken(String id) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration * 60000);
        return Jwts.builder()
                .setSubject(id)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
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
     * 生成令牌
     *
     * @param userDetails 用户
     * @return 令牌
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername());
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            if (expiration.before(new Date())) {
                return null;
            }
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            String username = getUsernameFromToken(token);
            refreshedToken = generateToken(username);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 刷新令牌
     *
     * @param 截取完整的token，根据前缀 "Bearer "开头
     * @return 新令牌
     */
    public String interceptCompleteToken(String completeToken) {
        String authToken = completeToken.substring(this.getTokenHead().length());
        return authToken;
    }


    /**
     * 验证令牌
     *
     * @param token       令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

}
