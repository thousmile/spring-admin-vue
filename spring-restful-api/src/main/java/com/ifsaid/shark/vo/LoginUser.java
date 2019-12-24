package com.ifsaid.shark.vo;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 用户登录，参数传递
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
public class LoginUser implements java.io.Serializable {

    @NotNull(message = "用户名不能为空")
    @Length(min = 5, message = "用户名长度不能少于五位")
    private String username;

    @NotNull(message = "密码不能为空")
    @Length(min = 5, message = "密码长度不能少于五位")
    private String password;

    @NotNull(message = "验证码不能为空")
    @Length(min = 4, max = 4, message = "验证码长度是四位")
    private String codeText;

    @NotNull(message = "验证码 KEY 不能为空")
    private String codeKey;

}
