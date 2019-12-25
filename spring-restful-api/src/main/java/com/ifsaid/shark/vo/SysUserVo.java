package com.ifsaid.shark.vo;

import com.ifsaid.shark.entity.SysUser;
import lombok.*;

import java.util.List;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 登录成功后，用户详细 Vo 模型
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/12 23:29
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysUserVo extends SysUser implements java.io.Serializable {

    /**
     * 部门名称
     *
     * @date: 2019/12/11 22:15
     */
    private String departmentName;

    /**
     * 角色名称，列表
     *
     * @date: 2019/12/11 22:15
     */
    private List<RoleVo> roles;

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleVo {

        private Integer rid;

        private String roleName;

        private String description;

    }

}
