package com.xaaef.robin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * [ 通用 ] 参数配置表
 *
 * @TableName sys_config
 */

@TableName(value = "sys_config")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysConfig extends BaseEntity implements Serializable {
    /**
     * 参数主键
     */
    @TableId(type = IdType.AUTO)
    private Long configId;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数键名
     */
    private String configKey;

    /**
     * 参数键值
     */
    private String configValue;

    /**
     * 系统内置（1.是 0.否）
     */
    private Integer configType;


}
