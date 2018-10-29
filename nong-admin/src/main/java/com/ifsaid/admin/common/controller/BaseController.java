package com.ifsaid.admin.common.controller;

import com.github.pagehelper.PageInfo;
import com.ifsaid.admin.common.service.IBaseService;
import com.ifsaid.admin.vo.MyPage;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/23 14:16
 * @describe：
 * @version: 1.0
 */

@Slf4j
public class BaseController<T, ID, S extends IBaseService<T, ID>> {

    @Autowired
    protected S baseService;

    @ApiOperation(value = "单个查询", notes = "根据Id查询")
    @GetMapping("/{id}")
    public T get(@PathVariable("id") ID id) {
        log.info("get ID : {}", id);
        return baseService.selectByPrimaryKey(id);
    }

    @ApiOperation(value = "查询所有", notes = "分页查询所有")
    @GetMapping("/page")
    public PageInfo<T> paging(MyPage page) {
        log.info("page :{} size : {}", page.getPageNum(), page.getPageSize());
        return baseService.selectList(page);
    }

    @ApiOperation(value = "修改", notes = "修改必须要id")
    @PostMapping("/")
    public Integer update(@RequestBody T entity) {
        log.info("update :{}", entity);
        return baseService.updateByPrimaryKey(entity);
    }

    @ApiOperation(value = "删除", notes = "只需要类型的id即可")
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable("id") ID id) {
        log.info("delete ID :{}", id);
        return baseService.deleteByPrimaryKey(id);
    }

    @ApiOperation(value = "新增", notes = "不需要添加id")
    @PutMapping("/")
    public Integer save(@RequestBody T entity) {
        return baseService.insertSelective(entity);
    }

}
