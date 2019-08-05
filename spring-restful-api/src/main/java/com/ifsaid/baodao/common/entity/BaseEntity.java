package com.ifsaid.baodao.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 12:58
 * @describe： 通用实体类
 * @version: 1.0
 */

@MappedSuperclass
@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {

    @Column(name = "t_create_time")
    private Date createTime;

    @Column(name = "t_up_time")
    private Date upTime;

}
