package com.xaaef.robin.service;

import com.xaaef.robin.exception.JwtAuthException;
import com.xaaef.robin.jwt.JwtLoginUser;
import com.xaaef.robin.jwt.JwtTokenValue;

import java.util.Set;


/**
 * <p>
 * 服务端 token 认证
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/12 16:28
 */


public interface JwtTokenService {

    /**
     * 设置登录的用户 到redis中
     *
     * @param loginId
     * @param loginUser
     */
    void setLoginUser(String loginId, JwtLoginUser loginUser);


    /**
     * 校验 token 值是否正确
     * <p>
     * 例: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdG6IjE2NDPuFA
     *
     * @param bearerToken
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    JwtLoginUser validate(String bearerToken) throws JwtAuthException;


    /**
     * 刷新 token
     */
    JwtTokenValue refresh();


    /**
     * 退出登录
     */
    void logout();


    /**
     * 获取 所有的在线用户
     *
     * @return String 用户名称
     * @author Wang Chen Chen
     * @date 2021/7/12 16:29
     */
    Set<String> getOnlineUsers();

}
