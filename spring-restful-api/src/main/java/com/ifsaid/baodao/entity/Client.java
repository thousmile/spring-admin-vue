package com.ifsaid.baodao.entity;

import com.ifsaid.baodao.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 15:19
 * @describe：
 * @version: 1.0
 */


@Entity
@Table(name = "tb_client")
@Data
@EqualsAndHashCode(callSuper = false)
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Client extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "t_client_id")
    private String clientId;

    @Column(name = "t_client_secret")
    private String clientSecret;

    @Column(name = "t_scope")
    private String scope;

    @Column(name = "t_state")
    private Integer state;

    @Column(name = "t_describe")
    private String describe;

}
