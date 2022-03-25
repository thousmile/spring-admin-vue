package com.xaaef.robin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xaaef.robin.entity.ChinaArea;
import com.xaaef.robin.entity.LoginLog;
import com.xaaef.robin.param.QueryParams;
import com.xaaef.robin.service.LoginLogService;
import com.xaaef.robin.mapper.LoginLogMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author WangChenChen
 * @description 针对表【login_log(登录日志记录)】的数据库操作Service实现
 * @createDate 2022-03-24 11:22:11
 */


@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog>
        implements LoginLogService {

    @Override
    public IPage<LoginLog> pageKeywords(QueryParams params) {
        var wrapper = new LambdaQueryWrapper<LoginLog>();
        Page<LoginLog> page = Page.of(params.getPageNum(), params.getPageSize());
        page.addOrder(OrderItem.desc("create_time"));
        if (StringUtils.isNotBlank(params.getKeywords())) {
            String keywords = params.getKeywords().trim();
            wrapper.like(LoginLog::getNickname, keywords)
                    .or()
                    .like(LoginLog::getUsername, keywords)
                    .or()
                    .like(LoginLog::getAddress, keywords);
        }
        return baseMapper.selectPage(page, wrapper);
    }


}




