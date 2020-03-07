package com.ifsaid.shark.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.common.jwt.JwtTokenUtils;
import com.ifsaid.shark.common.jwt.JwtUser;
import com.ifsaid.shark.common.service.impl.BaseServiceImpl;
import com.ifsaid.shark.constant.PermissionType;
import com.ifsaid.shark.constant.StatusConstant;
import com.ifsaid.shark.entity.Relation;
import com.ifsaid.shark.entity.SysPermission;
import com.ifsaid.shark.entity.SysRole;
import com.ifsaid.shark.entity.SysUser;
import com.ifsaid.shark.exception.JwtAuthenticationException;
import com.ifsaid.shark.mapper.SysUserMapper;
import com.ifsaid.shark.service.SysDepartmentService;
import com.ifsaid.shark.service.SysPermissionService;
import com.ifsaid.shark.service.SysRoleService;
import com.ifsaid.shark.service.SysUserService;
import com.ifsaid.shark.util.QueryParameter;
import com.ifsaid.shark.vo.ButtonVo;
import com.ifsaid.shark.vo.MenuVo;
import com.ifsaid.shark.vo.SysUserVo;
import com.ifsaid.shark.vo.UserVo;
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
import org.springframework.security.core.userdetails.UserDetailsService;
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
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 用户 Service 实现类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Slf4j
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Integer, SysUserMapper> implements SysUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysDepartmentService departmentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private RedisTemplate<String, Object> redisTemplate;

    private BoundHashOperations<String, String, Object> tokenStorage() {
        return redisTemplate.boundHashOps(jwtTokenUtils.getTokenHeader());
    }

    /**
     * @describe 登录表单获取 Token
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    @Override
    public String login(String username, String password) throws AuthenticationException {
        // 把表单提交的 username  password 封装到 UsernamePasswordAuthenticationToken中
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(username);
        String token = jwtTokenUtils.generateToken(userDetails);
        log.debug("userDetails: {}", userDetails);
        tokenStorage().put(userDetails.getUsername(), userDetails);
        return token;
    }

    @Override
    public void logout(JwtUser loginUser) {
        tokenStorage().delete(loginUser.getUsername());
    }

    @Override
    public JwtUser validateUsername(String username) throws AuthenticationException {
        JwtUser jwtUser = (JwtUser) tokenStorage().get(username);
        if (jwtUser == null || StringUtils.isEmpty(jwtUser.getUsername())) {
            throw new JwtAuthenticationException("当前登录用户不存在");
        }
        return jwtUser;
    }

    @Override
    public UserVo findUserInfo() {
        // 从SecurityContextHolder中获取到，当前登录的用户信息。
        JwtUser userDetails = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 根据用户Id，获取用户详细信息。
        SysUser sysUser = findById(userDetails.getUid());
        UserVo result = new UserVo();
        BeanUtils.copyProperties(sysUser, result);
        // 根据用户Id，获取到拥有的 权限列表
        Set<SysPermission> permissions = sysPermissionService.findAllByUserId(sysUser.getUid());
        List<ButtonVo> buttonVos = new ArrayList<>();
        List<MenuVo> menuVos = new ArrayList<>();
        if (permissions != null && permissions.size() > 1) {
            permissions.forEach(permission -> {
                if (permission.getType().toLowerCase().equals(PermissionType.BUTTON)) {
                    /*
                     * 如果权限是按钮，就添加到按钮里面
                     * */
                    buttonVos.add(
                            new ButtonVo(
                                    permission.getPid(),
                                    permission.getResources(),
                                    permission.getTitle())
                    );
                }
                if (permission.getType().toLowerCase().equals(PermissionType.MENU)) {
                    /*
                     * 如果权限是菜单，就添加到菜单里面
                     * */
                    menuVos.add(
                            new MenuVo(
                                    permission.getPid(),
                                    permission.getParentId(),
                                    permission.getIcon(),
                                    permission.getResources(),
                                    permission.getTitle(),
                                    null
                            )
                    );
                }
            });
        }
        result.setButtons(buttonVos);
        result.setMenus(findRoots(menuVos));
        Set<SysRole> roles = sysRoleService.findAllByUserId(result.getUid());
        Set<String> rolesName = roles
                .stream()
                .map(r -> r.getDescription())
                .collect(Collectors.toSet());
        result.setRoles(rolesName);
        return result;
    }

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
     * 递归查找根节点
     *
     * @param allNodes
     * @return Set<MenuVo>
     */
    private List<MenuVo> findRoots(List<MenuVo> allNodes) {
        // 根节点
        List<MenuVo> root = new ArrayList<>();
        allNodes.forEach(node -> {
            if (node.getParentId() == 0) {
                root.add(node);
            }
        });
        root.forEach(node -> {
            findChildren(node, allNodes);
        });
        return root;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNode
     * @param treeNodes
     * @return MenuVo
     */
    private MenuVo findChildren(MenuVo treeNode, List<MenuVo> treeNodes) {
        for (MenuVo it : treeNodes) {
            if (treeNode.getPid().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
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

}
