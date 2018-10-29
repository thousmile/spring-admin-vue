package com.ifsaid.admin.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ifsaid.admin.common.mapper.BaseMapper;
import com.ifsaid.admin.common.service.IBaseService;
import com.ifsaid.admin.vo.MyPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/23 16:13
 * @describe： 通用service实现类
 * @version: 1.0
 */

@Slf4j
public class BaseServiceImpl<T, ID, M extends BaseMapper> implements IBaseService<T, ID> {

    @Autowired
    protected M baseMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int deleteByPrimaryKey(ID id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int insert(T record) {
        return baseMapper.insert(record);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int insertSelective(T record) {
        return baseMapper.insertSelective(record);
    }

    @Override
    public T selectByPrimaryKey(ID id) {
        return (T) baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<T> selectList(MyPage page) {
        log.info("selectList....... PageNum: {}  PageSize:{}", page.getPageNum(), page.getPageSize());
        return PageHelper.startPage(page.getPageNum(), page.getPageSize()).doSelectPageInfo(() -> baseMapper.selectList());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int updateByPrimaryKeySelective(T record) {
        return baseMapper.updateByPrimaryKeySelective(record);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public int updateByPrimaryKey(T record) {
        return baseMapper.updateByPrimaryKey(record);
    }

}
