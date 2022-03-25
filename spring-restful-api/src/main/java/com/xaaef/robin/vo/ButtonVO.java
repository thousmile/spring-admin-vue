package com.xaaef.robin.vo;

import lombok.*;


/**
 * <p>
 * 用户登录成功后，返回的按钮权限
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 */


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ButtonVO implements java.io.Serializable {

    private String perms;

    private String title;

}
