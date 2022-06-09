package com.xaaef.robin.jwt;

import com.xaaef.robin.enums.OAuth2Error;
import com.xaaef.robin.exception.JwtAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;


/**
 * <p>
 * jwt 工具类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 */


@Getter
@Component
@AllArgsConstructor
@EnableConfigurationProperties(JwtTokenProperties.class)
public class JwtTokenUtils {

    private JwtTokenProperties props;

    private static Key key;

    @PostConstruct
    public void init() {
        if (StringUtils.hasText(props.getSecret())) {
            byte[] bytes = props.getSecret().getBytes(StandardCharsets.UTF_8);
            key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(bytes));
        } else {
            key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
    }


    public JwtTokenProperties getProps() {
        return props;
    }


    /**
     * 从数据声明生成令牌
     *
     * @param id 数据声明
     * @return 令牌
     */
    public String createAccessToken(String id) {
        long expired = Instant.now()
                .plus(props.getTokenExpired(), ChronoUnit.SECONDS)
                .toEpochMilli();
        return Jwts.builder()
                .setId(id)
                .setSubject(String.valueOf(expired))
                .signWith(key)
                .compact();
    }


    /**
     * 从数据声明生成令牌
     *
     * @return 令牌
     */
    public String createTokenId() {
        return UUIDUtils.getSimpleStrId();
    }


    /**
     * 从数据声明生成令牌
     *
     * @return 令牌
     */
    public String createUUID() {
        return createTokenId();
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
    public String getIdFromToken(String token) throws JwtAuthException {
        String tokenValue = getTokenValue(token);
        if (tokenValue == null) {
            throw new JwtAuthException(OAuth2Error.TOKEN_FORMAT_ERROR);
        }
        Claims claims = getClaimsFromToken(tokenValue);
        // 过期时间 毫秒
        var milli = Long.valueOf(claims.getSubject());
        // 获取过期时间
        var expired = Instant.ofEpochMilli(milli);
        // 判断 token 是否过期！
        if (Instant.now().isAfter(expired)) {
            throw new JwtAuthException(OAuth2Error.ACCESS_TOKEN_EXPIRED);
        }
        return claims.getId();
    }


    /**
     * 截取完整的token，根据前缀 "Bearer "开头
     *
     * @return 新令牌
     */
    public String getTokenValue(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(props.getTokenType())) {
            return bearerToken.substring(props.getTokenType().length());
        }
        return null;
    }

}
