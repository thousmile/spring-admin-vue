package com.xaaef.robin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

/**
 * <p>
 * 性别
 * 0.女
 * 1.男
 * 2.未知
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 9:31
 */

@Getter
@ToString
public enum GenderEnum {

    FEMALE(0, "女"),

    MALE(1, "男"),

    UNKNOWN(2, "未知");

    GenderEnum(int code, String description) {
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
    public static GenderEnum create(Integer value) {
        for (var v : values()) {
            if (v.code == value) {
                return v;
            }
        }
        return UNKNOWN;
    }

}
