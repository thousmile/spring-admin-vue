package com.xaaef.robin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xaaef.robin.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * [ 权限 ] 角色表
 *
 * @TableName sys_role
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleProxy implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
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
