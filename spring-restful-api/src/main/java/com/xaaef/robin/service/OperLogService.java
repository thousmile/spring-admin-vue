package com.xaaef.robin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xaaef.robin.entity.OperLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xaaef.robin.param.QueryParams;

/**
 * @author WangChenChen
 * @description 针对表【oper_log(用户操作日志)】的数据库操作Service
 * @createDate 2022-03-24 11:22:11
 */

public interface OperLogService extends IService<OperLog> {


    /**
     * 分页查询
     *
     * @author Wang Chen Chen
     * @date 2021/8/25 9:41
     */
    IPage<OperLog> pageKeywords(QueryParams params);


}
