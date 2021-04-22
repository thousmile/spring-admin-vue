package com.xaaef.shark.service.impl;

import com.xaaef.shark.common.service.impl.BaseServiceImpl;
import com.xaaef.shark.entity.SysDepartment;
import com.xaaef.shark.mapper.SysDepartmentMapper;
import com.xaaef.shark.service.SysDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 部门 Service 实现类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */

@Slf4j
@Service
public class SysDepartmentServiceImpl extends BaseServiceImpl<SysDepartment, Integer, SysDepartmentMapper> implements SysDepartmentService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteById(Integer id) {
        if (baseMapper.haveChildren(id) > 0) {
            throw new RuntimeException("当前部门下，还有子部门，无法删除！");
        }
        if (baseMapper.haveUsers(id) > 0) {
            throw new RuntimeException("当前部门下，还有其用户在使用，无法删除！");
        }
        return baseMapper.deleteByPrimaryKey(id);
    }

}
