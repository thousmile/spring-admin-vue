package com.ifsaid.shark.controller;

import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.common.controller.BaseController;
import com.ifsaid.shark.entity.SysUser;
import com.ifsaid.shark.service.SysUserService;
import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.util.QueryParameter;
import com.ifsaid.shark.vo.SysUserVo;
import com.ifsaid.shark.vo.UserRelatedRoleVo;
import com.ifsaid.shark.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 用户管理 控制器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Slf4j
@Api(tags = "[ 权限管理 ] 用户管理")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController<SysUser, Integer, SysUserService> {

    @ApiOperation(value = "分页,获取用户详情", notes = "分页 查询所有，获取用户详情")
    @GetMapping("/info/page")
    public JsonResult findAllInfoPage(QueryParameter parameter) {
        PageInfo<SysUserVo> page = baseService.findAllPageInfo(parameter);
        return JsonResult.success(page.getTotal(), page.getList());
    }

    @ApiOperation(value = "获取用户详细信息", notes = "获取用户详细信息")
    @GetMapping("/info")
    public JsonResult<UserVo> findUserInfo() {
        return JsonResult.success(baseService.findUserInfo());
    }

    @ApiOperation(value = "修改用户角色", notes = "修改用户角色,会删除之前的角色信息。")
    @PostMapping("/update/roles")
    public JsonResult updateRolePermissions(@RequestBody @Validated UserRelatedRoleVo data, BindingResult br) {
        int result = baseService.updateUserRoles(data.getUid(), data.getRoles());
        return JsonResult.success(result);
    }


}
