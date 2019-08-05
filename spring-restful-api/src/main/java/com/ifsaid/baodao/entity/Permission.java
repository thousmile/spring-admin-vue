package com.ifsaid.baodao.entity;

import com.ifsaid.baodao.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tb_permission")
@Data
@EqualsAndHashCode(callSuper = false)
public class Permission extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_pid")
    private Integer pid;

    @Column(name = "t_parent_id")
    private Integer parentId;

    @Column(name = "t_resources")
    private String resources;

    @Column(name = "t_title")
    private String title;

    @Column(name = "t_icon")
    private String icon;

    @Column(name = "t_type")
    private String type;

    @Column(name = "t_describe")
    private String describe;

    @Transient
    private Set<Permission> children;


}