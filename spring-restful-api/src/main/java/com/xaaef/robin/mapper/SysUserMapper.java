package com.xaaef.robin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xaaef.robin.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xaaef.robin.param.UserQueryParams;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author WangChenChen
 * @description 针对表【sys_user([ 权限 ] 用户表)】的数据库操作Mapper
 * @createDate 2022-03-22 09:59:32
 * @Entity com.xaaef.robin.entity.SysUser
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户 keywords 获取用户信息
     *
     * @param params
     * @author Wang Chen Chen
     * @date 2021/7/5 11:46
     */
    IPage<SysUser> selectKeywordsList(Page<?> page, @Param("params") UserQueryParams params);


    /**
     * 删除某个用户，拥有的角色
     *
     * @param userId
     * @return Long
     * @date 2019/12/14 0:25
     */
    @Delete("delete from sys_user_role where user_id = #{userId}")
    int deleteHaveRoles(Long userId);


    /**
     * 用户关联多个角色
     *
     * @param userId
     * @param Set<Integer>
     * @date 2019/12/14 0:25
     */
    int insertByRoles(@Param("userId") Long userId, @Param("roles") Set<Long> roles);

}




