package com.ifsaid.baodao.entity;

import com.ifsaid.baodao.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tb_role")
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_rid")
    private Integer rid;

    @Column(name = "t_name")
    private String name;

    @Column(name = "t_describe")
    private String describe;

    @Column(name = "t_state")
    private Integer state;

    @Transient
    private Set<Permission> permissions;


}