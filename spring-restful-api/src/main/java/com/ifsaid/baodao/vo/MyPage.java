package com.ifsaid.baodao.vo;

import lombok.Data;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/26 9:54
 * @describe：
 * @version: 1.0
 */

@Data
public class MyPage {

    private int page = 0;

    private int size = 10;

    private String search = null;

    private Integer cid = null;

}
