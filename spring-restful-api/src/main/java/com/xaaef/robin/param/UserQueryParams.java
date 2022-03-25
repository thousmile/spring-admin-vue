package com.xaaef.robin.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * <p>
 * 用户查询的参数
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 */

@Getter
@Setter
@ToString
public class UserQueryParams extends QueryParams {

    /**
     * 角色 ID
     */
    private Long roleId;

    /**
     * 部门 ID
     */
    private Long deptId;

}
