package com.xaaef.shark.service;

import com.github.pagehelper.PageInfo;
import com.xaaef.shark.common.service.BaseService;
import com.xaaef.shark.entity.SysUser;
import com.xaaef.shark.util.QueryParameter;
import com.xaaef.shark.vo.ResetPassword;
import com.xaaef.shark.vo.SysUserVo;
import com.xaaef.shark.vo.UpdatePassword;

import java.util.Set;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 用户 Service 接口
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */

public interface SysUserService extends BaseService<SysUser, Integer> {

    /**
     * 修改用户的角色
     *
     * @param uid
     * @param roleIds
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:20
     */
    int updateUserRoles(Integer uid, Set<Integer> roleIds);

    /**
     * 修改密码
     *
     * @param pwd
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:20
     */
    int updatePassword(UpdatePassword pwd);

    /**
     * 重置密码
     *
     * @param pwd
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:20
     */
    int resetPassword(ResetPassword pwd);

    /**
     * 分页擦好像 获取用户详细信息
     *
     * @param parameter
     * @return PageInfo<SysUserVo>
     */
    PageInfo<SysUserVo> findAllPageInfo(QueryParameter parameter);

}
