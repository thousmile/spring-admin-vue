package com.ifsaid.baodao.common.service.impl;

import com.ifsaid.baodao.common.entity.BaseEntity;
import com.ifsaid.baodao.common.exception.JpaCrudException;
import com.ifsaid.baodao.common.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 13:53
 * @describe： service 通用实现类
 * @version: 1.0
 */

@Slf4j
public class BaseServiceImpl<T extends BaseEntity, ID, R extends JpaRepository> implements IBaseService<T, ID> {

    @Autowired
    protected R baseRepository;

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return baseRepository.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable page) {
        log.info("findAll PageNumber: {} ---> PageSize: {}", page.getPageNumber(), page.getPageSize());
        return baseRepository.findAll(page);
    }

    @Override
    public T findById(ID id) {
        log.info("findById: {}", id);
        Optional byId = baseRepository.findById(id);
        return (T) byId.get();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public T save(T entity) throws JpaCrudException {
        if (entity == null) {
            throw new JpaCrudException("You cannot save an empty entity class.");
        }
        entity.setCreateTime(new Date());
        entity.setUpTime(new Date());
        log.info("save: {}", entity);
        return (T) baseRepository.saveAndFlush(entity);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public T update(T entity) throws JpaCrudException {
        if (entity == null) {
            throw new JpaCrudException("You cannot update an empty entity class.");
        }
        entity.setUpTime(new Date());
        log.info("update: {}", entity);
        return (T) baseRepository.saveAndFlush(entity);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void deleteById(ID id) throws JpaCrudException {
        if (id == null || !baseRepository.existsById(id)) {
            throw new JpaCrudException("Unable to delete data whose ID does not exist");
        }
        log.info("deleteById: {}", id);
        baseRepository.deleteById(id);
    }

    @Override
    public boolean existsById(ID var1) {
        log.info("existsById: {}", var1);
        return baseRepository.existsById(var1);
    }

}
