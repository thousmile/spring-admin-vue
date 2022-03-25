package com.xaaef.robin.vo;

import com.xaaef.robin.domain.TreeNode;
import lombok.*;

import java.util.List;

/**
 * <p>
 * 菜单权限
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MenuVO implements java.io.Serializable, TreeNode<MenuVO> {

    private Long id;

    private Long parentId;

    private String icon;

    private String perms;

    private Integer sort;

    private String title;

    private List<MenuVO> children;

}
