package com.xaaef.robin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

import com.xaaef.robin.base.entity.BaseEntity;
import com.xaaef.robin.domain.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [ 权限 ] 部门表
 *
 * @TableName sys_dept
 */


@TableName(value = "sys_dept")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysDept extends BaseEntity implements Serializable, TreeNode<SysDept> {

    /**
     * 部门 ID
     */
    @TableId(type = IdType.AUTO)
    private Long deptId;

    /**
     * 父主键
     */
    private Long parentId;

    /**
     * 部门名
     */
    private String deptName;

    /**
     * 部门领导名称
     */
    private String leader;

    /**
     * 领导手机号码
     */
    private String leaderMobile;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 部门描述
     */
    private String description;


    @TableField(exist = false)
    private List<SysDept> children;


    @Override
    public Long getId() {
        return this.deptId;
    }

    @Override
    public List<SysDept> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List<SysDept> list) {
        this.children = list;
    }

}
