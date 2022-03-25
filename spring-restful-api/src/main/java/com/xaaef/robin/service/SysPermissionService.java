package com.xaaef.robin.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.xaaef.robin.base.service.BaseService;
import com.xaaef.robin.entity.SysPermission;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author WangChenChen
 * @description 针对表【sys_permission([ 权限 ] 菜单权限表)】的数据库操作Service
 * @createDate 2022-03-22 09:59:32
 */

public interface SysPermissionService extends BaseService<SysPermission> {


    /**
     * 根据用户ID 获取全部的权限
     *
     * @author WangChenChen
     * @date 2022/3/22 17:42
     */
    List<SysPermission> listByUserId(Long userId);


    /**
     * 根据用户ID 获取全部的权限 字符串
     *
     * @author WangChenChen
     * @date 2022/3/22 17:42
     */
    Set<String> listSimpleByUserId(Long userId);


    /**
     * 判断是否某个权限，是否还被其他角色引用
     */
    boolean roleReference(Long roleId);


    /**
     * 判断是否某个权限下，是否还有拥有子权限
     */
    boolean haveChildren(Long pid);


    /**
     * 获取 此部门 的所有菜单权限
     *
     * @author Wang Chen Chen
     * @date 2021/7/21 16:55
     */
    List<SysPermission> listByDeptId(Long deptId);


    /**
     * 获取 此角色 的所有菜单权限
     *
     * @author Wang Chen Chen
     * @date 2021/7/21 16:55
     */
    List<SysPermission> listByRoleId(Long roleId);


}
