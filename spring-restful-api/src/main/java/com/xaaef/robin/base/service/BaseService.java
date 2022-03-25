package com.xaaef.robin.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xaaef.robin.base.entity.BaseEntity;
import com.xaaef.robin.param.QueryParams;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 通用 service 实现类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 3.0
 * @date 2019/4/18 11:45
 */

public interface BaseService<T extends BaseEntity> extends IService<T> {

    /**
     * 根据关键字查询
     *
     * @param params
     * @param columns 实体类字段
     * @author Wang Chen Chen
     * @date 2021/8/25 9:41
     */
    IPage<T> pageKeywords(QueryParams params, SFunction<T, ?>... columns);


    /**
     * 根据关键字查询
     *
     * @param params
     * @param columns 实体类字段
     * @author Wang Chen Chen
     * @date 2021/8/25 9:41
     */
    IPage<T> pageKeywords(QueryParams params, List<SFunction<T, ?>> columns);


    /**
     * 根据 条件 查询全部
     *
     * @author Wang Chen Chen
     * @date 2021/8/25 9:41
     */
    List<T> list(SFunction<T, ?> column, Object value);


    /**
     * 根据 多条件 查询全部
     *
     * @param columns
     * @author Wang Chen Chen
     * @date 2021/8/25 9:41
     */
    List<T> list(Map<SFunction<T, ?>, Object> columns);


    /**
     * 根据ID判断，是否存在
     *
     * @author Wang Chen Chen
     * @date 2021/8/25 9:41
     */
    boolean existById(Serializable id);


    /**
     * 根据 字段 判断，是否存在
     *
     * @author Wang Chen Chen
     * @date 2021/8/25 9:41
     */
    boolean exist(SFunction<T, ?> column, Object value);


    /**
     * 根据 字段 判断，数量
     *
     * @return
     * @author Wang Chen Chen
     * @date 2021/8/25 9:41
     */
    Long count(SFunction<T, ?> column, Object value);


    /**
     * 根据 字段 判断，是否存在
     *
     * @author Wang Chen Chen
     * @date 2021/8/25 9:41
     */
    T getOne(SFunction<T, ?> column, Object value);


}
