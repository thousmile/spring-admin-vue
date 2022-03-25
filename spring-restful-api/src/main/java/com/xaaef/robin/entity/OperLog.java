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
 * 用户操作日志
 *
 * @TableName oper_log
 */

@TableName(value = "oper_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperLog implements Serializable {

    /**
     * ID
     */
    @TableId
    private String messageId;

    /**
     * 标题
     */
    private String title;

    /**
     * 操作类型
     */
    private String operType;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 方法参数
     */
    private String methodArgs;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求 url
     */
    private String requestUrl;

    /**
     * 请求IP
     */
    private String requestIp;

    /**
     * 操作真实地址
     */
    private String address;

    /**
     * 响应结果
     */
    private String responseResult;

    /**
     * 操作状态（ 200.正常 其他.异常 ）
     */
    private Integer status;

    /**
     * 错误日志
     */
    private String errorLog;

    /**
     * 耗时
     */
    private Long timeCost;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
