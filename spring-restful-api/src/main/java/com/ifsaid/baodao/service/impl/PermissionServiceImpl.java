package com.ifsaid.baodao.service.impl;

import com.ifsaid.baodao.common.service.impl.BaseServiceImpl;
import com.ifsaid.baodao.entity.Permission;
import com.ifsaid.baodao.repository.PermissionRepository;
import com.ifsaid.baodao.service.IPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:27
 * @describe： 权限Service 实现类
 * @version: 1.0
 */

@Slf4j
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Integer, PermissionRepository> implements IPermissionService {

    @Override
    public Set<Permission> findAllByUserId(String uid) {
        return baseRepository.findAllByUserId(uid);
    }

    @Override
    public Set<Integer> findPermissionByRoleId(Integer rid) {
        return baseRepository.findPermissionByRoleId(rid);
    }

    @Override
    public Set<Permission> findAllTree() {
        return findRoots(findAll());
    }

    /**
     * 查找所有根节点
     *
     * @param allNodes
     * @return
     */
    private Set<Permission> findRoots(List<Permission> allNodes) {
        // 根节点
        Set<Permission> root = new HashSet<>();
        allNodes.forEach(node -> {
            if (node.getParentId() == 0) {
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
    private Permission findChildren(Permission treeNode, List<Permission> treeNodes) {
        for (Permission it : treeNodes) {
            if (treeNode.getPid().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new HashSet<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }
}
