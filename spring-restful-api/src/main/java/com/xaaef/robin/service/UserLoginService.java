package com.xaaef.robin.service;

import com.xaaef.robin.exception.JwtAuthException;
import com.xaaef.robin.jwt.JwtTokenValue;
import com.xaaef.robin.vo.LoginUserVO;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户 登录 Service 接口
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 */


public interface UserLoginService {

    /**
     * 用户登录
     *
     * @param user
     * @return String token 值
     * @throws AuthenticationException
     */
    JwtTokenValue login(LoginUserVO user, HttpServletRequest request) throws JwtAuthException;

}
