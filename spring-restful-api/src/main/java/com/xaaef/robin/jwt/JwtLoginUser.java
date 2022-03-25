package com.xaaef.robin.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xaaef.robin.enums.StatusEnum;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * spring security 认证 UserDetails 实现类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 */

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtLoginUser implements UserDetails {

    /**
     * 用户唯一ID
     */
    private Long userId;

    /**
     * 登录ID
     */
    private String loginId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户登录时，使用的用户名
     */
    private String username;

    /**
     * 用户登录时，使用的密码
     */
    private String password;

    /**
     * 用户状态， [ 0.禁用 1.正常 2.被删除 ]
     */
    private StatusEnum status;

    /**
     * 权限列表
     */
    private Collection<StringGrantedAuthority> authorities;


    @Override
    public Collection<StringGrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<StringGrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public String getUsername() {
        return this.username;
    }


    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return status == StatusEnum.NORMAL;
    }


    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }


    public String getLoginId() {
        return loginId;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
