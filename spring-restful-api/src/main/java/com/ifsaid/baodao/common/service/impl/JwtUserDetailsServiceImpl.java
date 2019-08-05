package com.ifsaid.baodao.common.service.impl;

import com.ifsaid.baodao.common.jwt.JwtUser;
import com.ifsaid.baodao.entity.User;
import com.ifsaid.baodao.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/29 14:15
 * @describe： jwt验证用户是否合法
 * @version: 1.0
 */

@Slf4j
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByAccount(username);
        if (user == null || StringUtils.isEmpty(user.getUid())) {
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));
        } else {
            return new JwtUser(user.getUid(), user.getAccount(), user.getPassword(), user.getState(), null);
        }
    }


}
