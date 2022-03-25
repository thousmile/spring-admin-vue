package com.xaaef.robin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;


/**
 * <p>
 * 0.普通用户
 * 1.管理员 ( 管理员是禁止被删除 )
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 9:31
 */


@Getter
@ToString
public enum AdminFlagEnum {

    USER(0, "用户"),

    ADMIN(1, "管理员");

    AdminFlagEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @EnumValue
    @JsonValue
    private final int code;

    private final String description;

    /**
     * 用于 spring mvc 实体参数绑定
     */
    @JsonCreator
    public static AdminFlagEnum create(Integer value) {
        for (var v : values()) {
            if (v.code == value) {
                return v;
            }
        }
        return USER;
    }

}
