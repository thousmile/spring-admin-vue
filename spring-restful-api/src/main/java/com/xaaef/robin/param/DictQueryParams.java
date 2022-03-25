package com.xaaef.robin.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * <p>
 * 字典查询，参数
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 */

@Data
@ToString
@EqualsAndHashCode
public class DictQueryParams extends QueryParams {

    /**
     * 字典类型
     */
    private String dictTypeKey;

}
