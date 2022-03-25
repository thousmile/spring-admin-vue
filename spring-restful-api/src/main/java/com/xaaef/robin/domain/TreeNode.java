package com.xaaef.robin.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * <p>
 * 树节点
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 9:31
 */

public interface TreeNode<T extends TreeNode> extends java.io.Serializable {

    Long getId();

    Long getParentId();

    Integer getSort();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<T> getChildren();

    void setChildren(List<T> list);

}
