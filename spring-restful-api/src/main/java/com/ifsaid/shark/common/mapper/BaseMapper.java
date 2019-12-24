package com.ifsaid.shark.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * MyBatis 通用 Mapper 接口
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public interface BaseMapper<T, ID> extends Mapper<T>, MySqlMapper<T> {

}
