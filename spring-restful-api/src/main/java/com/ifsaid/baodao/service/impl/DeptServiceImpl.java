package com.ifsaid.baodao.service.impl;

import com.ifsaid.baodao.common.exception.JpaCrudException;
import com.ifsaid.baodao.common.service.impl.BaseServiceImpl;
import com.ifsaid.baodao.entity.Dept;
import com.ifsaid.baodao.repository.DeptRepository;
import com.ifsaid.baodao.service.IDeptService;
import com.ifsaid.baodao.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:27
 * @describe： 部门Service 实现类
 * @version: 1.0
 */

@Slf4j
@Service
public class DeptServiceImpl extends BaseServiceImpl<Dept, Integer, DeptRepository> implements IDeptService {

    @Autowired
    protected IUserService userService;

    /**
     * @describe 删除部门
     * @parameter [Integer]
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void deleteById(Integer integer) throws JpaCrudException {
        if (baseRepository.countByParentId(integer) > 0) {
            throw new JpaCrudException("这个部门下还有子部门，无法删除！");
        }
        if (userService.countByDept(integer) > 0) {
            throw new JpaCrudException("这个部门下还有用户，无法删除！");
        }
        baseRepository.deleteById(integer);
    }

    /**
     * 查找所有根节点
     *
     * @param allNodes
     * @return
     */
    private Set<Dept> findRoots(List<Dept> allNodes) {
        // 根节点
        Set<Dept> root = new HashSet<>();
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
    private Dept findChildren(Dept treeNode, List<Dept> treeNodes) {
        for (Dept it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new HashSet<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }


    @Override
    public Set<Dept> findAllTree() {
        List<Dept> deptList = baseRepository.findAll(Sort.by(Sort.Direction.DESC, "upTime"));
        return findRoots(deptList);
    }

}
