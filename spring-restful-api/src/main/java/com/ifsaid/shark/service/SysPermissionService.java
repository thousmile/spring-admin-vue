package com.ifsaid.shark.service;

import com.ifsaid.shark.common.service.BaseService;
import com.ifsaid.shark.entity.SysPermission;

import java.util.Set;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 权限 Service 接口
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface SysPermissionService extends BaseService<SysPermission, Integer> {

    /**
     * 根据用户Id，查询拥有权限
     *
     * @param uid
     * @return Set<SysPermission>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:23
     */
    Set<SysPermission> findAllByUserId(Integer uid);

    /**
     * 根据角色Id，查询拥有权限
     *
     * @param rid
     * @return Set<Integer>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:22
     */
    Set<SysPermission> findPermissionByRoleId(Integer rid);

}
