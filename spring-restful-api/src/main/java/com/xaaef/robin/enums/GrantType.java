package com.xaaef.robin.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

/**
 * <p>
 * password : 密码模式
 * we_chat : 微信登陆模式
 * tencent_qq : 腾讯 QQ 登陆模式
 * sms : 手机短信模式
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/5 9:31
 */

@Getter
@ToString
public enum GrantType {

    PASSWORD("password", "密码 登录模式"),

    WECHAT("we_chat", "微信登录 模式"),

    TENCENT_QQ("tencent_qq", "腾讯QQ登录 模式"),

    SEND_SMS("send_sms", "发送短信"),

    SMS("sms", "手机短信登录 模式");

    GrantType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue
    private final String code;

    private final String description;

    @JsonCreator
    public static GrantType get(String value) {
        for (var v : values()) {
            if (v.code.equalsIgnoreCase(value)) {
                return v;
            }
        }
        return PASSWORD;
    }

}
