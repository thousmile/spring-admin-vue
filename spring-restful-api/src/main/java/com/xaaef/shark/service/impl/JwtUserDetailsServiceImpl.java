package com.xaaef.shark.service.impl;

import com.xaaef.shark.common.jwt.JwtLoginUser;
import com.xaaef.shark.entity.SysUser;
import com.xaaef.shark.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * spring security 认证 UserDetailsService 实现类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */


@Slf4j
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.find(SysUser.builder().username(username).build());
        if (user == null || StringUtils.isEmpty(user.getUid())) {
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));
        } else {
            return new JwtLoginUser(user.getUid(), null, user.getUsername(), user.getPassword(), user.getStatus(), null);
        }
    }


}
