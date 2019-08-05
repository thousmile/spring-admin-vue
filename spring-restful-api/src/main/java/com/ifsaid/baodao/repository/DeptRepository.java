package com.ifsaid.baodao.repository;

import com.ifsaid.baodao.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 13:54
 * @describe： 部门dao接口
 * @version: 1.0
 */

public interface DeptRepository extends JpaRepository<Dept, Integer> {

    /**
     *@describe 根据父部门查询出所有子部门
     *@date 2018/11/16
     *@author  Wang Chen Chen
     */
    int countByParentId(Integer parentId);

}
