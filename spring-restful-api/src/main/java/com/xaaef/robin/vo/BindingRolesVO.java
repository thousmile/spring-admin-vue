package com.xaaef.robin.vo;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * <p>
 * 用户绑定角色
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/9 17:46
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BindingRolesVO {

    @NotNull(message = "用户ID不能为空!")
    private Long userId;

    @NotNull(message = "角色不能为空!")
    @Size(min = 1, message = "角色最少是一个!")
    private Set<Long> roles;

}
