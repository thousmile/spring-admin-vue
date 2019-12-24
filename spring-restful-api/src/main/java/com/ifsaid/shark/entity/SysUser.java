package com.ifsaid.shark.entity;

import com.ifsaid.shark.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 后台系统用户
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Entity
@Table(name = "tb_sys_user")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysUser extends BaseEntity implements java.io.Serializable {

    /**
     * 用户唯一id
     *
     * @date: 2019/12/11 22:15
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    /**
     * 头像
     *
     * @date: 2019/12/11 22:15
     */
    private String avatar;

    /**
     * 用户名
     *
     * @date: 2019/12/11 22:15
     */
    private String username;

    /**
     * 邮箱
     *
     * @date: 2019/12/11 22:15
     */
    private String email;

    /**
     * 昵称
     *
     * @date: 2019/12/11 22:15
     */
    private String nickname;

    /**
     * 密码
     *
     * @date: 2019/12/11 22:15
     */
    private String password;

    /**
     * 性别
     *
     * @date: 2019/12/11 22:15
     */
    private Integer gender;

    /**
     * 生日
     *
     * @date: 2019/12/11 22:15
     */
    private LocalDate birthday;

    /**
     * 部门 Id
     *
     * @date: 2019/12/11 22:15
     */
    private Integer deptId;

    /**
     * 状态 [ 0.禁用 1.正常 2.被删除 ]
     *
     * @date: 2019/12/11 22:15
     */
    private Integer status;

}