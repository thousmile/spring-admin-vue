package com.xaaef.robin.mapper;

import com.xaaef.robin.entity.SysDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * @author WangChenChen
 * @description 针对表【sys_dept([ 权限 ] 部门表)】的数据库操作Mapper
 * @createDate 2022-03-22 09:59:32
 * @Entity com.xaaef.robin.entity.SysDept
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {


    // 删除 部门 的所有权限
    @Delete("DELETE FROM sys_dept_permission WHERE dept_id = #{id}")
    int deleteHaveMenus(Long id);


    // 部门 添加 权限
    int insertByPerms(@Param("id") Long id, @Param("items") Set<Long> items);


}




