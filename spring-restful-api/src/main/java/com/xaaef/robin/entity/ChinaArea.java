package com.xaaef.robin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.xaaef.robin.domain.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [ 通用 ] 中国行政地区表
 *
 * @TableName cn_area
 */

@TableName(value = "cn_area")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChinaArea implements Serializable, TreeNode<ChinaArea> {

    /**
     * 行政代码 [ 唯一 ]
     */
    @TableId
    private Long areaCode;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 父级行政代码
     */
    private Long parentCode;

    /**
     * 邮政编码
     */
    private Object zipCode;

    /**
     * 区号
     */
    private String cityCode;

    /**
     * 名称
     */
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 组合名
     */
    private String mergerName;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 经度
     */
    private BigDecimal lng;

    /**
     * 纬度
     */
    private BigDecimal lat;

    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<ChinaArea> children;

    @Override
    public Long getId() {
        return this.areaCode;
    }

    @Override
    public Long getParentId() {
        return this.parentCode;
    }

    @Override
    public Integer getSort() {
        return 0;
    }

    @Override
    public List<ChinaArea> getChildren() {
        return this.children;
    }

    @Override
    public void setChildren(List<ChinaArea> list) {
        this.children = list;
    }

}
