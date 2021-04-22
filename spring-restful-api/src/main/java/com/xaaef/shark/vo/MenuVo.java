package com.xaaef.shark.vo;

import lombok.*;

import java.util.List;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 菜单权限
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MenuVo implements java.io.Serializable {

    private Integer pid;

    private Integer parentId;

    private String icon;

    private String resources;

    private String title;

    private List<MenuVo> children;

}
