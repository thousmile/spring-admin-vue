package com.xaaef.robin.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xaaef.robin.base.entity.BaseEntity;
import com.xaaef.robin.base.service.BaseService;
import com.xaaef.robin.jwt.JwtSecurityUtils;
import com.xaaef.robin.param.QueryParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @date 2021/10/21 21:39
 */

@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * 创建时间 , 数据库字段
     */
    protected final static String CREATE_TIME = "create_time";


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(T entity) {
        if (null == entity.getCreateUser() && JwtSecurityUtils.getAuthentication() != null) {
            entity.setCreateUser(JwtSecurityUtils.getUserId());
        }
        if (null == entity.getLastUpdateUser() && JwtSecurityUtils.getAuthentication() != null) {
            entity.setLastUpdateUser(JwtSecurityUtils.getUserId());
        }
        entity.setCreateTime(LocalDateTime.now());
        entity.setLastUpdateTime(LocalDateTime.now());
        return baseMapper.insert(entity) > 0;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(Collection<T> entityList) {
        entityList.forEach(entity -> {
            if (null == entity.getCreateUser() && JwtSecurityUtils.getAuthentication() != null) {
                entity.setCreateUser(JwtSecurityUtils.getUserId());
            }
            if (null == entity.getLastUpdateUser() && JwtSecurityUtils.getAuthentication() != null) {
                entity.setLastUpdateUser(JwtSecurityUtils.getUserId());
            }
            entity.setCreateTime(LocalDateTime.now());
            entity.setLastUpdateTime(LocalDateTime.now());
        });
        return super.saveBatch(entityList);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(T entity) {
        entity.setCreateTime(null);
        entity.setCreateUser(null);
        if (null == entity.getLastUpdateUser() && JwtSecurityUtils.getAuthentication() != null) {
            entity.setLastUpdateUser(JwtSecurityUtils.getUserId());
        }
        entity.setLastUpdateTime(LocalDateTime.now());
        return super.updateById(entity);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        entity.setCreateTime(null);
        entity.setCreateUser(null);
        if (null == entity.getLastUpdateUser() && JwtSecurityUtils.getAuthentication() != null) {
            entity.setLastUpdateUser(JwtSecurityUtils.getUserId());
        }
        entity.setLastUpdateTime(LocalDateTime.now());
        return super.update(entity, updateWrapper);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateBatchById(Collection<T> entityList) {
        entityList.forEach(entity -> {
            entity.setCreateTime(null);
            entity.setCreateUser(null);
            if (null == entity.getLastUpdateUser() && JwtSecurityUtils.getAuthentication() != null) {
                entity.setLastUpdateUser(JwtSecurityUtils.getUserId());
            }
            entity.setLastUpdateTime(LocalDateTime.now());
        });
        return super.updateBatchById(entityList);
    }


    @Override
    public boolean existById(Serializable id) {
        return super.getById(id) != null;
    }


    @Override
    public boolean exist(SFunction<T, ?> column, Object value) {
        var wrapper = new LambdaQueryWrapper<T>().eq(column, value);
        return super.count(wrapper) > 0;
    }


    @Override
    public Long count(SFunction<T, ?> column, Object value) {
        var wrapper = new LambdaQueryWrapper<T>().eq(column, value);
        return super.count(wrapper);
    }


    @Override
    public T getOne(SFunction<T, ?> column, Object value) {
        return super.getOne(new LambdaQueryWrapper<T>().eq(column, value));
    }


    protected QueryWrapper<T> getKeywordsQueryWrapper(QueryParams params, SFunction<T, ?>[] columns) {
        var wrapper = new QueryWrapper<T>();
        // 开始时间是否为空
        if (ObjectUtils.isNotEmpty(params.getStartTime())) {
            // 如果结束时间是否为空
            if (ObjectUtils.isNotEmpty(params.getEndTime())) {
                wrapper.between(CREATE_TIME, params.getStartTime(), params.getEndTime());
            } else {
                wrapper.between(CREATE_TIME, params.getStartTime(), LocalDateTime.now());
            }
        }
        if (StringUtils.isNotBlank(params.getKeywords()) && columns != null && columns.length > 0) {
            String keywords = params.getKeywords().trim();
            for (int i = 0; i < columns.length; i++) {
                if (i == 0)
                    wrapper.lambda().like(columns[i], keywords);
                else
                    wrapper.lambda().or().like(columns[i], keywords);
            }
        }
        return wrapper;
    }


    @Override
    public IPage<T> pageKeywords(QueryParams params, SFunction<T, ?>... columns) {
        Page<T> page = Page.of(params.getPageNum(), params.getPageSize());
        QueryWrapper<T> wrapper = getKeywordsQueryWrapper(params, columns);
        return super.page(page, wrapper);
    }


    @Override
    public IPage<T> pageKeywords(QueryParams params, List<SFunction<T, ?>> columns) {
        Page<T> page = Page.of(params.getPageNum(), params.getPageSize());
        QueryWrapper<T> wrapper = getKeywordsQueryWrapper(params, columns.toArray(SFunction[]::new));
        return super.page(page, wrapper);
    }


    @Override
    public List<T> list(SFunction<T, ?> column, Object value) {
        return super.list(new LambdaQueryWrapper<T>().eq(column, value));
    }


    @Override
    public List<T> list(Map<SFunction<T, ?>, Object> columns) {
        var wrapper = new LambdaQueryWrapper<T>();
        columns.forEach(wrapper::eq);
        return super.list(wrapper);
    }


}
