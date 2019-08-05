package com.ifsaid.baodao.entity;

import com.ifsaid.baodao.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_category")
@Data
@EqualsAndHashCode(callSuper = false)
public class Category extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_cid")
    private Integer cid;

    @Column(name = "t_title")
    private String title;

    @Column(name = "t_describe")
    private String describe;

    @Column(name = "t_region")
    private String region;

}