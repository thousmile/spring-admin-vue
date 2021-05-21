package com.xaaef.shark.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xaaef.shark.common.jwt.JwtLoginUser;
import com.xaaef.shark.common.jwt.JwtTokenUtils;
import com.xaaef.shark.common.service.impl.BaseServiceImpl;
import com.xaaef.shark.constant.PermissionType;
import com.xaaef.shark.constant.StatusConstant;
import com.xaaef.shark.entity.Relation;
import com.xaaef.shark.entity.SysPermission;
import com.xaaef.shark.entity.SysRole;
import com.xaaef.shark.entity.SysUser;
import com.xaaef.shark.exception.JwtAuthenticationException;
import com.xaaef.shark.mapper.SysUserMapper;
import com.xaaef.shark.service.SysDepartmentService;
import com.xaaef.shark.service.SysPermissionService;
import com.xaaef.shark.service.SysRoleService;
import com.xaaef.shark.service.SysUserService;
import com.xaaef.shark.util.QueryParameter;
import com.xaaef.shark.util.SecurityUtils;
import com.xaaef.shark.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * 用户 Service 实现类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */

@Slf4j
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Integer, SysUserMapper> implements SysUserService {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysDepartmentService departmentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public PageInfo<SysUserVo> findAllPageInfo(QueryParameter parameter) {
        PageInfo<SysUser> pageInfo = PageHelper
                .startPage(parameter.getPageNum(), parameter.getPageSize())
                .doSelectPageInfo(() -> {
                    if (StringUtils.isEmpty(parameter.getKeywords())) {
                        baseMapper.selectAll();
                    } else {
                        baseMapper.selectByKeywords(parameter.getKeywords());
                    }
                });
        List<SysUserVo> collect = pageInfo.getList().stream()
                .map(res -> {
                    SysUserVo sysUserVo = new SysUserVo();
                    BeanUtils.copyProperties(res, sysUserVo);
                    List<SysUserVo.RoleVo> roles = sysRoleService.findAllByUserId(res.getUid())
                            .stream()
                            .map(role -> {
                                return new SysUserVo.RoleVo(role.getRid(), role.getRoleName(), role.getDescription());
                            })
                            .collect(Collectors.toList());
                    sysUserVo.setRoles(roles);
                    String departmentName = departmentService.findById(res.getDeptId()).getName();
                    sysUserVo.setDepartmentName(departmentName);
                    return sysUserVo;
                }).collect(Collectors.toList());
        PageInfo<SysUserVo> result = new PageInfo<>();
        result.setList(collect);
        result.setTotal(pageInfo.getTotal());
        return result;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteById(Integer uid) {
        if (SecurityUtils.isAdmin(uid)) {
            throw new RuntimeException("此用户是管理员，无法删除！");
        }
        // 删除用户拥有的角色
        baseMapper.deleteHaveRoles(uid);
        return baseMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public SysUser findById(Integer uid) {
        SysUser sysUser = baseMapper.selectByPrimaryKey(uid);
        sysUser.setPassword(null);
        return sysUser;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int create(SysUser entity) {
        String encodePassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodePassword);
        entity.setStatus(StatusConstant.NORMAL);
        entity.setCreateTime(LocalDateTime.now());
        entity.setLastUpdateTime(LocalDateTime.now());
        return baseMapper.insertSelective(entity);
    }

    /**
     * 修改用户权限
     *
     * @param uid
     * @param roleIds
     * @return int
     */
    @Override
    public int updateUserRoles(Integer uid, Set<Integer> roleIds) {
        List<Relation> collect = roleIds.stream()
                // 去除重复的角色ID
                .distinct()
                // 构造 Relation 对象
                .map(res -> new Relation(uid, res))
                .collect(Collectors.toList());
        // 先删除当前用户拥有的所有角色
        baseMapper.deleteHaveRoles(uid);
        // 在赋值新的角色
        return baseMapper.insertByRoles(collect);
    }

    @Override
    public int updatePassword(UpdatePassword pwd) {
        SysUser sysUser = baseMapper.selectByPrimaryKey(pwd.getUid());
        if (!pwd.getNewPwd().equals(pwd.getConfirmPwd())) {
            throw new RuntimeException("新密码与确认密码不一致，请重新输入！");
        }
        if (passwordEncoder.matches(pwd.getOldPwd(), sysUser.getPassword())) {
            if (passwordEncoder.matches(pwd.getNewPwd(), sysUser.getPassword())) {
                throw new RuntimeException("新密码与旧密码相同，请重新输入！");
            } else {
                String newPassword = passwordEncoder.encode(pwd.getNewPwd());
                return update(SysUser.builder().uid(sysUser.getUid()).password(newPassword).build());
            }
        }
        throw new RuntimeException("旧密码错误，请重新输入！");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int resetPassword(ResetPassword pwd) {
        SysUser sysUser = baseMapper.selectByPrimaryKey(pwd.getUid());
        if (sysUser != null) {
            String newPassword = passwordEncoder.encode(pwd.getNewPwd());
            return baseMapper.updateByPrimaryKeySelective(
                    SysUser.builder().uid(pwd.getUid()).password(newPassword).build()
            );
        }
        throw new RuntimeException("用户不存在，无法重置密码！");
    }

    @Override
    public int update(SysUser entity) {
        // 不允许使用 update 修改密码！
        entity.setPassword(null);
        if (StringUtils.hasText(entity.getUsername())) {
            int count = baseMapper.selectCount(SysUser.builder().username(entity.getUsername()).build());
            if (count > 0) {
                throw new RuntimeException(String.format("用户名“ %s ”已存在，请重新输入！", entity.getUsername()));
            }
        }
        return super.update(entity);
    }
}
