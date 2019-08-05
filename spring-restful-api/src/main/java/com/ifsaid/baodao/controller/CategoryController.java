package com.ifsaid.baodao.controller;

import com.ifsaid.baodao.common.controller.BaseController;
import com.ifsaid.baodao.entity.Category;
import com.ifsaid.baodao.service.ICategoryService;
import com.ifsaid.baodao.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:49
 * @describe：
 * @version: 1.0
 */

@Api(tags = "类目管理")
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController<Category, Integer, ICategoryService> {

    @ApiOperation(value = "查询所有", notes = "根据省份简称查询出所有的类目")
    @GetMapping("/all/{simple}")
    public Result<List<Category>> findAll(@PathVariable("simple") String simple) {
        return Result.success(baseService.findAllByRegion(simple));
    }


}
