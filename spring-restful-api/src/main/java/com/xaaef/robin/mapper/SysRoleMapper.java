package com.xaaef.robin.mapper;

import com.xaaef.robin.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xaaef.robin.entity.SysRoleProxy;
import org.apache.ibatis.annotations.*;

import java.util.Map;
import java.util.Set;

/**
 * @author WangChenChen
 * @description 针对表【sys_role([ 权限 ] 角色表)】的数据库操作Mapper
 * @createDate 2022-03-22 09:59:32
 * @Entity com.xaaef.robin.entity.SysRole
 */

public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询用户的角色
     *
     * @author WangChenChen
     * @date 2022/3/22 18:14
     */
    Set<SysRole> listByUserId(Long userId);


    /**
     * 查询用户的角色
     *
     * @author WangChenChen
     * @date 2022/3/22 18:14
     */
    Set<SysRoleProxy> listByUserIds(@Param("userIds") Set<Long> userIds);


    // 角色添加 菜单权限
    int insertByMenus(@Param("roleId") Long roleId,
                      @Param("items") Set<Long> items);


    // 是否还有 用户 引用此角色
    @Select("SELECT COUNT(*) FROM sys_user_role WHERE role_id = #{roleId}")
    int userReference(Long roleId);


    // 删除 角色 的所有权限
    @Delete("DELETE FROM sys_role_permission WHERE role_id = #{roleId}")
    int deleteHaveMenus(Long roleId);


}




