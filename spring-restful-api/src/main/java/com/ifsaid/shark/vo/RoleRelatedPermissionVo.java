package com.ifsaid.shark.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 用户关联角色
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/16 21:14
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Getter
@Setter
@ToString
public class RoleRelatedPermissionVo implements java.io.Serializable {

    @NotNull(message = "角色ID不能为空!")
    private Integer rid;

    @NotNull(message = "权限ID列表,不能为空!")
    @Size(min = 1, message = "角色权限最少赋值一个")
    private Set<Integer> permissions;

}
