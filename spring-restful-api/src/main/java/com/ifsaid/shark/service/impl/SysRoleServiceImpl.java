package com.ifsaid.shark.service.impl;

import com.ifsaid.shark.common.service.impl.BaseServiceImpl;
import com.ifsaid.shark.entity.Relation;
import com.ifsaid.shark.entity.SysRole;
import com.ifsaid.shark.mapper.SysRoleMapper;
import com.ifsaid.shark.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 角色 Service 接口
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole, Integer, SysRoleMapper> implements SysRoleService {

    @Override
    public int updateRolePermissions(Integer rid, Set<Integer> permissionIds) {
        List<Relation> collect = permissionIds.stream()
                // 去除重复的 权限 ID
                .distinct()
                .filter(pid -> pid != null && pid > 0)
                // 构造 Relation 对象
                .map(res -> new Relation(rid, res))
                .collect(Collectors.toList());
        log.info("collect: {}", collect);
        // 先删除当前角色，拥有的所有权限
        baseMapper.deleteHavePermissions(rid);
        // 在赋值新的 权限
        return baseMapper.insertByPermissions(collect);
    }

    @Override
    public Set<SysRole> findAllByUserId(Integer uid) {
        return baseMapper.findAllByUserId(uid);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteById(Integer rid) {
        if (baseMapper.userReference(rid) > 0) {
            throw new RuntimeException("当前角色，还有其他用户引用，无法删除！");
        }
        // 删除当前角色，拥有的权限
        baseMapper.deleteHavePermissions(rid);
        // 删除角色
        return baseMapper.deleteByPrimaryKey(rid);
    }

}
