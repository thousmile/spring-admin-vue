package com.ifsaid.baodao.controller;

import com.ifsaid.baodao.common.controller.BaseController;
import com.ifsaid.baodao.entity.Article;
import com.ifsaid.baodao.service.IArticleService;
import com.ifsaid.baodao.vo.MyPage;
import com.ifsaid.baodao.vo.Result;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:49
 * @describe：
 * @version: 1.0
 */

@Api(tags = "文章管理")
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController<Article, Long, IArticleService> {

    @GetMapping("/list")
    @Override
    public Result<Page<Article>> findAll(MyPage page) {
        return Result.success(baseService.findAll(page));
    }

}
