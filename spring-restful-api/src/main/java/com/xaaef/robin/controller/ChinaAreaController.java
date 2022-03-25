package com.xaaef.robin.controller;

import com.xaaef.robin.domain.Pagination;
import com.xaaef.robin.entity.ChinaArea;
import com.xaaef.robin.param.QueryParams;
import com.xaaef.robin.service.ChinaAreaService;
import com.xaaef.robin.util.JsonResult;
import com.xaaef.robin.util.TreeNodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * <p>
 * 中国行政地区 Controller
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/25 22:13
 */


@Slf4j
@Api(tags = "[ 通用 ] 中国行政地区")
@RestController
@RequestMapping("/china/area")
public class ChinaAreaController {

    @Autowired
    private ChinaAreaService baseService;

    @ApiOperation(value = "单个查询", notes = "根据Id查询")
    @GetMapping("/{id}")
    public JsonResult<ChinaArea> findById(@PathVariable("id") Long id) {
        return JsonResult.success(baseService.getById(id));
    }


    @ApiOperation(value = "分页查询", notes = "根据关键字搜索")
    @GetMapping("/query")
    public JsonResult<Pagination<ChinaArea>> query(QueryParams params) {
        var all = baseService.pageKeywords(params);
        return JsonResult.success(all.getTotal(), all.getRecords());
    }


    @ApiOperation(value = "查询所有", notes = "根据父节点查询所有子节点")
    @GetMapping("/list/{parentCode}")
    public JsonResult<List<ChinaArea>> findAll(@PathVariable Long parentCode) {
        if (parentCode == null || parentCode < 1) {
            parentCode = 0L;
        }
        var all = baseService.list(ChinaArea::getParentCode, parentCode);
        return JsonResult.success(all);
    }


    @ApiOperation(value = "查询所有[树节点]", notes = "以树节点的形式展示")
    @GetMapping("/tree")
    public JsonResult<List<ChinaArea>> tree() {
        var roots = TreeNodeUtils.findRoots(baseService.list());
        return JsonResult.success(roots);
    }


}
