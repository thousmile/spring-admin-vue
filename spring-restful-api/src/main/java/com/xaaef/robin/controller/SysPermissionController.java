package com.xaaef.robin.controller;

import com.xaaef.robin.aspect.log.LogType;
import com.xaaef.robin.aspect.log.OperateLog;
import com.xaaef.robin.domain.Pagination;
import com.xaaef.robin.entity.SysPermission;
import com.xaaef.robin.enums.PermissionTypeEnum;
import com.xaaef.robin.param.QueryParams;
import com.xaaef.robin.service.SysPermissionService;
import com.xaaef.robin.util.JsonResult;
import com.xaaef.robin.util.TreeNodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * 菜单 控制器
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/8 10:15
 */

@Slf4j
@Api(tags = "[ 权限 ] 权限管理")
@RestController
@RequestMapping("permission")
@AllArgsConstructor
public class SysPermissionController {

    private SysPermissionService baseService;

    @ApiOperation(value = "单个查询", notes = "根据Id查询")
    @GetMapping("/{id}")
    public JsonResult<SysPermission> findById(@PathVariable("id") Integer id) {
        return JsonResult.success(baseService.getById(id));
    }


    @ApiOperation(value = "分页", notes = "分页 查询所有")
    @GetMapping("/query")
    public JsonResult<Pagination<SysPermission>> pageQuery(QueryParams params) {
        var page = baseService.pageKeywords(
                params,
                SysPermission::getTitle, SysPermission::getPerms
        );
        return JsonResult.success(page.getTotal(), page.getRecords());
    }


    @ApiOperation(value = "查询所有", notes = "查询所有")
    @GetMapping("/all")
    public JsonResult<List<SysPermission>> all() {
        List<SysPermission> list = baseService.list();
        list.sort(Comparator.comparing(SysPermission::getSort));
        return JsonResult.success(list);
    }


    @ApiOperation(value = "查询树节点", notes = "查询所有[以树节点形式展示] filter: true 只显示菜单")
    @GetMapping("/tree")
    public JsonResult<List<SysPermission>> tree(Boolean filter) {
        List<SysPermission> roots = null;
        if (filter) {
            roots = TreeNodeUtils.findRoots(baseService.list(SysPermission::getPermType, PermissionTypeEnum.MENU));
        } else {
            roots = TreeNodeUtils.findRoots(baseService.list());
        }
        return JsonResult.success(roots);
    }


    @ApiOperation(value = "新增", notes = "不需要添加id")
    @OperateLog(title = "[菜单] 新增", type = LogType.INSERT)
    @PostMapping()
    public JsonResult create(@RequestBody SysPermission entity) {
        baseService.save(entity);
        return JsonResult.success(entity);
    }


    @ApiOperation(value = "修改", notes = "修改必须要id")
    @OperateLog(title = "[权限] 修改", type = LogType.UPDATE)
    @PutMapping()
    public JsonResult update(@RequestBody SysPermission entity) {
        boolean byId = baseService.updateById(entity);
        return JsonResult.success(byId);
    }


    @ApiOperation(value = "删除", notes = "只需要id即可")
    @OperateLog(title = "[权限] 删除", type = LogType.DELETE)
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        boolean byId = baseService.removeById(id);
        return JsonResult.success(byId);
    }


}
