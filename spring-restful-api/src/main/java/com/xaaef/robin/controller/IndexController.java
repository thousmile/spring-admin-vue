package com.xaaef.robin.controller;

import com.xaaef.robin.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 首页 控制器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 3.0
 */


@Slf4j
@Api(tags = "[ 首页 ]")
@RestController
@AllArgsConstructor
public class IndexController {

    @PreAuthorize("hasAuthority('home:get')")
    @ApiOperation(value = "home", notes = "home")
    @GetMapping(value = {"home",})
    public JsonResult<String> home() {
        return JsonResult.success("home");
    }


    @PreAuthorize("hasAuthority('index:get')")
    @ApiOperation(value = "index", notes = "index")
    @GetMapping(value = {"index"})
    public JsonResult<String> index() {
        return JsonResult.success("index");
    }


}
