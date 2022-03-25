package com.xaaef.robin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xaaef.robin.entity.ChinaArea;
import com.xaaef.robin.mapper.ChinaAreaMapper;
import com.xaaef.robin.param.QueryParams;
import com.xaaef.robin.service.ChinaAreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author WangChenChen
 * @description 针对表【cn_area([ 通用 ] 中国行政地区表)】的数据库操作Service实现
 * @createDate 2022-03-22 09:59:32
 */


@Service
public class ChinaAreaServiceImpl extends ServiceImpl<ChinaAreaMapper, ChinaArea>
        implements ChinaAreaService {


    @Override
    public IPage<ChinaArea> pageKeywords(QueryParams params) {
        var wrapper = new LambdaQueryWrapper<ChinaArea>();
        Page<ChinaArea> page = Page.of(params.getPageNum(), params.getPageSize());
        if (StringUtils.isNotBlank(params.getKeywords())) {
            wrapper.like(ChinaArea::getName, params.getKeywords())
                    .or()
                    .like(ChinaArea::getMergerName, params.getKeywords())
                    .or()
                    .like(ChinaArea::getPinyin, params.getKeywords());
        }
        return baseMapper.selectPage(page, wrapper);
    }

    @Override
    public List<ChinaArea> list(SFunction<ChinaArea, ?> column, Object value) {
        return super.list(new LambdaQueryWrapper<ChinaArea>().eq(column, value));
    }

}




