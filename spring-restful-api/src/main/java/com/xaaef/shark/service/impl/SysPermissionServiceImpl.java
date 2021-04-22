package com.xaaef.shark.service.impl;

import com.xaaef.shark.common.service.impl.BaseServiceImpl;
import com.xaaef.shark.entity.SysPermission;
import com.xaaef.shark.mapper.SysPermissionMapper;
import com.xaaef.shark.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 权限 Service 实现类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */

@Slf4j
@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission, Integer, SysPermissionMapper> implements SysPermissionService {

    @Override
    public Set<SysPermission> findAllByUserId(Integer uid) {
        return baseMapper.findAllByUserId(uid);
    }

    @Override
    public Set<SysPermission> findPermissionByRoleId(Integer rid) {
        return baseMapper.findAllByRoleId(rid);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteById(Integer id) {
        if (baseMapper.haveChildren(id) > 0) {
            throw new RuntimeException("当前权限下，还有子权限，无法删除！");
        }
        if (baseMapper.roleReference(id) > 0) {
            throw new RuntimeException("当前权限，还有其他角色在引用，无法删除！");
        }
        return baseMapper.deleteByPrimaryKey(id);
    }

}
