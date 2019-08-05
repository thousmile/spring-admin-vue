package com.ifsaid.baodao.service;

import com.ifsaid.baodao.common.service.IBaseService;
import com.ifsaid.baodao.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:24
 * @describe：
 * @version: 1.0
 */

public interface IRoleService extends IBaseService<Role, Integer> {

    /**
     *@describe 修改用户的角色
     *@date 2018/11/16
     *@author  Wang Chen Chen
     */
    Integer updateUserRole(String uid, Set<Integer> roleIds);

    /**
     *@describe 修改角色的权限
     *@date 2018/11/16
     *@author  Wang Chen Chen
     */
    Integer updateRolePermission(Integer rid, Set<Integer> permissionIds);


    /**
     * @Author: Wang Chen Chen
     * @Description: 根据用户id查出当前角色
     * @Date: 17:37 2018/11/18
     */
    Set<Role> findAllByUserId(String uid);

}
