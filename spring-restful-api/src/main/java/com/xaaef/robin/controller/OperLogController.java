package com.xaaef.robin.controller;

import com.xaaef.robin.aspect.log.LogType;
import com.xaaef.robin.aspect.log.OperateLog;
import com.xaaef.robin.domain.Pagination;
import com.xaaef.robin.entity.OperLog;
import com.xaaef.robin.param.QueryParams;
import com.xaaef.robin.service.OperLogService;
import com.xaaef.robin.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 用户操作日志 Controller
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/25 22:13
 */


@Slf4j
@Api(tags = "[ 系统 ] 操作日志")
@RestController
@AllArgsConstructor
@RequestMapping("/oper/log")
public class OperLogController {

    private OperLogService baseService;

    @ApiOperation(value = "分页查询", notes = "根据关键字搜索")
    @GetMapping("/query")
    public JsonResult<Pagination<OperLog>> query(QueryParams params) {
        var all = baseService.pageKeywords(params);
        return JsonResult.success(all.getTotal(), all.getRecords());
    }


    @ApiOperation(value = "删除", notes = "只需要id即可")
    @OperateLog(title = "[操作日志] 删除", type = LogType.DELETE)
    @DeleteMapping("batch")
    public JsonResult<Boolean> delete(@RequestBody List<String> ids) {
        boolean b = baseService.removeByIds(ids);
        return JsonResult.success(b);
    }


}
