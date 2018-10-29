package com.ifsaid.admin.service.impl;

import com.ifsaid.admin.common.jwt.JwtUser;
import com.ifsaid.admin.entity.SysRole;
import com.ifsaid.admin.entity.SysUser;
import com.ifsaid.admin.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/29 14:15
 * @describe：
 * @version: 1.0
 */

@Slf4j
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectByUserName(username);
        if (sysUser == null || StringUtils.isEmpty(sysUser.getUid())) {
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));
        } else {
            List<SimpleGrantedAuthority> collect = sysUser.getRoles().stream().map(SysRole::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            return new JwtUser(sysUser.getUsername(), sysUser.getPassword(), sysUser.getState(), collect);
        }
    }


}
