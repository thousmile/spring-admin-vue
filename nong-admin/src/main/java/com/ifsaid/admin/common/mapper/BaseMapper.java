package com.ifsaid.admin.common.mapper;

import java.util.List;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/26 9:42
 * @describe： 通用  Mapper 接口
 * @version: 1.0
 */
public interface BaseMapper<T, ID> {

    int deleteByPrimaryKey(ID id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(ID id);

    List<T> selectList();

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

}
