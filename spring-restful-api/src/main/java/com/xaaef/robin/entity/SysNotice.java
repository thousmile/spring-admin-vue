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
 * [ 通用 ] 通知公告表
 *
 * @TableName sys_notice
 */

@TableName(value = "sys_notice")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysNotice extends BaseEntity implements Serializable {
    /**
     * 公告ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 公告类型 [ 0.危险  1.紧急  2.警告  3.通知 ]
     */
    private Integer category;

    /**
     * 发布时间
     */
    private LocalDateTime releaseTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 谁可以查看此公告 [ 0.此商户下的用户  1.全部的用户 ]
     */
    private Integer viewRange;

    /**
     * 状态 【0.已经过期 1.正常 】
     */
    private Integer status;

    /**
     * 过期时间
     */
    private LocalDateTime expired;


}
