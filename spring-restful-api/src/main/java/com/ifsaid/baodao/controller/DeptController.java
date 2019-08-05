package com.ifsaid.baodao.controller;

import com.ifsaid.baodao.common.controller.BaseController;
import com.ifsaid.baodao.entity.Dept;
import com.ifsaid.baodao.service.IDeptService;
import com.ifsaid.baodao.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:49
 * @describe：
 * @version: 1.0
 */

@Api(tags = "部门管理")
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController<Dept, Integer, IDeptService> {

    @ApiOperation(value = "查询全部", notes = "以树节点的方式返回")
    @GetMapping("/tree")
    public Result treeList() {
        return Result.success(baseService.findAllTree());
    }

}
