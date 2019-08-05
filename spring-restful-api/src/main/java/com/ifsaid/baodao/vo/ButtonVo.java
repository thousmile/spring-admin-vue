package com.ifsaid.baodao.vo;

import lombok.Data;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/29 15:33
 * @describe：
 * @version: 1.0
 */

@Data
public class ButtonVo {

    private Integer pid;

    private String resources;

    private String title;

    public ButtonVo() {
    }

    public ButtonVo(Integer pid, String resources, String title) {
        this.pid = pid;
        this.resources = resources;
        this.title = title;
    }
}
