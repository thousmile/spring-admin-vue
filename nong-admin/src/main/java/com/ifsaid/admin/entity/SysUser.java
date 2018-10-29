package com.ifsaid.admin.entity;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class SysUser {

    private String uid;

    private String avatar;

    private String username;

    private String nickname;

    private String password;

    private String phone;

    private String mail;

    private Integer state;

    private Date addTime;

    private Date upTime;

    private Integer dept;

    private Set<SysRole> roles;

}