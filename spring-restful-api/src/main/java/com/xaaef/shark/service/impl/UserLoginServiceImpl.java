package com.xaaef.shark.service.impl;

import com.xaaef.shark.common.jwt.JwtLoginUser;
import com.xaaef.shark.common.jwt.JwtTokenUtils;
import com.xaaef.shark.constant.LoginConstant;
import com.xaaef.shark.constant.PermissionType;
import com.xaaef.shark.entity.SysDepartment;
import com.xaaef.shark.entity.SysPermission;
import com.xaaef.shark.entity.SysRole;
import com.xaaef.shark.entity.SysUser;
import com.xaaef.shark.exception.JwtAuthenticationException;
import com.xaaef.shark.service.*;
import com.xaaef.shark.util.LocalTimeUtils;
import com.xaaef.shark.util.SecurityUtils;
import com.xaaef.shark.vo.ButtonVo;
import com.xaaef.shark.vo.MenuVo;
import com.xaaef.shark.vo.TokenValue;
import com.xaaef.shark.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDepartmentService sysDepartmentService;

    @Autowired
    @Lazy
    private RedisTemplate<String, Object> redisTemplate;

    private BoundHashOperations<String, String, Object> tokenStorage() {
        return redisTemplate.boundHashOps(jwtTokenUtils.getTokenHeader());
    }

    /**
     * 登录表单获取 Token
     *
     * @return
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    @Override
    public TokenValue login(String username, String password) throws AuthenticationException {
        // 把表单提交的 username  password 封装到 UsernamePasswordAuthenticationToken中
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtLoginUser userDetails = (JwtLoginUser) authentication.getPrincipal();

        // 生成一个随机ID 跟当前用户关联
        String loginId = jwtTokenUtils.createLoginId();

        String token = jwtTokenUtils.createToken(loginId);
        log.debug("userDetails: {}", userDetails);

        setLoginUser(loginId, userDetails);

        return TokenValue.builder()
                .header(jwtTokenUtils.getTokenHeader())
                .value(token)
                .prefix(jwtTokenUtils.getTokenHead())
                .expiration(jwtTokenUtils.getExpiration())
                .build();
    }

    @Override
    public void logout() {
        JwtLoginUser loginUser = SecurityUtils.getLoginUser();
        // 移除登录的用户。根据tokenId
        removeLoginUser(loginUser.getLoginId());
    }

    private void removeLoginUser(String loginId) {
        redisTemplate.delete(loginId);
    }

    private JwtLoginUser getLoginUser(String loginId) {
        JwtLoginUser loginUser = (JwtLoginUser) redisTemplate
                .opsForValue().get(LoginConstant.LOGIN_TOKEN_KEY + loginId);
        return loginUser;
    }

    private void setLoginUser(String loginId, JwtLoginUser loginUser) {
        loginUser.setLoginId(loginId);
        String loginKey = LoginConstant.LOGIN_TOKEN_KEY + loginId;
        // 将随机id 跟 当前登录的用户关联，在一起！
        redisTemplate.opsForValue().set(
                loginKey,
                loginUser,
                jwtTokenUtils.getExpiration(),
                TimeUnit.MINUTES
        );
        // 判断是否开启 单点登录
        if (jwtTokenUtils.getSso()) {
            String onlineUserKey = LoginConstant.ONLINE_USER_KEY + loginUser.getUsername();

            String oldLoginKey = (String) redisTemplate.opsForValue().get(onlineUserKey);
            // 判断用户名。是否已经登录了！
            if (StringUtils.hasText(oldLoginKey)) {
                // 移除之前登录的用户
                removeLoginUser(LoginConstant.LOGIN_TOKEN_KEY + oldLoginKey);

                // 移除在线用户
                removeLoginUser(onlineUserKey);

                // 获取当前时间
                String milli = LocalTimeUtils.currentSecondFormat();
                // 将 被强制挤下线的用户，以及时间，保存到 redis中，提示给前端用户！
                redisTemplate.opsForValue().set(
                        LoginConstant.FORCED_OFFLINE_KEY + oldLoginKey,
                        milli,
                        5,
                        TimeUnit.MINUTES
                );
            }
            redisTemplate.opsForValue().set(
                    onlineUserKey,
                    loginId,
                    jwtTokenUtils.getExpiration(),
                    TimeUnit.MINUTES
            );
        }
    }

    @Override
    public TokenValue refresh() {
        JwtLoginUser loginUser = SecurityUtils.getLoginUser();
        // 移除登录的用户。根据tokenId
        removeLoginUser(loginUser.getLoginId());

        // 生成一个随机ID 跟当前用户关联
        String loginId = jwtTokenUtils.createLoginId();

        // 重新生成token
        String token = jwtTokenUtils.createToken(loginId);

        setLoginUser(loginId, loginUser);

        return TokenValue.builder()
                .header(jwtTokenUtils.getTokenHeader())
                .value(token)
                .prefix(jwtTokenUtils.getTokenHead())
                .expiration(jwtTokenUtils.getExpiration())
                .build();
    }

    @Override
    public JwtLoginUser validateUser(String loginId) throws AuthenticationException {
        JwtLoginUser jwtUser = getLoginUser(loginId);
        if (jwtUser == null || StringUtils.isEmpty(jwtUser.getUsername())) {
            // 判断是否启用单点登录
            if (jwtTokenUtils.getSso()) {
                String key = LoginConstant.FORCED_OFFLINE_KEY + loginId;
                // 判断此用户，是不是被挤下线
                String offlineTime = (String) redisTemplate.opsForValue().get(key);
                if (StringUtils.hasText(offlineTime)) {
                    // 删除 被挤下线 的消息提示
                    removeLoginUser(key);
                    String errMsg = String.format("您的账号在[ %s ]被其他用户拥下线了！", offlineTime);
                    log.info("errMsg {}", errMsg);
                    throw new JwtAuthenticationException(errMsg);
                }
            }
            throw new JwtAuthenticationException("当前登录用户不存在");
        }
        jwtUser.setLoginId(loginId);
        return jwtUser;
    }

    @Override
    public UserVo findUserInfo() {
        // 从SecurityContextHolder中获取到，当前登录的用户信息。
        JwtLoginUser userDetails = SecurityUtils.getLoginUser();
        // 根据用户Id，获取用户详细信息。
        SysUser sysUser = sysUserService.findById(userDetails.getUid());
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
        String departmentName = sysDepartmentService.findById(sysUser.getDeptId()).getName();
        result.setDepartmentName(departmentName);
        result.setRoles(rolesName);
        return result;
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


}
