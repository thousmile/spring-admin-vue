package com.xaaef.shark.service;

import com.xaaef.shark.common.jwt.JwtLoginUser;
import com.xaaef.shark.vo.TokenValue;
import com.xaaef.shark.vo.UserVo;
import org.springframework.security.core.AuthenticationException;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 用户 登录 Service 接口
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */

public interface UserLoginService {

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return String token 值
     * @throws AuthenticationException
     */
    TokenValue login(String username, String password) throws AuthenticationException;

    /**
     * 用户退出登录
     *
     * @param loginUser
     */
    void logout();

    /**
     * 刷新 token
     *
     * @return UserVo
     */
    TokenValue refresh();

    /**
     * 校验登录的用户是否存在
     *
     * @param loginId 登录ID
     * @return 操作结果
     * @throws AuthenticationException
     */
    JwtLoginUser validateUser(String loginId) throws AuthenticationException;

    /**
     * 获取用户详细信息
     *
     * @return UserVo
     */
    UserVo findUserInfo();


}
