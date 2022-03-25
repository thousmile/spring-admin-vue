package com.xaaef.robin.vo;

import com.xaaef.robin.entity.SysUser;
import lombok.*;

import java.util.List;
import java.util.Set;


/**
 * <p>
 * 登录成功后，用户详细 Vo 模型
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
public class SysUserVO extends SysUser implements java.io.Serializable {

    /**
     * 按钮
     */
    private List<ButtonVO> buttons;

    /**
     * 菜单
     */
    private List<MenuVO> menus;


}
