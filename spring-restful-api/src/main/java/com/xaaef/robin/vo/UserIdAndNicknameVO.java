package com.xaaef.robin.vo;

import lombok.*;

/**
 * <p>
 * 用户简单信息 Vo 模型
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/12 23:29
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserIdAndNicknameVO implements java.io.Serializable {

    /**
     * 用户唯一id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String avatar;

}
