package com.xaaef.robin.vo;

import com.xaaef.robin.entity.SysPermission;
import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0.1
 * @date 2021/7/24 14:39
 */

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePermsVO implements java.io.Serializable {

    /**
     * 已经拥有的菜单 ID
     */
    Set<Long> have;

    /**
     * 全部 菜单 ID 和 名称
     */
    List<SysPermission> all;

}


