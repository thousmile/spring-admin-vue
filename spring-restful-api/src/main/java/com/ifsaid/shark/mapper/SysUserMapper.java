package com.ifsaid.shark.mapper;

import com.ifsaid.shark.common.mapper.BaseMapper;
import com.ifsaid.shark.entity.Relation;
import com.ifsaid.shark.entity.SysUser;

import java.util.List;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface SysUserMapper extends BaseMapper<SysUser, Integer> {

    /**
     * 根据 用户名，和 昵称，模糊匹配
     *
     * @param keywords
     * @return Set<SysRole>
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:04
     */
    Set<SysUser> selectByKeywords(String keywords);

    /**
     * 角色关联，多个角色
     *
     * @param record
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:25
     */
    int insertByRoles(List<Relation> record);

    /**
     * 删除某个用户，拥有的角色
     *
     * @param uid
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/14 0:25
     */
    int deleteHaveRoles(Integer uid);

}
