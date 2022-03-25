package com.xaaef.robin.enums;

import lombok.Getter;

/**
 * <p>
 * 认证状态
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/5 9:31
 */

@Getter
public enum OAuth2Error {

    /**
     * 认证错误，不知道啥原因
     */
    OAUTH2_EXCEPTION(400004, "认证错误!"),


    /**
     * access_token 不存在
     */
    ACCESS_TOKEN_INVALID(400010, "access_token 不存在"),


    /**
     * access_token 过期
     */
    ACCESS_TOKEN_EXPIRED(400011, "access_token 已过期"),


    /**
     * token 格式错误
     */
    TOKEN_FORMAT_ERROR(400012, "access_token 格式错误"),


    /**
     * 用户不存在
     */
    USER_INVALID(400020, "用户或者密码错误"),


    /**
     * 当前用户被锁定
     */
    USER_LOCKING(400023, "此用户被锁定"),

    /**
     * 用户手机号不存在
     */
    USER_MOBILE_INVALID(400024, "用户手机号不存在"),

    /**
     * 授权类型错误
     */
    AUTHORIZATION_GRANT_TYPE(400033, "授权类型错误"),

    /**
     * 请求参数解析错误
     */
    REQUEST_PARAM_VALIDATE(400044, "请求参数解析错误"),

    /**
     * 验证码错误
     */
    VERIFICATION_CODE_ERROR(400045, "验证码错误"),

    /**
     * 权限不足
     */
    ACCESS_DENIED(400061, "请求访问，权限不足");

    OAuth2Error(Integer status, String error) {
        this.status = status;
        this.error = error;
    }

    /**
     * 状态信息
     */
    private final Integer status;

    /**
     * 错误消息
     */
    private final String error;

}
