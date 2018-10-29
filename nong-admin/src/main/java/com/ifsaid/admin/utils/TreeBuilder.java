package com.ifsaid.admin.utils;


import com.ifsaid.admin.entity.SysUser;
import com.ifsaid.admin.vo.MenuVo;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/29 16:16
 * @describe：
 * @version: 1.0
 */

public class TreeBuilder {

    public static Set<MenuVo> findRoots(Set<MenuVo> allNodes) {
        // 根节点
        Set<MenuVo> root = new HashSet<>();
        allNodes.forEach(node -> {
            if (node.getFather() == 0) {
                root.add(node);
            }
        });
        root.forEach(node -> {
            findChildren(node, allNodes);
        });
        return root;
    }


    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    private static MenuVo findChildren(MenuVo treeNode, Set<MenuVo> treeNodes) {
        for (MenuVo it : treeNodes) {
            if (treeNode.getPid().equals(it.getFather())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<MenuVo>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }


    public static void main(String[] args) {
        Set<MenuVo> set = new HashSet<>();
        set.addAll(Arrays.asList(
                new MenuVo(1, 0, "一级", "", ""),
                new MenuVo(2, 0, "一级", "", ""),
                new MenuVo(3, 1, "二级", "", ""),
                new MenuVo(4, 1, "二级", "", ""),
                new MenuVo(4, 2, "二级", "", ""),
                new MenuVo(6, 2, "二级", "", ""),
                new MenuVo(7, 3, "三级", "", ""),
                new MenuVo(8, 4, "三级", "", ""),
                new MenuVo(9, 5, "三级", "", ""),
                new MenuVo(10, 6, "三级", "", ""),
                new MenuVo(11, 6, "三级", "", "")
        ));

        for (MenuVo menuVo : findRoots(set)) {
            System.out.println(menuVo);
        }

    }


}