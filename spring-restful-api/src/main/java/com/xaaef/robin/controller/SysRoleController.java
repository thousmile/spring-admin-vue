package com.xaaef.robin.controller;

import com.xaaef.robin.aspect.log.LogType;
import com.xaaef.robin.aspect.log.OperateLog;
import com.xaaef.robin.domain.Pagination;
import com.xaaef.robin.entity.SysRole;
import com.xaaef.robin.param.QueryParams;
import com.xaaef.robin.service.SysRoleService;
import com.xaaef.robin.util.JsonResult;
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
 * 角色控制器
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/8 10:15
 */

@Slf4j
@Api(tags = "[ 权限 ] 角色管理")
@RestController
@RequestMapping("role")
@AllArgsConstructor
public class SysRoleController {

    private SysRoleService baseService;

    @ApiOperation(value = "单个查询", notes = "根据Id查询")
    @GetMapping("/{id}")
    public JsonResult<SysRole> findById(@PathVariable("id") Integer id) {
        return JsonResult.success(baseService.getById(id));
    }


    @ApiOperation(value = "查询所有", notes = "查询所有")
    @GetMapping("/all")
    public JsonResult<List<SysRole>> findAll() {
        return JsonResult.success(baseService.list());
    }


    @ApiOperation(value = "新增", notes = "不需要添加id")
    @OperateLog(title = "[角色] 新增", type = LogType.INSERT)
    @PostMapping()
    public JsonResult create(@RequestBody SysRole entity) {
        baseService.save(entity);
        return JsonResult.success(entity);
    }


    @ApiOperation(value = "修改", notes = "修改必须要id")
    @OperateLog(title = "[角色] 修改", type = LogType.UPDATE)
    @PutMapping()
    public JsonResult<Boolean> update(@RequestBody SysRole entity) {
        boolean b = baseService.updateById(entity);
        return JsonResult.success(b);
    }


    @ApiOperation(value = "删除", notes = "只需要id即可")
    @OperateLog(title = "[角色] 删除", type = LogType.DELETE)
    @DeleteMapping("/{id}")
    public JsonResult<Boolean> delete(@PathVariable("id") Long id) {
        boolean b = baseService.removeById(id);
        return JsonResult.success(b);
    }


    @ApiOperation(value = "分页", notes = "分页 查询所有")
    @GetMapping("/query")
    public JsonResult<Pagination<SysRole>> pageQuery(QueryParams params) {
        var page = baseService.pageKeywords(
                params,
                SysRole::getRoleName,
                SysRole::getDescription
        );
        return JsonResult.success(page.getTotal(), page.getRecords());
    }


    @ApiOperation(value = "拥有的权限", notes = "查询角色拥有的权限！")
    @GetMapping("/perms/{roleId}")
    public JsonResult<UpdatePermsVO> selectHavePerms(@PathVariable Long roleId) {
        var data = baseService.listHavePerms(roleId);
        return JsonResult.success(data);
    }


    @ApiOperation(value = "绑定权限", notes = "会删除原有的权限！")
    @OperateLog(title = "[角色] 绑定权限", type = LogType.UPDATE)
    @PostMapping("/perms")
    public JsonResult<Boolean> bindingPerms(@RequestBody @Validated BindingPermsVO entity,
                                            BindingResult br) {
        boolean b = baseService.updatePerms(entity.getId(), entity.getItems());
        return JsonResult.success(b);
    }

}
