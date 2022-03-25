package com.xaaef.robin.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * 通用父实体类
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 9:31
 */

@Getter
@Setter
public class BaseEntity implements java.io.Serializable {

    /**
     * 创建时间
     *
     * @date 2019/12/11 21:12
     */
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    /**
     * 创建人 id
     *
     * @date 2019/12/11 21:12
     */
    @TableField(fill = FieldFill.INSERT)
    protected Long createUser;

    /**
     * 最后一次修改时间
     *
     * @date 2019/12/11 21:12
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime lastUpdateTime;

    /**
     * 最后一次修改人 id
     *
     * @date 2019/12/11 21:12
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Long lastUpdateUser;

}
