package com.ifsaid.admin.vo;

import lombok.Data;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/26 9:54
 * @describe：
 * @version: 1.0
 */

@Data
public class MyPage {

    private int pageNum = 1;

    private int pageSize = 10;

    private String search = null;

}
