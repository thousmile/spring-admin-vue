package com.ifsaid.baodao.entity;

import com.ifsaid.baodao.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tb_dept")
@Data
@EqualsAndHashCode(callSuper = false)
public class Dept extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_id")
    private Integer id;

    @Column(name = "t_name")
    private String name;

    @Column(name = "t_parent_id")
    private Integer parentId;

    @Column(name = "t_level")
    private Integer level;

    @Column(name = "t_describe")
    private String describe;

    @Transient
    private Set<Dept> children;


}