package com.xaaef.robin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * <p>
 * ID 可以是 角色ID，部门ID
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/23 15:14
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BindingPermsVO implements java.io.Serializable {

    @NotNull(message = "ID不能为空!")
    private Long id;

    @NotNull(message = "权限列表不能为空!")
    @Size(min = 1, message = "权限列表最少是一个!")
    private Set<Long> items;

}
