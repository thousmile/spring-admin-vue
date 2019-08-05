package com.ifsaid.baodao.common.service;

import com.ifsaid.baodao.common.exception.JpaCrudException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 13:52
 * @describe： 通用service接口
 * @version: 1.0
 */

public interface IBaseService<T, ID> {

    List<T> findAll();

    List<T> findAll(Sort sort);

    Page<T> findAll(Pageable page);

    T findById(ID id);

    T save(T entity) throws JpaCrudException;

    T update(T entity) throws JpaCrudException;

    void deleteById(ID id) throws JpaCrudException;

    boolean existsById(ID var1);

}
