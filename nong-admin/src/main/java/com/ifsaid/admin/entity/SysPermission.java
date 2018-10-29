package com.ifsaid.admin.entity;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class SysPermission {

    private Integer pid;

    private Integer father;

    private String resources;

    private String title;

    private String icon;

    private String type;

    private Date addTime;

    private Date upTime;

    private String describe;

    private Set<SysPermission> childNodes;

}