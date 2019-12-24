package com.ifsaid.shark.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * token 返回值
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/12 23:29
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Getter
@Setter
@Builder
public class TokenValue implements java.io.Serializable {

    /**
     * @description: 请求头的值
     */
    private String header;

    /**
     * @description: token 值
     */
    private String value;

    /**
     * @description: token 前缀
     */
    private String prefix;

    /**
     * @description: 过期时间（毫秒，这里默认20分钟）
     */
    private Long expiration;

}
