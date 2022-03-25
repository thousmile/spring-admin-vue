package com.xaaef.robin.service;

import com.xaaef.robin.base.service.BaseService;
import com.xaaef.robin.entity.SysDept;
import com.xaaef.robin.vo.UpdatePermsVO;

import java.util.Set;

/**
 * @author WangChenChen
 * @description 针对表【sys_dept([ 权限 ] 部门表)】的数据库操作Service
 * @createDate 2022-03-22 09:59:32
 */


public interface SysDeptService extends BaseService<SysDept> {

    /**
     * 已经拥有的菜单
     *
     * @throws
     * @author Wang Chen Chen<932560435@qq.com>
     * @create 2021/7/24 14:49
     */
    UpdatePermsVO listHavePerms(Long deptId);


    /**
     * 修改 部门 菜单
     *
     * @author Wang Chen Chen
     * @date 2021/7/23 15:19
     */
    boolean updatePerms(Long id, Set<Long> items);

}
