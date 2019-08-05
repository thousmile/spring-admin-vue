package com.ifsaid.baodao.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/17 16:11
 * @describe：
 * @version: 1.0
 */

@Data
public class LoginUser {

    @NotNull(message = "用户名不能为空")
    @Length(min = 4, message = "用户名长度不能少于四位")
    private String username;

    @NotNull(message = "密码不能为空")
    @Length(min = 4, message = "密码长度不能少于四位")
    private String password;

}
