package com.ifsaid.baodao.service.impl;

import com.ifsaid.baodao.common.exception.JpaCrudException;
import com.ifsaid.baodao.common.service.impl.BaseServiceImpl;
import com.ifsaid.baodao.entity.Category;
import com.ifsaid.baodao.repository.CategoryRepository;
import com.ifsaid.baodao.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:27
 * @describe： 类目Service 实现类
 * @version: 1.0
 */

@Slf4j
@Service
@CacheConfig(cacheNames = "categorys")
public class CategoryServiceImpl extends BaseServiceImpl<Category, Integer, CategoryRepository> implements ICategoryService {

    @Override
    @Cacheable(key = "#p0")
    public List<Category> findAllByRegion(String region) {
        return baseRepository.findAllByRegion(region);
    }

    @Override
    @CacheEvict(key = "#p0", allEntries = true)
    public void deleteById(Integer integer) throws JpaCrudException {
        super.deleteById(integer);
    }

}
