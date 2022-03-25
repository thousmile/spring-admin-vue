package com.xaaef.robin.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * <p>
 * 基础参数传递
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @date 2021/10/21 21:38
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
public class QueryParams implements java.io.Serializable {

    public static QueryParams build() {
        return new QueryParams();
    }

    public static QueryParams build(Integer pageNum, Integer pageSize) {
        return new QueryParams(pageNum, pageSize, null, null, null);
    }

    public static QueryParams build(Integer pageNum, Integer pageSize, String keywords) {
        return new QueryParams(pageNum, pageSize, keywords, null, null);
    }

    public QueryParams() {
        this.pageNum = 1;
        this.pageSize = 10;
    }

    /**
     * 当前第几页
     */
    private Integer pageNum;

    /**
     * 每页多少条数据
     */
    private Integer pageSize;

    /**
     * 搜索，关键字
     */
    private String keywords;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

}
