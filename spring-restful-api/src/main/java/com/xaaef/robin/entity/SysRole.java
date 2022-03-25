package com.xaaef.robin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.xaaef.robin.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [ 权限 ] 角色表
 *
 * @TableName sys_role
 */

@TableName(value = "sys_role")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRole extends BaseEntity implements Serializable {
    /**
     * 角色id
     */
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 部门描述
     */
    private String description;
}
