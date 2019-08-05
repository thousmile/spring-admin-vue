package com.ifsaid.baodao.service;

import com.ifsaid.baodao.common.exception.UserExistsException;
import com.ifsaid.baodao.common.service.IBaseService;
import com.ifsaid.baodao.entity.User;
import com.ifsaid.baodao.vo.MyPage;
import com.ifsaid.baodao.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.AuthenticationException;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:24
 * @describe：
 * @version: 1.0
 */

public interface IUserService extends IBaseService<User, String> {

    /**
     * @describe 根据账号查询出用户信息
     * @date 2018/11/15
     * @author Wang Chen Chen
     */
    User findByAccount(String account);

    /**
     * @describe 根据微信的openId查询出用户信息
     * @date 2018/11/15
     * @author Wang Chen Chen
     */
    User findByOpenId(String openId);

    /**
     * 获取用户详细信息
     *
     * @param account
     * @return 操作结果
     */
    UserVo findUserInfo(String account);

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
    User register(User user) throws UserExistsException;

    /**
     * 刷新密钥
     *
     * @param oldToken 原密钥
     * @return 新密钥
     */
    String refreshToken(String oldToken);

    /**
     * @describe 查询出某个部门下用户数量
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    int countByDept(Integer deptId);

    /**
     * @Author: Wang Chen Chen
     * @Description: 获取用户分页详情
     * @Date: 17:22 2018/11/18
     */
    Page<UserVo> findAllInfo(Pageable page);


}
