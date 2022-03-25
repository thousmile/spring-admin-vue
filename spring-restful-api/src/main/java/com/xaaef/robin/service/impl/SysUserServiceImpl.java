package com.xaaef.robin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xaaef.robin.base.service.impl.BaseServiceImpl;
import com.xaaef.robin.entity.SysPermission;
import com.xaaef.robin.entity.SysRole;
import com.xaaef.robin.entity.SysUser;
import com.xaaef.robin.enums.AdminFlagEnum;
import com.xaaef.robin.enums.StatusEnum;
import com.xaaef.robin.jwt.JwtLoginUser;
import com.xaaef.robin.jwt.JwtSecurityUtils;
import com.xaaef.robin.jwt.StringGrantedAuthority;
import com.xaaef.robin.param.UserQueryParams;
import com.xaaef.robin.service.*;
import com.xaaef.robin.mapper.SysUserMapper;
import com.xaaef.robin.util.IdUtils;
import com.xaaef.robin.util.TreeNodeUtils;
import com.xaaef.robin.vo.*;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.xaaef.robin.constant.ConfigKey.*;
import static com.xaaef.robin.enums.PermissionTypeEnum.*;


/**
 * @author WangChenChen
 * @description 针对表【sys_user([ 权限 ] 用户表)】的数据库操作Service实现
 * @createDate 2022-03-22 09:59:32
 */


