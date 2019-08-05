package com.ifsaid.baodao.repository;

import com.ifsaid.baodao.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 13:54
 * @describe： 文章dao接口
 * @version: 1.0
 */

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "select a from Article as a where a.state = 1 and a.categoryId =:categoryId ")
    Page<Article> findAllByCategoryId(Pageable page, @Param("categoryId") Integer categoryId);

    @Query(value = "select a from Article as a where a.state = 1 and concat(a.title,a.introduce) like concat('%',:search,'%') ")
    Page<Article> findAllBySearch(Pageable page, @Param("search") String search);

}
