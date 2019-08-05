package com.ifsaid.baodao.service.impl;

import com.ifsaid.baodao.common.constant.PermissionType;
import com.ifsaid.baodao.common.exception.JpaCrudException;
import com.ifsaid.baodao.common.exception.UserExistsException;
import com.ifsaid.baodao.common.jwt.JwtTokenUtil;
import com.ifsaid.baodao.common.jwt.JwtUser;
import com.ifsaid.baodao.common.service.impl.BaseServiceImpl;
import com.ifsaid.baodao.entity.Dept;
import com.ifsaid.baodao.entity.Permission;
import com.ifsaid.baodao.entity.Role;
import com.ifsaid.baodao.entity.User;
import com.ifsaid.baodao.repository.UserRepository;
import com.ifsaid.baodao.service.*;
import com.ifsaid.baodao.vo.ButtonVo;
import com.ifsaid.baodao.vo.MenuVo;
import com.ifsaid.baodao.vo.MyPage;
import com.ifsaid.baodao.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 14:27
 * @describe： 用户 Service 实现类
 * @version: 1.0
 */

@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String, UserRepository> implements IUserService {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private IRedisService redisService;

    @Autowired
    private IPermissionService permissionService;


    @Override
    public User findByAccount(String account) {
        return baseRepository.findByAccount(account);
    }

    @Override
    public User findByOpenId(String openId) {
        return baseRepository.findByOpenId(openId);
    }

    /**
     * @describe 根据账号查询用户信息
     * @date 2018/11/17
     * @author Wang Chen Chen
     */
    @Override
    public UserVo findUserInfo(String account) {
        User user = findByAccount(account);
        UserVo result = new UserVo(user.getUid(), user.getAvatar(), user.getNickname(), user.getAccount(), user.getMail());
        Set<Permission> permissions = permissionService.findAllByUserId(user.getUid());
        Set<ButtonVo> buttonVos = new HashSet<>();
        Set<MenuVo> menuVos = new HashSet<>();
        if (permissions != null && permissions.size() > 1) {
            permissions.forEach(permission -> {
                if (permission.getType().toLowerCase().equals(PermissionType.BUTTON)) {
                    /*
                     * 如果权限是按钮，就添加到按钮里面
                     * */
                    buttonVos.add(new ButtonVo(permission.getPid(), permission.getResources(), permission.getTitle()));
                }
                if (permission.getType().toLowerCase().equals(PermissionType.MENU)) {
                    /*
                     * 如果权限是菜单，就添加到菜单里面
                     * */
                    menuVos.add(new MenuVo(permission.getPid(), permission.getParentId(), permission.getIcon(), permission.getResources(), permission.getTitle()));
                }
            });
        }
        result.setButtons(buttonVos);
        result.setMenus(findRoots(menuVos));
        return result;
    }


    /**
     * 递归查找根节点
     *
     * @param allNodes
     * @return
     */
    private Set<MenuVo> findRoots(Set<MenuVo> allNodes) {
        // 根节点
        Set<MenuVo> root = new HashSet<>();
        allNodes.forEach(node -> {
            if (node.getFather() == 0) {
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
     * @param treeNodes
     * @return
     */
    private MenuVo findChildren(MenuVo treeNode, Set<MenuVo> treeNodes) {
        for (MenuVo it : treeNodes) {
            if (treeNode.getPid().equals(it.getFather())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<MenuVo>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
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
        String token = jwtTokenUtil.generateToken(userDetails);
        log.info("userDetails: {}", userDetails);
        redisService.addMap(jwtTokenUtil.getTokenHeader(), userDetails.getUsername(), userDetails, jwtTokenUtil.getExpiration());
        return token;
    }

    /**
     * @describe 注册用户
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    @Override
    public User register(User user) throws UserExistsException {
        if (baseRepository.countByAccountOrMail(user.getAccount()) != null) {
            throw new UserExistsException(String.format("'%s' 这个用户已经存在了", user.getAccount()));
        }
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setUpTime(new Date());
        user.setCreateTime(new Date());
        return baseRepository.save(user);
    }


    /**
     * @describe 刷新 Token
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    @Override
    public String refreshToken(String oldToken) {
        if (oldToken != null && StringUtils.isNotEmpty(oldToken)) {
            // The part after "Bearer "
            final String authToken = oldToken.substring(this.jwtTokenUtil.getTokenHead().length());
            // 获取账号
            return jwtTokenUtil.refreshToken(authToken);
        }
        return null;
    }

    @Override
    public int countByDept(Integer deptId) {
        return baseRepository.countByDept(deptId);
    }

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IRoleService roleService;

    @Override
    public Page<UserVo> findAllInfo(Pageable page) {
        Page<User> all = findAll(page);
        List<UserVo> userVos = new ArrayList<>();
        all.forEach(user -> {
            Dept dept = deptService.findById(user.getDept());
            Set<Role> roles = roleService.findAllByUserId(user.getUid());
            UserVo userVo = new UserVo(user.getUid(),
                    user.getAvatar(), user.getNickname(),
                    user.getAccount(), user.getMail(), user.getGender(),
                    user.getBirthday(), user.getState(), dept, roles,
                    user.getCreateTime(), user.getUpTime()
            );
            userVos.add(userVo);
        });
        return new PageImpl<>(userVos, page, all.getTotalElements());
    }

    @Override
    public User save(User entity) throws JpaCrudException {
        String rawPassword = entity.getPassword();
        entity.setPassword(passwordEncoder.encode(rawPassword));
        entity.setOpenId(" ");
        return super.save(entity);
    }

    @Override
    public User update(User entity) throws JpaCrudException {
        User user = findById(entity.getUid());
        if (entity.getAccount() != null && entity.getAccount().trim() != "") {
            user.setAccount(entity.getAccount());
        }
        if (entity.getPassword() != null && entity.getPassword().trim() != "") {
            String rawPassword = entity.getPassword();
            user.setPassword(passwordEncoder.encode(rawPassword));
        }
        if (entity.getAvatar() != null && entity.getAvatar().trim() != "") {
            user.setAvatar(entity.getAvatar());
        }
        if (entity.getNickname() != null && entity.getNickname().trim() != "") {
            user.setNickname(entity.getNickname());
        }
        if (entity.getMail() != null && entity.getMail().trim() != "") {
            user.setMail(entity.getMail());
        }
        if (entity.getOpenId() != null && entity.getOpenId().trim() != "") {
            user.setOpenId(entity.getOpenId());
        }
        if (entity.getBirthday() != null) {
            user.setBirthday(entity.getBirthday());
        }
        if (entity.getDept() != null) {
            user.setDept(entity.getDept());
        }
        if (entity.getGender() != null) {
            user.setGender(entity.getGender());
        }
        if (entity.getState() != null) {
            user.setState(entity.getState());
        }
        return super.update(user);
    }


}
