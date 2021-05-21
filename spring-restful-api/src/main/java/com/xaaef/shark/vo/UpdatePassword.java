package com.xaaef.shark.vo;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdatePassword {
    /**
     * 用户ID
     *
     * @date 2021/5/21 15:43
     */
    @NotNull(message = "用户ID不能为空!")
    private Integer uid;

    /**
     * 旧密码
     *
     * @date 2021/5/21 15:43
     */
    @NotNull(message = "旧密码不能为空!")
    @Length(min = 5, message = "旧密码长度要大于5位!")
    private String oldPwd;

    /**
     * 新密码
     *
     * @date 2021/5/21 15:43
     */
    @NotNull(message = "新密码不能为空!")
    @Length(min = 5, message = "新密码长度要大于5位!")
    private String newPwd;

    /**
     * 确认密码
     *
     * @date 2021/5/21 15:43
     */
    @NotNull(message = "确认密码不能为空!")
    @Length(min = 5, message = "确认密码长度要大于5位!")
    private String confirmPwd;

}
