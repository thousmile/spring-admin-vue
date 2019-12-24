package com.ifsaid.shark.common.service;

import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.common.domain.BaseEntity;
import com.ifsaid.shark.util.QueryParameter;

import java.util.List;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 通用 service 接口
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface BaseService<T extends BaseEntity, ID> {

    /**
     * 分页查询
     *
     * @param parameter
     * @return PageInfo<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    PageInfo<T> findAllPage(QueryParameter parameter);

    /**
     * 查看所有数据，根据条件查询
     *
     * @param entity
     * @return List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    List<T> findAll(T entity);

    /**
     * 根据ID查询数据
     *
     * @param id
     * @return T
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    T findById(ID id);

    /**
     * 根据条件查询，只返回一条数据
     *
     * @param entity
     * @return T
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    T find(T entity);

    /**
     * 新增数据
     *
     * @param entity
     * @return int
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    int create(T entity);

    /**
     * 批量新增数据， 返回 新增的数量
     *
     * @param list
     * @return int
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    int batchCreate(List<T> list);

    /**
     * 修改数据，必须带 ID ，返回 被修改的数量
     *
     * @param entity
     * @return int
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    int update(T entity);

    /**
     * 根据ID删除数据，返回 删除的数量
     *
     * @param id
     * @return int
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    int deleteById(ID id);

    /**
     * 根据条件删除，返回 删除的数量
     *
     * @param entity
     * @return int
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    int delete(T entity);

    /**
     * 根据ID判断数据是否存在
     *
     * @param entity
     * @return int
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    int count(T entity);

    /**
     * 根据ID判断数据是否存在
     *
     * @param id
     * @return boolean
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    boolean existsWithPrimaryKey(ID id);

}
