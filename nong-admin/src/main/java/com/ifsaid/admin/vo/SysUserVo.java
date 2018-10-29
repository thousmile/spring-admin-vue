package com.ifsaid.admin.vo;

import com.ifsaid.admin.entity.SysDept;
import com.ifsaid.admin.entity.SysRole;
import com.ifsaid.admin.entity.SysUser;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.management.relation.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;


@Data
public class SysUserVo {

    private String uid;

    @URL(message = "头像地址格式不正确")
    private String avatar;

    @NotBlank(message = "昵称必须要填写")
    private String nickname;

    @NotBlank(message = "密码必须要填写")
    @Length(min = 5, max = 20, message = "密码长度必须大于5位")
    private String password;

    @NotBlank(message = "用户名必须要填写")
    @Length(min = 5, max = 20, message = "用户名长度必须大于5位")
    private String username;

    private String phone;

    @Email(message = "邮箱地址必须要填写")
    private String mail;

    private Integer state;

    private Date addTime;

    private Date upTime;

    private Integer dept;

    private SysDept sysDept;

    private Set<SysRole> roles;

    /**
     * @describe 按钮
     * @date 2018/10/29
     * @author Wang Chen Chen
     */
    private Set<ButtonVo> buttons;

    /**
     * @describe 菜单
     * @date 2018/10/29
     * @author Wang Chen Chen
     */
    private Set<MenuVo> menus;

    public SysUser toSysUser() {
        SysUser sysUser = new SysUser();
        sysUser.setNickname(this.nickname);
        sysUser.setUsername(this.username);
        sysUser.setMail(this.mail);
        sysUser.setPassword(this.password);
        sysUser.setAvatar(this.avatar);
        sysUser.setDept(this.dept);
        sysUser.setUpTime(new Date());
        sysUser.setAddTime(new Date());
        sysUser.setState(1);
        return sysUser;
    }


    public SysUserVo(String uid, @URL(message = "头像地址格式不正确") String avatar,
                     @NotBlank(message = "昵称必须要填写") String nickname,
                     @NotBlank(message = "用户名必须要填写")
                     @Length(min = 5, max = 20, message = "用户名长度必须大于5位") String username,
                     @Email(message = "邮箱地址必须要填写") String mail,
                     Date addTime, Set<SysRole> roles,
                     Set<ButtonVo> buttons, Set<MenuVo> menus
    ) {
        this.uid = uid;
        this.avatar = avatar;
        this.nickname = nickname;
        this.username = username;
        this.mail = mail;
        this.addTime = addTime;
        this.roles = roles;
        this.buttons = buttons;
        this.menus = menus;
    }

}