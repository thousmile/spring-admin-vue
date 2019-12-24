package com.ifsaid.shark.vo;

import lombok.*;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 用户登录成功后，返回的按钮权限
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ButtonVo implements java.io.Serializable {

    private Integer pid;

    private String resources;

    private String title;


}
