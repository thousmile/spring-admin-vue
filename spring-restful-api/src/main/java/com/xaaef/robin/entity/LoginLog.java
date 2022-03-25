package com.xaaef.robin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录日志记录
 *
 * @TableName login_log
 */

@TableName(value = "login_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginLog implements Serializable {

    /**
     * ID
     */
    @TableId
    private String messageId;

    /**
     * 登录类型
     */
    private String grantType;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 请求IP
     */
    private String requestIp;

    /**
     * 操作真实地址
     */
    private String address;

    /**
     * 操作系统类型
     */
    private String osName;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
