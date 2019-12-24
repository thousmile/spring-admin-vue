package com.ifsaid.shark.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 通用查询参数
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Getter
@Setter
@ToString
public class QueryParameter implements java.io.Serializable {

    /**
     * @description: 当前第几页
     * @date: 2019/12/11 21:02
     */
    private int pageNum = 1;

    /**
     * @description: 每页多少条数据
     * @date: 2019/12/11 21:02
     */
    private int pageSize = 10;

    /**
     * @description: 搜索，关键字
     * @date: 2019/12/11 21:02
     */
    private String keywords;

}
