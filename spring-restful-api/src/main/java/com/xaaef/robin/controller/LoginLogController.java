package com.xaaef.robin.controller;

import com.xaaef.robin.aspect.log.LogType;
import com.xaaef.robin.aspect.log.OperateLog;
import com.xaaef.robin.domain.Pagination;
import com.xaaef.robin.entity.LoginLog;
import com.xaaef.robin.param.QueryParams;
import com.xaaef.robin.service.LoginLogService;
import com.xaaef.robin.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 用户登录日志 Controller
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/25 22:13
 */


@Slf4j
@Api(tags = "[ 系统 ] 登录日志")
@RestController
@AllArgsConstructor
@RequestMapping("/login/log")
public class LoginLogController {

    private LoginLogService baseService;

    @ApiOperation(value = "分页查询", notes = "根据关键字搜索")
    @GetMapping("/query")
    public JsonResult<Pagination<LoginLog>> query(QueryParams params) {
        var all = baseService.pageKeywords(params);
        return JsonResult.success(all.getTotal(), all.getRecords());
    }

    @ApiOperation(value = "批量删除", notes = "只需要id即可")
    @OperateLog(title = "[登录日志] 批量删除", type = LogType.DELETE)
    @DeleteMapping("batch")
    public JsonResult<Boolean> delete(@RequestBody List<String> ids) {
        boolean b = baseService.removeByIds(ids);
        return JsonResult.success(b);
    }

}
