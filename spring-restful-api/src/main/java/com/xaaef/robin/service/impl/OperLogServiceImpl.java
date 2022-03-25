package com.xaaef.robin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xaaef.robin.entity.ChinaArea;
import com.xaaef.robin.entity.OperLog;
import com.xaaef.robin.param.QueryParams;
import com.xaaef.robin.service.OperLogService;
import com.xaaef.robin.mapper.OperLogMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author WangChenChen
 * @description 针对表【oper_log(用户操作日志)】的数据库操作Service实现
 * @createDate 2022-03-24 11:22:11
 */


@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, OperLog>
        implements OperLogService {

    @Override
    public IPage<OperLog> pageKeywords(QueryParams params) {
        var wrapper = new LambdaQueryWrapper<OperLog>();
        Page<OperLog> page = Page.of(params.getPageNum(), params.getPageSize());
        page.addOrder(OrderItem.desc("create_time"));
        if (StringUtils.isNotBlank(params.getKeywords())) {
            String keywords = params.getKeywords().trim();
            wrapper.like(OperLog::getTitle, keywords)
                    .or()
                    .like(OperLog::getMethod, keywords)
                    .or()
                    .like(OperLog::getRequestUrl, keywords);
        }
        return baseMapper.selectPage(page, wrapper);
    }


}




