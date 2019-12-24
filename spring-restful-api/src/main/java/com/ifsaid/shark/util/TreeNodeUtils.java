package com.ifsaid.shark.util;

import com.ifsaid.shark.common.domain.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 将List<>数据递归成，树节点的形式
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/12 21:27
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public class TreeNodeUtils {

    private final static int ROOT = 0;

    /**
     * 查找所有根节点
     *
     * @param allNodes
     * @return Set<TreeNode>
     */
    public static List<TreeNode> findRoots(List<TreeNode> allNodes) {
        // 根节点
        List<TreeNode> root = new ArrayList<>();
        allNodes.forEach(node -> {
            if (node.getParentId() == ROOT) {
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
     * @param treeNode
     * @param treeNodes
     * @return TreeNode
     */
    private static TreeNode findChildren(TreeNode treeNode, List<TreeNode> treeNodes) {
        for (TreeNode it : treeNodes) {
            if (treeNode.getId() == it.getParentId()) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

}
