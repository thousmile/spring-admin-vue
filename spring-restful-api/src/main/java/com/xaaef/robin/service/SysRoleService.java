package com.xaaef.robin.service;

import com.xaaef.robin.base.service.BaseService;
import com.xaaef.robin.entity.SysRole;
import com.xaaef.robin.vo.UpdatePermsVO;

import java.util.Map;
import java.util.Set;

/**
 * @author WangChenChen
 * @description 针对表【sys_role([ 权限 ] 角色表)】的数据库操作Service
 * @createDate 2022-03-22 09:59:32
 */

public interface SysRoleService extends BaseService<SysRole> {

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
    Map<Long, Set<SysRole>> listByUserIds(Set<Long> userIds);

    /**
     * 已经拥有的权限
     */
    UpdatePermsVO listHavePerms(Long roleId);

    /**
     * 修改 角色 权限
     *
     * @author Wang Chen Chen
     * @date 2021/7/23 15:19
     */
    boolean updatePerms(Long id, Set<Long> items);


}
