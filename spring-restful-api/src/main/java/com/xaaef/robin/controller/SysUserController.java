package com.xaaef.robin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xaaef.robin.aspect.log.LogType;
import com.xaaef.robin.aspect.log.OperateLog;
import com.xaaef.robin.domain.Pagination;
import com.xaaef.robin.entity.SysUser;
import com.xaaef.robin.jwt.JwtSecurityUtils;
import com.xaaef.robin.param.UserQueryParams;
import com.xaaef.robin.service.SysUserService;
import com.xaaef.robin.util.JsonResult;
import com.xaaef.robin.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


/**
 * <p>
 * 用户管理 控制器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 */

@Slf4j
@Api(tags = "[ 权限管理 ] 用户管理")
@RestController
@AllArgsConstructor
@RequestMapping("user")
public class SysUserController {

    private SysUserService baseService;

    @ApiOperation(value = "获取用户详细信息", notes = "获取用户详细信息")
    @GetMapping("/info")
    public JsonResult<SysUserVO> findUserInfo() {
        Long userId = JwtSecurityUtils.getUserId();
        return JsonResult.success(baseService.getUserInfo(userId));
    }

    @ApiOperation(value = "获取所有租户和系统用户", notes = "获取当前租户下的 所有用户 以及 系统用户下的所有")
    @GetMapping("/allUsers")
    public JsonResult<Set<UserIdAndNicknameVO>> listAllSimpleUser() {
        return JsonResult.success(baseService.listAllSimpleUser());
    }


    @ApiOperation(value = "新增", notes = "不需要添加id")
    @OperateLog(title = "[用户] 新增", type = LogType.INSERT)
    @PostMapping()
    public JsonResult create(@RequestBody SysUser entity) {
        baseService.save(entity);
        return JsonResult.success(entity);
    }


    @ApiOperation(value = "分页查询", notes = "分页 查询所有")
    @GetMapping("/query")
    public JsonResult<Pagination<SysUser>> pageQuery(UserQueryParams params) {
        IPage<SysUser> page = baseService.pageKeywords(params);
        return JsonResult.success(page.getTotal(), page.getRecords());
    }


    @ApiOperation(value = "修改", notes = "修改必须要id")
    @OperateLog(title = "[用户] 修改", type = LogType.UPDATE)
    @PutMapping()
    public JsonResult update(@RequestBody SysUser entity) {
        boolean byId = baseService.updateById(entity);
        return JsonResult.success(byId);
    }


    @ApiOperation(value = "删除", notes = "修改必须要id")
    @OperateLog(title = "[用户] 删除", type = LogType.UPDATE)
    @DeleteMapping("/{id}")
    public JsonResult delete(@PathVariable Long id) {
        boolean b = baseService.removeById(id);
        return JsonResult.success(b);
    }


    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @OperateLog(title = "[用户] 修改密码", type = LogType.UPDATE)
    @PostMapping("/update/password")
    public JsonResult updatePassword(@RequestBody @Validated UpdatePasswordVO data,
                                     BindingResult br) {
        return JsonResult.success(
                baseService.updatePassword(data)
        );
    }


    @ApiOperation(value = "重置用户密码", notes = "重置用户密码")
    @OperateLog(title = "[用户] 重置用户密码", type = LogType.UPDATE)
    @PostMapping("/reset/password")
    public JsonResult resetPassword(@RequestBody @Validated ResetPasswordVO data,
                                    BindingResult br) {
        return JsonResult.success(
                baseService.resetPassword(data)
        );
    }


    @ApiOperation(value = "修改用户角色", notes = "修改用户角色,会删除之前的角色信息。")
    @OperateLog(title = "修改用户角色", type = LogType.UPDATE)
    @PostMapping("/update/roles")
    public JsonResult updateRole(@RequestBody @Validated BindingRolesVO data,
                                 BindingResult br) {
        return JsonResult.success(
                baseService.updateRoles(data.getUserId(), data.getRoles())
        );
    }


}
