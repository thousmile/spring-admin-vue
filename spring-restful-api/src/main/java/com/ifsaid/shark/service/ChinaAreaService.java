package com.ifsaid.shark.service;

import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.entity.ChinaArea;
import com.ifsaid.shark.util.QueryParameter;

import java.util.List;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 中国行政地区 Service 接口
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface ChinaAreaService {

    /**
     * 分页查询
     *
     * @param parameter
     * @return PageInfo<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    PageInfo<ChinaArea> findAllPage(QueryParameter parameter);


    /**
     * 查看所有数据，根据条件查询
     *
     * @param entity
     * @return List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    List<ChinaArea> findAll(ChinaArea entity);

    /**
     * 根据ID查询数据
     *
     * @param id
     * @return T
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 11:50
     */
    ChinaArea findById(Long id);

}
