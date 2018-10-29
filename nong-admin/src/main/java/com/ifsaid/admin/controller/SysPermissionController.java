package com.ifsaid.admin.controller;

import com.ifsaid.admin.common.controller.BaseController;
import com.ifsaid.admin.entity.SysPermission;
import com.ifsaid.admin.service.ISysPermissionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * [权限管理] 权限表 前端控制器
 * </p>
 *
 * @author wang chen chen
 * @since 2018-10-23
 */

@Slf4j
@Api(tags = "权限")
@RestController
@RequestMapping("/permission")
public class SysPermissionController extends BaseController<SysPermission, Integer, ISysPermissionService> {

}
