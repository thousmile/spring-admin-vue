package com.ifsaid.shark.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.entity.ChinaArea;
import com.ifsaid.shark.mapper.ChinaAreaMapper;
import com.ifsaid.shark.service.ChinaAreaService;
import com.ifsaid.shark.util.QueryParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 中国行政地区 Service 实现类
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/25 22:11
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Slf4j
@Service
public class ChinaAreaServiceImpl implements ChinaAreaService {

    @Autowired
    private ChinaAreaMapper baseMapper;

    @Override
    public PageInfo<ChinaArea> findAllPage(QueryParameter parameter) {
        log.info("findAll parameter: {}", parameter);
        PageInfo<ChinaArea> pageInfo = PageHelper
                .startPage(parameter.getPageNum(), parameter.getPageSize())
                .doSelectPageInfo(() -> baseMapper.selectAll());
        return pageInfo;
    }

    @Override
    public List<ChinaArea> findAll(ChinaArea entity) {
        log.info("findAll entity: {}", entity);
        if (entity == null) {
            baseMapper.selectAll();
        }
        return baseMapper.select(entity);
    }

    @Override
    public ChinaArea findById(Long id) {
        log.info("findById id: {}", id);
        return baseMapper.selectByPrimaryKey(id);
    }

}
