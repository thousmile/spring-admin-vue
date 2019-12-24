package com.ifsaid.shark.vo;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 登录成功后，用户详细 Vo 模型
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/12 23:29
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements java.io.Serializable {

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
     * 性别
     *
     * @date: 2019/12/11 22:15
     */
    private Integer gender;

    /**
     * 部门名称
     *
     * @date: 2019/12/11 22:15
     */
    private String departmentName;

    /**
     * 角色名称，列表
     *
     * @date: 2019/12/11 22:15
     */
    private Set<String> roles;

    /**
     * 生日
     *
     * @date: 2019/12/11 22:15
     */
    private LocalDate birthday;

    /**
     * @describe 按钮
     * @date 2018/10/29
     * @author Wang Chen Chen
     */
    private List<ButtonVo> buttons;

    /**
     * @describe 菜单
     * @date 2018/10/29
     * @author Wang Chen Chen
     */
    private List<MenuVo> menus;

}
