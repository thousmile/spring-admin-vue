package com.xaaef.robin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

/**
 * <p>
 * 默认
 * 1.是
 * 0.否
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 9:31
 */

@Getter
@ToString
public enum DefaultEnum {

    YES(1, "是"),

    NO(0, "否");

    DefaultEnum(int code, String description) {
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
    public static DefaultEnum create(Integer value) {
        for (var v : values()) {
            if (v.code == value) {
                return v;
            }
        }
        return NO;
    }


}
