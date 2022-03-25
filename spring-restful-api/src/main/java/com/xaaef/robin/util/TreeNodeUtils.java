package com.xaaef.robin.util;

import com.xaaef.robin.domain.TreeNode;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 将 List 数据递归成，树节点的形式
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 9:31
 */


@Slf4j
public class TreeNodeUtils {

    private final static Long ROOT = 0L;

    /**
     * 查找所有根节点
     *
     * @param allNodes
     * @return Set<TreeNode>
     */
    public static <T extends TreeNode<T>> List<T> findRoots(List<T> allNodes) {
        return allNodes.stream().filter(node -> node.getParentId().equals(ROOT))
                .map(node -> findChildren(node, allNodes))
                .sorted(TreeNodeUtils::comparator)
                .collect(Collectors.toList());
    }


    /**
     * 递归查找子节点
     *
     * @param treeNode
     * @param treeNodes
     * @return TreeNode
     */
    private static <T extends TreeNode<T>> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        // 排序
        if (treeNode.getChildren() != null && treeNode.getChildren().size() > 1) {
            treeNode.getChildren().sort((Comparator<TreeNode<T>>) TreeNodeUtils::comparator);
        }
        return treeNode;
    }


    /**
     * 比较
     *
     * @param o1
     * @param o2
     * @return TreeNode
     */
    private static <T extends TreeNode<T>> int comparator(TreeNode<T> o1, TreeNode<T> o2) {
        int temp = o1.getSort() - o2.getSort();
        return Integer.compare(temp, 0);
    }

}
