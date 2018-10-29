package com.ifsaid.admin.mapper;

import com.ifsaid.admin.common.mapper.BaseMapper;
import com.ifsaid.admin.entity.SysRole;

import java.util.Set;

public interface SysRoleMapper extends BaseMapper<SysRole, Integer> {

    Set<SysRole> selectByUserName(String username);

}