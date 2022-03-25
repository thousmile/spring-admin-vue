package com.xaaef.robin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xaaef.robin.base.service.BaseService;
import com.xaaef.robin.entity.SysUser;
import com.xaaef.robin.param.UserQueryParams;
import com.xaaef.robin.vo.ResetPasswordVO;
import com.xaaef.robin.vo.SysUserVO;
import com.xaaef.robin.vo.UpdatePasswordVO;
import com.xaaef.robin.vo.UserIdAndNicknameVO;

import java.util.Set;

/**
* @author WangChenChen
* @description 针对表【sys_user([ 权限 ] 用户表)】的数据库操作Service
* @createDate 2022-03-22 09:59:32
*/

public interface SysUserService extends BaseService<SysUser> {

    /**
     * 根据关键字查询
     *
     * @param params
     * @author Wang Chen Chen
     * @date 2021/7/5 11:46
     */
    IPage<SysUser> pageKeywords(UserQueryParams params);


    /**
     * 获取 所有的 用户ID,昵称,头像
     *
     * @return
     * @author Wang Chen Chen
     * @date 2021/7/5 11:46
     */
    Set<UserIdAndNicknameVO> listAllSimpleUser();


    /**
     * 修改密码
     *
     * @param pwd
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:20
     */
    boolean updatePassword(UpdatePasswordVO pwd);


    /**
     * 重置密码
     *
     * @param pwd
     * @return int
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:20
     */
    boolean resetPassword(ResetPasswordVO pwd);


    /**
     * 获取用户详细信息
     *
     * @return SysUserVO
     */
    SysUserVO getUserInfo(Long userId);


    /**
     * 修改用户的角色
     *
     * @param userId
     * @param roleIds
     * @return int
     * @date 2019/12/12 21:20
     */
    boolean updateRoles(Long userId, Set<Long> roleIds);


}
