package com.ifsaid.shark.mapper;

import com.ifsaid.shark.common.mapper.BaseMapper;
import com.ifsaid.shark.entity.SysPermission;

import java.util.List;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface SysPermissionMapper extends BaseMapper<SysPermission, Integer> {

    /**
     * 根据 用户ID 获取拥有的权限
     *
     * @param uid
     * @return List<SysPermission>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:43
     */
    Set<SysPermission> findAllByUserId(Integer uid);

    /**
     * 根据 角色ID 获取拥有的权限
     *
     * @param rid
     * @return List<SysPermission>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/13 23:43
     */
    Set<SysPermission> findAllByRoleId(Integer rid);

}
