package com.ifsaid.baodao.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifsaid.baodao.common.controller.BaseController;
import com.ifsaid.baodao.entity.Permission;
import com.ifsaid.baodao.entity.Role;
import com.ifsaid.baodao.service.IPermissionService;
import com.ifsaid.baodao.service.IRoleService;
import com.ifsaid.baodao.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:49
 * @describe：
 * @version: 1.0
 */

@Slf4j
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<Role, Integer, IRoleService> {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IPermissionService permissionService;

    @ApiOperation(value = "查询角色详情，包括权限列表", notes = "根据Id查询")
    @GetMapping("/info/{rid}")
    public Result<String> roleInfo(@PathVariable Integer rid) {
        Map<String, Object> map = new HashMap<>();
        map.put("current", permissionService.findPermissionByRoleId(rid));
        Permission permission = new Permission();
        permission.setTitle("权限列表");
        permission.setPid(0);
        permission.setChildren(permissionService.findAllTree());
        map.put("all", Arrays.asList(permission));
        return Result.success(map);
    }

    /**
     * @describe 修改用户的角色
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    @ApiOperation(value = "修改用户权限", notes = "参数格式: {'uid':'1','rids':'[1,2,3]'}")
    @PostMapping("/update/user")
    public Result updateUserRole(@RequestBody Map<String, Object> map) {
        String uid = (String) map.get("uid");
        List<Integer> rids = (List<Integer>) map.get("rids");
        Set<Integer> collect = rids.stream().filter(r -> r != 0).collect(Collectors.toSet());
        log.info("updateUserRole collect : {}", collect);
        if (uid == null || StringUtils.isEmpty(uid)) {
            return Result.error500("用户ID不能为空", null);
        }
        if (collect == null || collect.size() < 1) {
            return Result.error500("角色ID不能为空", null);
        }
        return Result.success("success", baseService.updateUserRole(uid, collect));
    }

    /**
     * @describe 修改角色的权限
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    @ApiOperation(value = "修改角色权限", notes = "参数格式: {'rid':'1','pids':'[1,2,3]'}")
    @PostMapping("/update/permission")
    public Result<String> roleInfo(@RequestBody Map<String, Object> map) {
        Integer rid = (Integer) map.get("rid");
        List<Integer> pids = (List<Integer>) map.get("pids");
        log.info("updatePermission rid : {}  {}", rid, pids);
        Set<Integer> collect = pids.stream().filter(p -> p != 0).collect(Collectors.toSet());
        log.info("updatePermission collect : {}", collect);
        if (rid == null || rid < 1)
            return Result.error500("角色ID不能为空", null);
        if (pids.size() < 1)
            return Result.error500("权限不能为空", null);
        return Result.success("success", baseService.updateRolePermission(rid, collect));
    }

}
