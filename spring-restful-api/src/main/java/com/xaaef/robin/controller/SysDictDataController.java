package com.xaaef.robin.controller;

import com.xaaef.robin.aspect.log.LogType;
import com.xaaef.robin.aspect.log.OperateLog;
import com.xaaef.robin.domain.Pagination;
import com.xaaef.robin.entity.SysDictData;
import com.xaaef.robin.param.DictQueryParams;
import com.xaaef.robin.service.SysDictDataService;
import com.xaaef.robin.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典数据 控制器
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/8 10:15
 */

@Slf4j
@Api(tags = "[ 通用 ] 字典数据")
@RestController
@RequestMapping("/dict/data")
@AllArgsConstructor
public class SysDictDataController {

    private SysDictDataService baseService;

    @ApiOperation(value = "单个查询", notes = "根据Id查询")
    @GetMapping("/{id}")
    public JsonResult<SysDictData> findById(@PathVariable("id") Integer id) {
        return JsonResult.success(baseService.getById(id));
    }


    @ApiOperation(value = "分页", notes = "分页 查询所有")
    @GetMapping("/query")
    public JsonResult<Pagination<SysDictData>> pageQuery(DictQueryParams params) {
        var page = baseService.pageKeywords(params);
        return JsonResult.success(page.getTotal(), page.getRecords());
    }


    @ApiOperation(value = "根据Type Key查询所有", notes = "根据字典type查询所有")
    @GetMapping("/type/{key}")
    public JsonResult<List<SysDictData>> findTypeKey(@PathVariable("key") String key) {
        return JsonResult.success(baseService.list(SysDictData::getTypeKey, key));
    }


    @ApiOperation(value = "新增", notes = "不需要添加id")
    @OperateLog(title = "[字典数据] 新增", type = LogType.INSERT)
    @PostMapping()
    public JsonResult create(@RequestBody SysDictData entity) {
        baseService.save(entity);
        return JsonResult.success(entity);
    }


    @ApiOperation(value = "修改", notes = "修改必须要id")
    @OperateLog(title = "[字典数据] 修改", type = LogType.UPDATE)
    @PutMapping()
    public JsonResult<Boolean> update(@RequestBody SysDictData entity) {
        boolean b = baseService.updateById(entity);
        return JsonResult.success(b);
    }


    @ApiOperation(value = "删除", notes = "只需要id即可")
    @OperateLog(title = "[字典数据] 删除", type = LogType.DELETE)
    @DeleteMapping("/{id}")
    public JsonResult<Boolean> delete(@PathVariable("id") Integer id) {
        boolean b = baseService.removeById(id);
        return JsonResult.success(b);
    }

}
