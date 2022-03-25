package com.xaaef.robin.service.impl;

import com.xaaef.robin.base.service.impl.BaseServiceImpl;
import com.xaaef.robin.entity.SysPermission;
import com.xaaef.robin.service.SysPermissionService;
import com.xaaef.robin.mapper.SysPermissionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author WangChenChen
 * @description 针对表【sys_permission([ 权限 ] 菜单权限表)】的数据库操作Service实现
 * @createDate 2022-03-22 09:59:32
 */


@Service
@AllArgsConstructor
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermissionMapper, SysPermission>
        implements SysPermissionService {

    @Override
    public List<SysPermission> listByUserId(Long userId) {
        return baseMapper.listByUserId(userId);
    }

    @Override
    public Set<String> listSimpleByUserId(Long userId) {
        return baseMapper.listSimpleByUserId(userId);
    }


    @Override
    public boolean roleReference(Long roleId) {
        return baseMapper.roleReference(roleId) > 0;
    }


    @Override
    public boolean haveChildren(Long pid) {
        return count(SysPermission::getParentId, pid) > 0;
    }


    @Override
    public List<SysPermission> listByDeptId(Long deptId) {
        return baseMapper.selectByDeptId(deptId);
    }


    @Override
    public List<SysPermission> listByRoleId(Long roleId) {
        return baseMapper.selectByRoleId(roleId);
    }


}




