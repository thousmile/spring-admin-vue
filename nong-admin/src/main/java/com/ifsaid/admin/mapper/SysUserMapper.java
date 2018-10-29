package com.ifsaid.admin.mapper;

import com.ifsaid.admin.common.mapper.BaseMapper;
import com.ifsaid.admin.entity.SysUser;

public interface SysUserMapper extends BaseMapper<SysUser, String> {

    SysUser selectByUserName(String username);

}