package com.ifsaid.baodao.vo;

import com.ifsaid.baodao.entity.Dept;
import com.ifsaid.baodao.entity.Role;
import com.ifsaid.baodao.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 15:49
 * @describe：
 * @version: 1.0
 */

@Data
public class UserVo {

    private String uid;

    private String avatar;

    private String nickname;

    private String account;

    private String mail;

    private Integer gender;

    private Date birthday;

    private Integer state;

    private Integer dept;

    private Dept department;

    private Set<Role> roles;

    private Date createTime;

    private Date upTime;

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

    public User toSysUser() {
        User user = new User();
        user.setAvatar(this.avatar);
        user.setAccount(this.account);
        user.setNickname(this.nickname);
        user.setMail(this.mail);
        return user;
    }

    public UserVo(String uid, String avatar, String nickname, String account, String mail) {
        this.uid = uid;
        this.avatar = avatar;
        this.nickname = nickname;
        this.account = account;
        this.mail = mail;
    }

    public UserVo(String uid, String avatar, String nickname, String account, String mail, Integer gender, Date birthday, Integer state, Dept department, Set<Role> roles, Date createTime, Date upTime) {
        this.uid = uid;
        this.avatar = avatar;
        this.nickname = nickname;
        this.account = account;
        this.mail = mail;
        this.gender = gender;
        this.birthday = birthday;
        this.state = state;
        this.department = department;
        this.roles = roles;
        this.createTime = createTime;
        this.upTime = upTime;
    }


}
