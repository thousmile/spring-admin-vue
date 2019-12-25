package com.ifsaid.shark.controller;

import com.ifsaid.shark.common.controller.BaseController;
import com.ifsaid.shark.common.domain.TreeNode;
import com.ifsaid.shark.constant.PermissionType;
import com.ifsaid.shark.entity.SysPermission;
import com.ifsaid.shark.service.SysPermissionService;
import com.ifsaid.shark.util.JsonResult;
import com.ifsaid.shark.util.TreeNodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 权限管理 控制器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@Api(tags = "[ 权限管理 ] 权限管理")
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController extends BaseController<SysPermission, Integer, SysPermissionService> {

    @ApiOperation(value = "查询所有[树节点]", notes = "以树节点的形式展示 <br> \n\n 如果 filter 是 true，那么就是要过滤掉，按钮。如果是 false。就是菜单和按钮全要")
    @GetMapping("/tree")
    public JsonResult tree(@RequestParam(defaultValue = "false") boolean filter) {
        List<TreeNode> collect = baseService.findAll(null)
                .stream()
                .distinct()
                // 如果 filter 是 true，那么就是要过滤掉，按钮。如果是 false。就是菜单和按钮全要
                .filter(res -> filter ? PermissionType.MENU.equals(res.getType().toLowerCase()) : true)
                .map(res -> new TreeNode(res.getPid(), res.getTitle(), res.getParentId(), res, new ArrayList<>()))
                .collect(Collectors.toList());
        return JsonResult.success(TreeNodeUtils.findRoots(collect));
    }


}
