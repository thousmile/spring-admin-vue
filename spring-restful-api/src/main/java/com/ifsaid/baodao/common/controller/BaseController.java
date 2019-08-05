package com.ifsaid.baodao.common.controller;

import com.ifsaid.baodao.common.entity.BaseEntity;
import com.ifsaid.baodao.common.service.IBaseService;
import com.ifsaid.baodao.vo.MyPage;
import com.ifsaid.baodao.vo.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:39
 * @describe： 通用 增删改查 控制器
 * @version: 1.0
 */

@Slf4j
public class BaseController<T extends BaseEntity, ID, S extends IBaseService<T, ID>> {

    @Autowired
    protected S baseService;

    @ApiOperation(value = "单个查询", notes = "根据Id查询")
    @GetMapping("/{id}")
    public Result<T> findById(@PathVariable("id") ID id) {
        log.info("get ID : {}", id);
        return Result.success(baseService.findById(id));
    }

    @ApiOperation(value = "查询所有", notes = "查询所有")
    @GetMapping("/all")
    public Result<List<T>> findAll() {
        return Result.success(baseService.findAll());
    }

    @ApiOperation(value = "分页", notes = "分页 查询所有")
    @GetMapping("/page")
    public Result<Page<T>> findAll(MyPage page) {
        log.info("page :  {} size : {}", page.getPage(), page.getSize());
        PageRequest rageRequest = PageRequest.of(page.getPage() - 1, page.getSize(), Sort.by(Sort.Direction.DESC, "upTime"));
        return Result.success(baseService.findAll(rageRequest));
    }

    @ApiOperation(value = "新增", notes = "不需要添加id")
    @PostMapping()
    public Result<T> save(@RequestBody T entity) {
        log.info("save :  {}", entity);
        return Result.success(baseService.save(entity));
    }

    @ApiOperation(value = "修改", notes = "修改必须要id")
    @PutMapping()
    public Result<T> update(@RequestBody T entity) {
        log.info("update:  {}", entity);
        return Result.success(baseService.update(entity));
    }

    @ApiOperation(value = "删除", notes = "只需要id即可")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable("id") ID id) {
        log.info("delete:  {}", id);
        baseService.deleteById(id);
        return Result.success();
    }

}
