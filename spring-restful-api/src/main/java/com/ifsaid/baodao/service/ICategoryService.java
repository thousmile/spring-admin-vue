package com.ifsaid.baodao.service;

import com.ifsaid.baodao.common.service.IBaseService;
import com.ifsaid.baodao.entity.Article;
import com.ifsaid.baodao.entity.Category;

import java.util.List;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:24
 * @describe：
 * @version: 1.0
 */

public interface ICategoryService extends IBaseService<Category, Integer> {

    /**
     *@describe  根据省份获取类目
     *@date 2018/11/16
     *@author  Wang Chen Chen
     */
    List<Category> findAllByRegion(String region);

}
