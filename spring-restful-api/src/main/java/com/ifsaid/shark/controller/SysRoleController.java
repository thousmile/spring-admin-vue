package com.ifsaid.shark.controller;

import com.ifsaid.shark.common.controller.BaseController;
import com.ifsaid.shark.common.domain.TreeNode;
import com.ifsaid.shark.entity.SysPermission;
import com.ifsaid.shark.entity.SysRole;
import com.ifsaid.shark.service.SysPermissionService;
import com.ifsaid.shark.service.SysRoleService;
import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.util.TreeNodeUtils;
import com.ifsaid.shark.vo.RoleRelatedPermissionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 角色管理 控制器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@Api(tags = "[ 权限管理 ] 角色管理")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController<SysRole, Integer, SysRoleService> {

    @Autowired
    private SysPermissionService sysPermissionService;

    @ApiOperation(value = "查询角色详情，包括权限列表", notes = "根据Id查询")
    @GetMapping("/info/{rid}")
    public JsonResult roleInfo(@PathVariable Integer rid) {
        Map<String, Object> map = new HashMap<>(2);
        // 获取当前角色，拥有的权限
        Set<SysPermission> havePermissionCollect = sysPermissionService.findPermissionByRoleId(rid);

        // 获取全部的权限
        List<TreeNode> allPermissionCollect = sysPermissionService.findAll(null)
                .stream()
                .distinct()
                .map(res -> new TreeNode(res.getPid(), res.getTitle(), res.getParentId(), null, null))
                .collect(Collectors.toList());

        // 将当前角色拥有的权限 pid，挑选出来
        Set<Integer> hashSet = havePermissionCollect.stream().map(res -> res.getPid()).collect(Collectors.toSet());
        // 再次遍历，当前角色拥有的权限，然后 移除父节点 只留下子节点。
        havePermissionCollect.forEach(res -> hashSet.remove(res.getParentId()));
        // 将全部权限，递归成树节点的形式
        map.put("all", TreeNodeUtils.findRoots(allPermissionCollect));
        // 将当前角色拥有的权限Id，以列表的形式返回
        map.put("have", hashSet);
        return JsonResult.success(map);
    }


    @ApiOperation(value = "修改角色权限", notes = "修改角色权限,会删除之前的权限信息。")
    @PostMapping("/update/permissions")
    public JsonResult updateRolePermissions(@RequestBody @Validated RoleRelatedPermissionVo data, BindingResult br) {
        int result = baseService.updateRolePermissions(data.getRid(), data.getPermissions());
        return JsonResult.success(result);
    }


}
