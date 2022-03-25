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
 * [ 权限 ] 用户社交平台登录
 *
 * @TableName sys_user_social
 */

@TableName(value = "sys_user_social")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserSocial extends BaseEntity implements Serializable {

    /**
     * 用户社交ID
     */
    @TableId(type = IdType.AUTO)
    private Long socialId;

    /**
     * 用户唯一ID
     */
    private Long userId;

    /**
     * 社交账号唯一ID
     */
    private String openId;

    /**
     * we_chat. 微信  tencent_qq. 腾讯QQ
     */
    private String socialType;

}
