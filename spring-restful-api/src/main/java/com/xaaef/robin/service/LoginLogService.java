package com.xaaef.robin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xaaef.robin.entity.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xaaef.robin.param.QueryParams;

/**
 * @author WangChenChen
 * @description 针对表【login_log(登录日志记录)】的数据库操作Service
 * @createDate 2022-03-24 11:22:11
 */


public interface LoginLogService extends IService<LoginLog> {

    /**
     * 分页查询
     *
     * @author Wang Chen Chen
     * @date 2021/8/25 9:41
     */
    IPage<LoginLog> pageKeywords(QueryParams params);


}
