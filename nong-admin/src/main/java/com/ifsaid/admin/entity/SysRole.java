package com.ifsaid.admin.entity;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class SysRole {

    private Integer rid;

    private String describe;

    private String name;

    private Integer state;

    private Date upTime;

    private Date addTime;

    // 权限列表
    private Set<SysPermission> permissions;

}