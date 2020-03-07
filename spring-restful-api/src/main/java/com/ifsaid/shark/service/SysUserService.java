package com.ifsaid.shark.service;

import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.common.jwt.JwtUser;
import com.ifsaid.shark.common.service.BaseService;
import com.ifsaid.shark.entity.SysUser;
import com.ifsaid.shark.util.QueryParameter;
import com.ifsaid.shark.vo.SysUserVo;
import com.ifsaid.shark.vo.UserVo;
import org.springframework.security.core.AuthenticationException;

import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 用户 Service 接口
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface SysUserService extends BaseService<SysUser, Integer> {

    /**
     * 修改用户的角色
     *
     * @param uid
     * @param roleIds
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:20
     */
    int updateUserRoles(Integer uid, Set<Integer> roleIds);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return String token 值
     * @throws AuthenticationException
     */
    String login(String username, String password) throws AuthenticationException;

    /**
     * 用户退出登录
     * @param loginUser
     */
    void logout(JwtUser loginUser);

    /**
     * 校验登录的用户中，用户名，是否正确
     *
     * @param username 用户名
     * @return 操作结果
     * @throws AuthenticationException
     */
    JwtUser validateUsername(String username) throws AuthenticationException;


    /**
     * 获取用户详细信息
     *
     * @return UserVo
     */
    UserVo findUserInfo();


    /**
     * 分页擦好像 获取用户详细信息
     *
     * @param parameter
     * @return PageInfo<SysUserVo>
     */
    PageInfo<SysUserVo> findAllPageInfo(QueryParameter parameter);

}
