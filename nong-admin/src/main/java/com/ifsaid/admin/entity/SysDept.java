package com.ifsaid.admin.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SysDept {

    private Integer id;

    private String name;

    private Integer parentId;

    private Integer level;

    private String describe;

    private Date upTime;

    private Date addTime;


}