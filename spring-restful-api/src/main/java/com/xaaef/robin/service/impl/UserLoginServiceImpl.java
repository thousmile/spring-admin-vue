package com.xaaef.robin.service.impl;

import com.xaaef.robin.aspect.log.LogStorage;
import com.xaaef.robin.exception.JwtAuthException;
import com.xaaef.robin.jwt.*;
import com.xaaef.robin.service.*;
import com.xaaef.robin.vo.LoginUserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Service
@AllArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final AuthenticationManager authManager;

    private final JwtTokenService tokenService;

    private final JwtTokenUtils jwtTokenUtils;

    private final LogStorage logStorage;

    /**
     * 登录表单获取 Token
     *
     * @return
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    @Override
    public JwtTokenValue login(LoginUserVO user, HttpServletRequest request) throws JwtAuthException {
        // 把表单提交的 username  password 封装到 UsernamePasswordAuthenticationToken中
        var source = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );

        var target = new JwtLoginUser();
        BeanUtils.copyProperties(source.getPrincipal(), target);
        // 生成一个随机ID 跟当前用户关联
        String loginId = jwtTokenUtils.createUUID();
        String token = jwtTokenUtils.createAccessToken(loginId);
        JwtTokenProperties props = jwtTokenUtils.getProps();
        tokenService.setLoginUser(loginId, target);

        // 保存登录日志到数据库中
        logStorage.asyncLoginSave(target, request);

        return JwtTokenValue.builder()
                .header(props.getTokenHeader())
                .accessToken(token)
                .tokenType(props.getTokenType())
                .expiresIn(props.getTokenExpired())
                .build();
    }


}
