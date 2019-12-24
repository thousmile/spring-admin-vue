package com.ifsaid.shark.entity;

import com.ifsaid.shark.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 后台系统 权限
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Entity
@Table(name = "tb_sys_permission")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysPermission extends BaseEntity implements java.io.Serializable {

    /**
     * @description: 权限 唯一ID
     * @date: 2019/12/11 22:15
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;

    /**
     * @description: 权限 上级权限ID
     * @date: 2019/12/11 22:15
     */
    private Integer parentId;

    /**
     * @description: 权限 全局资源标识符
     * @date: 2019/12/11 22:15
     */
    private String resources;

    /**
     * @description: 权限 标题
     * @date: 2019/12/11 22:15
     */
    private String title;

    /**
     * @description: 权限，如果是菜单的话，那么就是图标名称。如果是按钮，可以不赋值
     * @date: 2019/12/11 22:15
     */
    private String icon;

    /**
     * @description: 权限，button 或者 menu 只能 二选一
     * @date: 2019/12/11 22:15
     */
    private String type;

    /**
     * @description: 权限 描述
     * @date: 2019/12/11 22:15
     */
    private String description;

}