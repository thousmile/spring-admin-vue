package com.ifsaid.baodao.vo;

import lombok.Data;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/31 9:55
 * @describe：
 * @version: 1.0
 */

@Data
public class Relation {

    private Integer pid;

    private Integer rid;

    private String uid;

    public Relation(Integer pid, Integer rid) {
        this.pid = pid;
        this.rid = rid;
    }

    public Relation(Integer rid, String uid) {
        this.rid = rid;
        this.uid = uid;
    }

    public Relation() {
    }
}
