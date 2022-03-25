package com.xaaef.robin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.xaaef.robin.base.entity.BaseEntity;
import com.xaaef.robin.domain.TreeNode;
import com.xaaef.robin.enums.PermissionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [ 权限 ] 菜单权限表
 *
 * @TableName sys_permission
 */

@TableName(value = "sys_permission")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysPermission extends BaseEntity implements Serializable, TreeNode<SysPermission> {

    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long permissionId;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 父主键
     */
    private Long parentId;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 菜单类型（1.菜单 2.按钮）
     */
    private PermissionTypeEnum permType;

    @TableField(exist = false)
    private List<SysPermission> children;

    @Override
    public Long getId() {
        return this.permissionId;
    }

    @Override
    public List<SysPermission> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List<SysPermission> list) {
        this.children = list;
    }


}