@Service
@AllArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser>
        implements SysUserService, UserDetailsService {

    private SysPermissionService permissionService;

    private SysRoleService roleService;

    private SysDeptService deptService;

    private SysConfigService configService;


    @Override
    public IPage<SysUser> pageKeywords(UserQueryParams params) {
        Page<SysUser> page = Page.of(params.getPageNum(), params.getPageSize());
        page.addOrder(OrderItem.desc("last_update_time"));
        var result = baseMapper.selectKeywordsList(page, params);
        Set<Long> collect = result.getRecords().stream().map(SysUser::getUserId).collect(Collectors.toSet());
        Map<Long, Set<SysRole>> rolesMap = roleService.listByUserIds(collect);
        result.getRecords().forEach(user -> user.setRoles(rolesMap.get(user.getUserId())));
        return result;
    }


    @Override
    public Set<UserIdAndNicknameVO> listAllSimpleUser() {
        // 系统所有的系统用户
        var systemWrapper = new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getUserId, SysUser::getAvatar, SysUser::getNickname);
        return baseMapper.selectList(systemWrapper)
                .stream()
                .map(r -> new UserIdAndNicknameVO(r.getUserId(), r.getNickname(), r.getAvatar()))
                .collect(Collectors.toSet());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(SysUser entity) {
        if (StringUtils.isBlank(entity.getUsername()) && exist(SysUser::getUsername, entity.getUsername())) {
            throw new RuntimeException(String.format("用户名 %s 已经存在了！", entity.getUsername()));
        }
        if (StringUtils.isBlank(entity.getMobile()) && exist(SysUser::getMobile, entity.getMobile())) {
            throw new RuntimeException(String.format("手机号码 %s 已经存在了！", entity.getMobile()));
        }
        if (StringUtils.isBlank(entity.getEmail()) && exist(SysUser::getEmail, entity.getEmail())) {
            throw new RuntimeException(String.format("邮箱账号 %s 已经存在了！", entity.getEmail()));
        }

        // 如果用户密码，
        if (StringUtils.isBlank(entity.getPassword())) {
            String userDefaultPassword = configService.findValueByKey(USER_DEFAULT_PASSWORD);
            entity.setPassword(userDefaultPassword);
        }

        // 如果 头像为空
        if (StringUtils.isBlank(entity.getAvatar())) {
            String defaultLogo = configService.findValueByKey(DEFAULT_LOGO);
            entity.setAvatar(defaultLogo);
        }

        // 密码加密
        entity.setPassword(JwtSecurityUtils.encryptPassword(entity.getPassword()));

        // 新增的用户全部都是 普通用户
        entity.setAdminFlag(AdminFlagEnum.USER);

        if (entity.getStatus() == null) {
            entity.setStatus(StatusEnum.NORMAL);
        }

        // 使用单机版雪花ID算法
        entity.setUserId(IdUtils.getStandaloneId());

        var ok = super.save(entity);
        if (entity.getRoles() != null && entity.getRoles().size() > 0) {
            // 角色列表
            var roleIds = entity.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toSet());
            updateRoles(entity.getUserId(), roleIds);
        }

        return ok;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        SysUser user = getById(id);
        if (user == null) {
            throw new RuntimeException(String.format("用户id为 [ %s ] 的用户不存在！", id));
        }
        // 租户的管理员用户，是无法删除的！
        if (user.getAdminFlag() == AdminFlagEnum.ADMIN) {
            throw new RuntimeException(String.format("用户[ %s ] 是管理员，无法删除！", user.getNickname()));
        }
        baseMapper.deleteHaveRoles(user.getUserId());
        return super.removeById(id);
    }


    @Override
    public boolean updateById(SysUser entity) {
        if (entity.getRoles() != null && entity.getRoles().size() > 0) {
            // 角色列表
            var roleIds = entity.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toSet());
            // 修改 原有角色
            updateRoles(entity.getUserId(), roleIds);
        }
        entity.setPassword(null);
        return super.updateById(entity);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePassword(UpdatePasswordVO pwd) {
        SysUser sysUser = baseMapper.selectById(pwd.getUserId());
        if (!StringUtils.equals(pwd.getNewPwd(), pwd.getConfirmPwd())) {
            throw new RuntimeException("新密码与确认密码不一致，请重新输入！");
        }
        // 判断 老密码是否正确。
        if (JwtSecurityUtils.matchesPassword(pwd.getOldPwd(), sysUser.getPassword())) {
            // 判断 新密码 和 老密码是否相同
            if (JwtSecurityUtils.matchesPassword(pwd.getNewPwd(), sysUser.getPassword())) {
                throw new RuntimeException("新密码与旧密码相同，请重新输入！");
            } else {
                // 新密码 加密
                String newPassword = JwtSecurityUtils.encryptPassword(pwd.getNewPwd());
                // 修改
                return updateById(
                        SysUser.builder()
                                .userId(pwd.getUserId())
                                .password(newPassword)
                                .build()
                );
            }
        }
        throw new RuntimeException("旧密码错误，请重新输入！");
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean resetPassword(ResetPasswordVO pwd) {
        SysUser sysUser = baseMapper.selectById(pwd.getUserId());
        if (sysUser != null) {
            // 新密码 加密
            String newPassword = JwtSecurityUtils.encryptPassword(pwd.getNewPwd());
            // 修改
            return updateById(
                    SysUser.builder()
                            .userId(pwd.getUserId())
                            .password(newPassword)
                            .build()
            );
        }
        throw new RuntimeException("用户不存在，无法重置密码！");
    }


    @Override
    public SysUserVO getUserInfo(Long userId) {
        var sysUser = baseMapper.selectById(userId);
        // 用户菜单权限
        List<SysPermission> userMenus = new ArrayList<>();
        // 如果是管理员，就获取全部的权限
        if (sysUser.getAdminFlag() == AdminFlagEnum.ADMIN) {
            // 系统管理员就获取全部的菜单
            userMenus.addAll(permissionService.list());
        } else {
            // 获取当前 用户 自己的权限
            userMenus.addAll(permissionService.listByUserId(userId));
            // 获取当前 用户 所在部门的权限
            userMenus.addAll(permissionService.listByDeptId(sysUser.getDeptId()));
        }

        // 全部菜单
        var allNodes = userMenus.stream()
                .distinct()
                .filter(r -> r.getPermType() == MENU)
                .map(r -> {
                    MenuVO build = new MenuVO();
                    build.setId(r.getPermissionId());
                    build.setTitle(r.getTitle());
                    build.setPerms(r.getPerms());
                    build.setIcon(r.getIcon());
                    build.setParentId(r.getParentId());
                    build.setSort(r.getSort());
                    return build;
                })
                .collect(Collectors.toList());

        // 将 菜单列表，递归成 树节点的形式
        var treeMenus = TreeNodeUtils.findRoots(allNodes);

        // 获取所有的按钮
        var buttons = userMenus.stream()
                .distinct()
                .filter(r -> r.getPermType() == BUTTON)
                .map(r -> ButtonVO.builder().perms(r.getPerms()).title(r.getTitle()).build())
                .collect(Collectors.toList());

        SysUserVO result = new SysUserVO();
        BeanUtils.copyProperties(sysUser, result);
        result.setPassword(null);
        result.setRoles(roleService.listByUserId(sysUser.getUserId()));
        result.setDept(deptService.getById(sysUser.getDeptId()));
        result.setButtons(buttons);
        result.setMenus(treeMenus);
        return result;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRoles(Long userId, Set<Long> roleIds) {
        SysUser dbUser = getById(userId);
        if (dbUser == null) {
            throw new RuntimeException("用户不存在！");
        }
        // 先删除当前用户拥有的所有角色
        baseMapper.deleteHaveRoles(userId);
        // 在赋值新的角色
        return baseMapper.insertByRoles(userId, roleIds) > 0;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var dbUser = getOne(SysUser::getUsername, username);
        if (dbUser == null || StringUtils.isEmpty(dbUser.getUsername())) {
            throw new UsernameNotFoundException(String.format("'%s'.此用户不存在", username));
        } else {
            // 获取此用户的权限列表
            var authorities = permissionService.listSimpleByUserId(dbUser.getUserId())
                    .stream()
                    .map(StringGrantedAuthority::new)
                    .collect(Collectors.toSet());
            return JwtLoginUser.builder()
                    .userId(dbUser.getUserId())
                    .avatar(dbUser.getAvatar())
                    .username(dbUser.getUsername())
                    .nickname(dbUser.getNickname())
                    .password(dbUser.getPassword())
                    .status(dbUser.getStatus())
                    .authorities(authorities)
                    .build();
        }
    }


}




