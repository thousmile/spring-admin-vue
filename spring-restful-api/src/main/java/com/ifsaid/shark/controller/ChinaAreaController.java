package com.ifsaid.shark.controller;

import com.github.pagehelper.Page;
import com.ifsaid.shark.common.domain.TreeNode;
import com.ifsaid.shark.entity.ChinaArea;
import com.ifsaid.shark.service.ChinaAreaService;
import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.util.TreeNodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 中国行政地区 Controller
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/25 22:13
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
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
        log.info("get ID : {}", id);
        return JsonResult.success(baseService.findById(id));
    }

    @ApiOperation(value = "查询所有", notes = "查询所有")
    @GetMapping("/all/{parentCode}")
    public JsonResult<List<ChinaArea>> findAll(@PathVariable Long parentCode) {
        if (parentCode == null || parentCode < 1) {
            parentCode = 0L;
        }
        List<ChinaArea> all = baseService.findAll(ChinaArea.builder().parentCode(parentCode).build());
        return JsonResult.success(all);
    }

    @ApiOperation(value = "查询所有[树节点]", notes = "以树节点的形式展示")
    @GetMapping("/tree")
    public JsonResult<Page<ChinaArea>> tree() {
        List<TreeNode> collect = baseService.findAll(null)
                .stream()
                .distinct()
                .map(res -> new TreeNode(res.getAreaCode(), res.getName(), res.getParentCode(), res, null))
                .collect(Collectors.toList());
        return JsonResult.success(TreeNodeUtils.findRoots(collect));
    }


}
