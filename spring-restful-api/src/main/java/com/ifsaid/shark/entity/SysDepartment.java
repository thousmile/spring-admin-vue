package com.ifsaid.shark.entity;

import com.ifsaid.shark.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 后台系统 部门
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Entity
@Table(name = "tb_sys_department")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysDepartment extends BaseEntity implements java.io.Serializable {

    /**
     * @description: 部门 ID
     * @date: 2019/12/11 22:15
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * @description: 部门 名称
     * @date: 2019/12/11 22:15
     */
    private String name;

    /**
     * @description: 部门 名称
     * @date: 2019/12/11 22:15
     */
    private Integer parentId;

    /**
     * @description: 部门排序
     * @date: 2019/12/11 22:15
     */
    private Integer level;

    /**
     * @description: 部门 描述
     * @date: 2019/12/11 22:15
     */
    private String description;

}