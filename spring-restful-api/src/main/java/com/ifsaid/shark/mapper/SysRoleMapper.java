package com.ifsaid.shark.mapper;

import com.ifsaid.shark.common.mapper.BaseMapper;
import com.ifsaid.shark.entity.Relation;
import com.ifsaid.shark.entity.SysRole;

import java.util.List;
import java.util.Set;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface SysRoleMapper extends BaseMapper<SysRole, Integer> {

    /**
     * 根据 用户 ID 查询拥有的角色列表
     *
     * @param uid
     * @return Set<SysRole>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:04
     */
    Set<SysRole> findAllByUserId(Integer uid);

    /**
     * 角色关联，多个权限
     *
     * @param record
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:25
     */
    int insertByPermissions(List<Relation> record);

    /**
     * 删除某个角色，拥有的所有权限
     *
     * @param rid
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:25
     */
    int deleteHavePermissions(Integer rid);

    /**
     * 判断是否某个角色，是否还被其他用户引用
     *
     * @param rid
     * @return Integer
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/22 19:50
     */
    Integer userReference(Integer rid);

    /**
     * 根据 角色名称，和描述，模糊匹配
     *
     * @param keywords
     * @return Set<SysRole>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:04
     */
    Set<SysRole> selectByKeywords(String keywords);

}
