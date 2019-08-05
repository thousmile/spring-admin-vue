package com.ifsaid.baodao.entity;

import com.ifsaid.baodao.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "tb_user")
@Data
@EqualsAndHashCode(callSuper = false)
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "t_uid")
    private String uid;

    @Column(name = "t_avatar")
    private String avatar;

    @Column(name = "t_account")
    private String account;

    @Column(name = "t_mail")
    private String mail;

    @Column(name = "t_open_id")
    private String openId;

    @Column(name = "t_nickname")
    private String nickname;

    @Column(name = "t_password")
    private String password;

    @Column(name = "t_gender")
    private Integer gender;

    @Column(name = "t_birthday")
    private Date birthday;

    @Column(name = "t_state")
    private Integer state;

    @Column(name = "t_dept")
    private Integer dept;


}