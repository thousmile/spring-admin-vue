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
 * [ 通用 ] 字典类型表
 *
 * @TableName sys_dict_type
 */

@TableName(value = "sys_dict_type")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysDictType extends BaseEntity implements Serializable {
    /**
     * 字典类型 ID
     */
    @TableId(type = IdType.AUTO)
    private Long typeId;

    /**
     * 字典类型名
     */
    private String typeName;

    /**
     * 字典类型关键字
     */
    private String typeKey;

    /**
     * 部门描述
     */
    private String description;

}
