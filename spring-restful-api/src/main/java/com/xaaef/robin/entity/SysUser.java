package com.xaaef.robin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.xaaef.robin.base.entity.BaseEntity;
import com.xaaef.robin.enums.AdminFlagEnum;
import com.xaaef.robin.enums.GenderEnum;
import com.xaaef.robin.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [ 权限 ] 用户表
 *
 * @TableName sys_user
 */

@TableName(value = "sys_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends BaseEntity implements Serializable {
    /**
     * 用户ID
     */
    @TableId
    private Long userId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账号
     */
    private String username;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户名称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别[ 0.女  1.男  2.未知]
     */
    private GenderEnum gender;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 状态 【0.禁用 1.正常 2.锁定 】
     */
    private StatusEnum status;

    /**
     * 0. 普通用户  1. 管理员
     */
    private AdminFlagEnum adminFlag;

    /**
     * 部门信息
     */
    @TableField(exist = false)
    private SysDept dept;

    /**
     * 角色名称，列表
     */
    @TableField(exist = false)
    private Collection<SysRole> roles;

}
