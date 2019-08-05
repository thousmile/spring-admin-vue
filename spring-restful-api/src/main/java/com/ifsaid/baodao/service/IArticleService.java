package com.ifsaid.baodao.service;

import com.ifsaid.baodao.common.service.IBaseService;
import com.ifsaid.baodao.entity.Article;
import com.ifsaid.baodao.vo.MyPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:24
 * @describe：
 * @version: 1.0
 */

public interface IArticleService extends IBaseService<Article, Long> {

    // 根据类目分页
    Page<Article> findAll(MyPage page);



}
