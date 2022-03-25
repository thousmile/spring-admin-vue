package com.xaaef.robin.mapper;

import com.xaaef.robin.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @author WangChenChen
 * @description 针对表【sys_permission([ 权限 ] 菜单权限表)】的数据库操作Mapper
 * @createDate 2022-03-22 09:59:32
 * @Entity com.xaaef.robin.entity.SysPermission
 */

public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 根据 用户ID 获取拥有的权限
     */
    List<SysPermission> listByUserId(Long userId);


    /**
     * 根据 用户ID 获取拥有的权限
     */
    Set<String> listSimpleByUserId(Long userId);


    /**
     * 判断是否某个权限，是否还被其他角色引用
     */
    @Select("SELECT COUNT(*) FROM sys_role_permission WHERE role_id = #{roleId}")
    long roleReference(Long roleId);


    // 查询部门所有权限
    @Select("SELECT m.permission_id, m.parent_id, m.title, m.perms FROM sys_permission AS m " +
            "LEFT JOIN sys_dept_permission AS dm ON dm.permission_id = m.permission_id " +
            "WHERE dm.dept_id = #{deptId}")
    List<SysPermission> selectByDeptId(Long deptId);


    // 查询角色所有权限
    @Select("SELECT m.permission_id, m.parent_id, m.title, m.perms FROM sys_permission AS m " +
            "LEFT JOIN sys_role_permission AS rm ON rm.permission_id = m.permission_id " +
            "WHERE rm.role_id = #{roleId}")
    List<SysPermission> selectByRoleId(Long roleId);


}




