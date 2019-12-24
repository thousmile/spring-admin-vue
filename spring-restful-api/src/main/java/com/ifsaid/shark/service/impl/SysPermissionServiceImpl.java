package com.ifsaid.shark.service.impl;

import com.ifsaid.shark.common.service.impl.BaseServiceImpl;
import com.ifsaid.shark.entity.SysPermission;
import com.ifsaid.shark.mapper.SysPermissionMapper;
import com.ifsaid.shark.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 权限 Service 实现类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
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

}
