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
public class ResetPassword {

    /**
     * 用户ID
     *
     * @date 2021/5/21 15:43
     */
    @NotNull(message = "用户ID不能为空!")
    private Integer uid;

    /**
     * 新密码
     *
     * @date 2021/5/21 15:43
     */
    @NotNull(message = "新密码不能为空!")
    @Length(min = 5, message = "新密码长度要大于5位!")
    private String newPwd;

}
