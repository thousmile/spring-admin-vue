package com.xaaef.robin.controller;

import com.xaaef.robin.aspect.log.LogType;
import com.xaaef.robin.aspect.log.OperateLog;
import com.xaaef.robin.domain.Pagination;
import com.xaaef.robin.entity.SysDictType;
import com.xaaef.robin.param.QueryParams;
import com.xaaef.robin.service.SysDictTypeService;
import com.xaaef.robin.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典类型 控制器
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/8 10:15
 */

@Slf4j
@Api(tags = "[ 通用 ] 字典类型")
@RestController
@RequestMapping("/dict/type")
@AllArgsConstructor
public class SysDictTypeController {

    private SysDictTypeService baseService;

    @ApiOperation(value = "单个查询", notes = "根据Id查询")
    @GetMapping("/{id}")
    public JsonResult<SysDictType> findById(@PathVariable("id") Integer id) {
        return JsonResult.success(baseService.getById(id));
    }


    @ApiOperation(value = "查询所有", notes = "查询所有")
    @GetMapping("/all")
    public JsonResult<List<SysDictType>> findAll() {
        return JsonResult.success(baseService.list());
    }


    @ApiOperation(value = "分页", notes = "分页 查询所有")
    @GetMapping("/query")
    public JsonResult<Pagination<SysDictType>> pageQuery(QueryParams params) {
        var page = baseService.pageKeywords(
                params,
                SysDictType::getTypeName,
                SysDictType::getDescription
        );
        return JsonResult.success(page.getTotal(), page.getRecords());
    }


    @ApiOperation(value = "新增", notes = "不需要添加id")
    @OperateLog(title = "[字典类型]新增", type = LogType.INSERT)
    @PostMapping()
    public JsonResult create(@RequestBody SysDictType entity) {
        baseService.save(entity);
        return JsonResult.success(entity);
    }


    @ApiOperation(value = "修改", notes = "修改必须要id")
    @OperateLog(title = "[字典类型]修改", type = LogType.UPDATE)
    @PutMapping()
    public JsonResult<Boolean> update(@RequestBody SysDictType entity) {
        boolean b = baseService.updateById(entity);
        return JsonResult.success(b);
    }


    @ApiOperation(value = "删除", notes = "只需要id即可")
    @OperateLog(title = "[字典类型]删除", type = LogType.DELETE)
    @DeleteMapping("/{id}")
    public JsonResult<Boolean> delete(@PathVariable("id") Integer id) {
        boolean b = baseService.removeById(id);
        return JsonResult.success(b);
    }


}
