package com.ifsaid.admin.service;

import com.ifsaid.admin.common.exception.UserExistsException;
import com.ifsaid.admin.common.service.IBaseService;
import com.ifsaid.admin.entity.SysUser;
import com.ifsaid.admin.vo.SysUserVo;
import org.springframework.security.core.AuthenticationException;

/**
 * <p>
 * [权限管理] 用户表 服务类
 * </p>
 *
 * @author wang chen chen
 * @since 2018-10-23
 */
public interface ISysUserService extends IBaseService<SysUser, String> {

    SysUser findByUsername(String username);


    /**
     * 获取用户详细信息
     * @param username
     * @return 操作结果
     */
    SysUserVo findUserInfo(String username);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     */
    String login(String username, String password) throws AuthenticationException;

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 操作结果
     */
    Integer register(SysUser sysUser) throws UserExistsException;

    /**
     * 刷新密钥
     *
     * @param oldToken 原密钥
     * @return 新密钥
     */
    String refreshToken(String oldToken);

}
