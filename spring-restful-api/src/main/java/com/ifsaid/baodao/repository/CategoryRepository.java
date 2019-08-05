package com.ifsaid.baodao.repository;

import com.ifsaid.baodao.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 13:54
 * @describe： 文章类目  dao 接口
 * @version: 1.0
 */

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     *@describe  根据省份获取类目
     *@date 2018/11/16
     *@author  Wang Chen Chen
     */
    List<Category> findAllByRegion(String region);


}
