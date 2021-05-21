package com.xaaef.shark.controller;

import com.github.pagehelper.PageInfo;
import com.xaaef.shark.common.controller.BaseController;
import com.xaaef.shark.entity.SysUser;
import com.xaaef.shark.service.SysUserService;
import com.xaaef.shark.service.UserLoginService;
import com.xaaef.shark.util.JsonResult;
import com.xaaef.shark.util.QueryParameter;
import com.xaaef.shark.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 用户管理 控制器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */

@Slf4j
@Api(tags = "[ 权限管理 ] 用户管理")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController<SysUser, Integer, SysUserService> {

    @Autowired
    private UserLoginService userLoginService;

    @ApiOperation(value = "分页,获取用户详情", notes = "分页 查询所有，获取用户详情")
    @GetMapping("/info/page")
    public JsonResult findAllInfoPage(QueryParameter parameter) {
        PageInfo<SysUserVo> page = baseService.findAllPageInfo(parameter);
        return JsonResult.success(page.getTotal(), page.getList());
    }

    @ApiOperation(value = "获取用户详细信息", notes = "获取用户详细信息")
    @GetMapping("/info")
    public JsonResult<UserVo> findUserInfo() {
        return JsonResult.success(userLoginService.findUserInfo());
    }

    @ApiOperation(value = "修改用户密码", notes = "修改用户密码")
    @PostMapping("/update/password")
    public JsonResult<String> updatePassword(@RequestBody @Validated UpdatePassword pwd, BindingResult br) {
        try {
            int result = baseService.updatePassword(pwd);
            return JsonResult.success(result);
        } catch (RuntimeException e) {
            return JsonResult.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "重置用户密码", notes = "重置用户密码")
    @PostMapping("/reset/password")
    public JsonResult<String> resetPassword(@RequestBody @Validated ResetPassword pwd, BindingResult br) {
        try {
            int result = baseService.resetPassword(pwd);
            return JsonResult.success(result);
        } catch (RuntimeException e) {
            return JsonResult.fail(e.getMessage());
        }
    }


    @ApiOperation(value = "修改用户角色", notes = "修改用户角色,会删除之前的角色信息。")
    @PostMapping("/update/roles")
    public JsonResult updateRolePermissions(@RequestBody @Validated UserRelatedRoleVo data, BindingResult br) {
        int result = baseService.updateUserRoles(data.getUid(), data.getRoles());
        return JsonResult.success(result);
    }


}
