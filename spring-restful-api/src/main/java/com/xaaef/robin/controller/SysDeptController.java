package com.xaaef.robin.controller;

import com.xaaef.robin.aspect.log.LogType;
import com.xaaef.robin.aspect.log.OperateLog;
import com.xaaef.robin.domain.Pagination;
import com.xaaef.robin.entity.SysDept;
import com.xaaef.robin.param.QueryParams;
import com.xaaef.robin.service.SysDeptService;
import com.xaaef.robin.util.JsonResult;
import com.xaaef.robin.util.TreeNodeUtils;
import com.xaaef.robin.vo.BindingPermsVO;
import com.xaaef.robin.vo.UpdatePermsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 部门 控制器
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/8 10:15
 */

@Slf4j
@Api(tags = "[ 权限 ] 部门管理")
@RestController
@RequestMapping("dept")
@AllArgsConstructor
public class SysDeptController {

    private SysDeptService baseService;

    @ApiOperation(value = "单个查询", notes = "根据Id查询")
    @GetMapping("/{id}")
    public JsonResult<SysDept> findById(@PathVariable("id") Integer id) {
        return JsonResult.success(baseService.getById(id));
    }


    @ApiOperation(value = "分页", notes = "分页 查询所有")
    @GetMapping("/query")
    public JsonResult<Pagination<SysDept>> pageQuery(QueryParams params) {
        var page = baseService.pageKeywords(
                params,
                SysDept::getDeptName, SysDept::getDescription,
                SysDept::getLeader, SysDept::getLeaderMobile
        );
        return JsonResult.success(page.getTotal(), page.getRecords());
    }


    @ApiOperation(value = "查询所有", notes = "查询所有")
    @GetMapping("/all")
    public JsonResult<List<SysDept>> all() {
        return JsonResult.success(baseService.list());
    }


    @ApiOperation(value = "查询树节点", notes = "查询所有[以树节点形式展示]")
    @GetMapping("/tree")
    public JsonResult<List<SysDept>> tree() {
        var roots = TreeNodeUtils.findRoots(baseService.list());
        return JsonResult.success(roots);
    }


    @ApiOperation(value = "新增", notes = "不需要添加id")
    @OperateLog(title = "[部门] 新增", type = LogType.INSERT)
    @PostMapping()
    public JsonResult create(@RequestBody SysDept entity) {
        baseService.save(entity);
        return JsonResult.success(entity);
    }


    @ApiOperation(value = "修改", notes = "修改必须要id")
    @OperateLog(title = "[部门] 修改", type = LogType.UPDATE)
    @PutMapping()
    public JsonResult update(@RequestBody SysDept entity) {
        return JsonResult.success(baseService.updateById(entity));
    }


    @ApiOperation(value = "删除", notes = "只需要id即可")
    @OperateLog(title = "[部门]删除", type = LogType.DELETE)
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable("id") Integer id) {
        return JsonResult.success(baseService.removeById(id));
    }


    @ApiOperation(value = "拥有的权限", notes = "查询角色拥有的权限！")
    @GetMapping("/perms/{deptId}")
    public JsonResult<UpdatePermsVO> selectHaveMenus(@PathVariable Long deptId) {
        var data = baseService.listHavePerms(deptId);
        return JsonResult.success(data);
    }


    @ApiOperation(value = "绑定权限", notes = "会删除原有的权限！")
    @OperateLog(title = "[部门] 绑定权限", type = LogType.UPDATE)
    @PostMapping("/perms")
    public JsonResult bindingMenus(@RequestBody @Validated BindingPermsVO entity,
                                   BindingResult br) {
        boolean b = baseService.updatePerms(entity.getId(), entity.getItems());
        return JsonResult.success(b);
    }


}
