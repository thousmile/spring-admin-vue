package com.xaaef.robin.service.impl;

import com.xaaef.robin.constant.LoginConstant;
import com.xaaef.robin.exception.JwtAuthException;
import com.xaaef.robin.jwt.*;
import com.xaaef.robin.service.JwtTokenService;
import com.xaaef.robin.util.JsonUtils;
import com.xaaef.robin.util.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务端 jwt token 认证
 * </p>
 *
 * @author WangChenChen
 * @version 1.0
 * @date 2022/3/22 15:24
 */

@Slf4j
@Service
@AllArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtTokenProperties props;

    private final JwtTokenUtils jwtTokenUtils;

    private final StringRedisTemplate redisTemplate;

    private void removeLoginUser(String loginId) {
        redisTemplate.delete(loginId);
    }

    private JwtLoginUser getLoginUser(String loginId) {
        String loginUserJson = redisTemplate.opsForValue().get(LoginConstant.LOGIN_TOKEN_KEY + loginId);
        if (StringUtils.isNotBlank(loginUserJson)) {
            return JsonUtils.toPojo(loginUserJson, JwtLoginUser.class);
        }
        return null;
    }


    @Override
    public void setLoginUser(String loginId, JwtLoginUser loginUser) {
        loginUser.setLoginId(loginId);
        String loginUserJson = JsonUtils.toJson(loginUser);
        String loginKey = LoginConstant.LOGIN_TOKEN_KEY + loginId;
        // 将随机id 跟 当前登录的用户关联，在一起！
        redisTemplate.opsForValue().set(
                loginKey,
                loginUserJson,
                props.getTokenExpired(),
                TimeUnit.SECONDS
        );
        // 判断是否开启 单点登录
        if (props.getSso()) {
            String onlineUserKey = LoginConstant.ONLINE_USER_KEY + loginUser.getUsername();

            String oldLoginKey = redisTemplate.opsForValue().get(onlineUserKey);
            // 判断用户名。是否已经登录了！
            if (StringUtils.isNotBlank(oldLoginKey)) {
                // 移除之前登录的用户
                removeLoginUser(LoginConstant.LOGIN_TOKEN_KEY + oldLoginKey);

                // 移除在线用户
                removeLoginUser(onlineUserKey);

                // 获取当前时间
                String milli = TimeUtils.dateTimeFormat(LocalDateTime.now());
                // 将 被强制挤下线的用户，以及时间，保存到 redis中，提示给前端用户！
                redisTemplate.opsForValue().set(
                        LoginConstant.FORCED_OFFLINE_KEY + oldLoginKey,
                        milli,
                        props.getPromptExpired(),
                        TimeUnit.SECONDS
                );
            }

            // 保存 在线用户
            redisTemplate.opsForValue().set(
                    onlineUserKey,
                    loginId,
                    props.getTokenExpired(),
                    TimeUnit.SECONDS
            );
        }
    }


    @Override
    public JwtLoginUser validate(String bearerToken) throws JwtAuthException {
        // 获取到 用户的唯一登录ID
        String loginId = jwtTokenUtils.getIdFromToken(bearerToken);
        // 根据登录 唯一登录ID 从redis中获取登录的用户信息
        JwtLoginUser jwtUser = getLoginUser(loginId);
        // 如果此用户为空。判断是否开启了单点登录
        if (jwtUser == null || StringUtils.isEmpty(jwtUser.getUsername())) {
            // 判断是否启用单点登录
            if (props.getSso()) {
                String key = LoginConstant.FORCED_OFFLINE_KEY + loginId;
                // 判断此用户，是不是被挤下线
                String offlineTime = redisTemplate.opsForValue().get(key);
                if (StringUtils.isNotBlank(offlineTime)) {
                    // 删除 被挤下线 的消息提示
                    removeLoginUser(key);
                    String errMsg = String.format("您的账号在[ %s ]被其他用户拥下线了！", offlineTime);
                    log.info("errMsg {}", errMsg);
                    throw new JwtAuthException(errMsg);
                }
            }
            throw new JwtAuthException("当前登录用户不存在");
        }
        jwtUser.setLoginId(loginId);
        return jwtUser;
    }


    @Override
    public JwtTokenValue refresh() {
        JwtLoginUser loginUser = JwtSecurityUtils.getLoginUser();
        // 移除登录的用户。根据tokenId
        removeLoginUser(loginUser.getLoginId());

        // 生成一个随机ID 跟当前用户关联
        String loginId = jwtTokenUtils.createUUID();

        // 重新生成token
        String token = jwtTokenUtils.createAccessToken(loginId);

        setLoginUser(loginId, loginUser);

        return JwtTokenValue.builder()
                .header(props.getTokenHeader())
                .accessToken(token)
                .tokenType(props.getTokenType())
                .expiresIn(props.getTokenExpired())
                .build();
    }


    @Override
    public void logout() {
        JwtLoginUser loginUser = JwtSecurityUtils.getLoginUser();
        // 移除登录的用户。根据tokenId
        removeLoginUser(loginUser.getLoginId());
    }


    @Override
    public Set<String> getOnlineUsers() {
        return Objects.requireNonNull(redisTemplate.keys(LoginConstant.ONLINE_USER_KEY + "*"))
                .stream()
                .map(r -> r.replaceAll(LoginConstant.ONLINE_USER_KEY, ""))
                .collect(Collectors.toSet());
    }

}
