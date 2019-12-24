package com.ifsaid.shark.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.common.domain.BaseEntity;
import com.ifsaid.shark.common.mapper.BaseMapper;
import com.ifsaid.shark.common.service.BaseService;
import com.ifsaid.shark.util.QueryParameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 通用 service 实现类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Slf4j
public class BaseServiceImpl<T extends BaseEntity, ID, M extends BaseMapper<T, ID>> implements BaseService<T, ID> {

    @Autowired
    protected M baseMapper;

    @Override
    public PageInfo<T> findAllPage(QueryParameter parameter) {
        log.info("findAll parameter: {}", parameter);
        PageInfo<T> pageInfo = PageHelper
                .startPage(parameter.getPageNum(), parameter.getPageSize())
                .doSelectPageInfo(() -> baseMapper.selectAll());
        return pageInfo;
    }

    @Override
    public List<T> findAll(T entity) {
        log.info("findAll entity: {}", entity);
        if (entity == null) {
            baseMapper.selectAll();
        }
        return baseMapper.select(entity);
    }

    @Override
    public T findById(ID id) {
        log.info("findById id: {}", id);
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public T find(T entity) {
        log.info("find entity: {}", entity);
        return baseMapper.selectOne(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int create(T entity) {
        entity.setCreateTime(LocalDateTime.now());
        entity.setLastUpdateTime(LocalDateTime.now());
        log.info("create entity: {}", entity);
        return baseMapper.insertUseGeneratedKeys(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchCreate(List<T> list) {
        list.forEach(res -> {
            res.setCreateTime(LocalDateTime.now());
            res.setLastUpdateTime(LocalDateTime.now());
        });
        log.info("batchCreate list: {}", list);
        return baseMapper.insertList(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(T entity) {
        entity.setLastUpdateTime(LocalDateTime.now());
        log.info("update entity: {}", entity);
        return baseMapper.updateByPrimaryKeySelective(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteById(ID id) {
        log.info("deleteById id: {}", id);
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delete(T entity) {
        log.info("delete entity: {}", entity);
        return baseMapper.delete(entity);
    }

    @Override
    public int count(T entity) {
        log.info("count entity: {}", entity);
        return baseMapper.selectCount(entity);
    }

    @Override
    public boolean existsWithPrimaryKey(ID id) {
        log.info("existsWithPrimaryKey id: {}", id);
        return baseMapper.existsWithPrimaryKey(id);
    }
}
