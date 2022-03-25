package com.xaaef.robin.controller;

import com.xaaef.robin.aspect.log.LogType;
import com.xaaef.robin.aspect.log.OperateLog;
import com.xaaef.robin.domain.Pagination;
import com.xaaef.robin.entity.SysConfig;
import com.xaaef.robin.param.QueryParams;
import com.xaaef.robin.service.SysConfigService;
import com.xaaef.robin.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 配置文件 控制器
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/8 10:15
 */

@Slf4j
@Api(tags = "[ 全局配置 ] 配置文件管理")
@RestController
@RequestMapping("config")
@AllArgsConstructor
public class SysConfigController {

    private SysConfigService baseService;

    @ApiOperation(value = "单个查询", notes = "根据Id查询")
    @GetMapping("/{id}")
    public JsonResult<SysConfig> findById(@PathVariable("id") Integer id) {
        return JsonResult.success(baseService.getById(id));
    }


    @ApiOperation(value = "新增", notes = "不需要添加id")
    @OperateLog(title = "[全局配置] 新增", type = LogType.INSERT)
    @PostMapping()
    public JsonResult create(@RequestBody SysConfig entity) {
        try {
            var save = baseService.save(entity);
            return JsonResult.success(save);
        } catch (Exception e) {
            return JsonResult.fail(e.getMessage());
        }
    }


    @ApiOperation(value = "修改", notes = "修改必须要id")
    @OperateLog(title = "[全局配置] 修改", type = LogType.UPDATE)
    @PutMapping()
    public JsonResult<String> update(@RequestBody SysConfig entity) {
        try {
            baseService.updateById(entity);
            return JsonResult.success();
        } catch (Exception e) {
            return JsonResult.fail(e.getMessage());
        }
    }


    @ApiOperation(value = "删除", notes = "只需要id即可")
    @OperateLog(title = "[全局配置] 删除", type = LogType.DELETE)
    @DeleteMapping("/{id}")
    public JsonResult<String> delete(@PathVariable("id") Integer id) {
        try {
            baseService.removeById(id);
            return JsonResult.success();
        } catch (Exception e) {
            return JsonResult.fail(e.getMessage());
        }
    }


    @ApiOperation(value = "分页", notes = "分页 查询所有")
    @GetMapping("/query")
    public JsonResult<Pagination<SysConfig>> pageQuery(QueryParams params) {
        var page = baseService.pageKeywords(params,
                SysConfig::getConfigName,
                SysConfig::getConfigKey
        );
        return JsonResult.success(page.getTotal(), page.getRecords());
    }


}
