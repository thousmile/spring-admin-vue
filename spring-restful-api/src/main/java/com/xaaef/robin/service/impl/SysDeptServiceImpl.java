package com.xaaef.robin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xaaef.robin.base.service.impl.BaseServiceImpl;
import com.xaaef.robin.entity.SysDept;
import com.xaaef.robin.entity.SysPermission;
import com.xaaef.robin.entity.SysUser;
import com.xaaef.robin.mapper.SysDeptMapper;
import com.xaaef.robin.mapper.SysUserMapper;
import com.xaaef.robin.service.SysDeptService;
import com.xaaef.robin.service.SysPermissionService;
import com.xaaef.robin.util.TreeNodeUtils;
import com.xaaef.robin.vo.UpdatePermsVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author WangChenChen
 * @description 针对表【sys_dept([ 权限 ] 部门表)】的数据库操作Service实现
 * @createDate 2022-03-22 09:59:32
 */

@Slf4j
@Service
@AllArgsConstructor
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept>
        implements SysDeptService {

    private SysPermissionService permissionService;

    private SysUserMapper userMapper;

    @Override
    public UpdatePermsVO listHavePerms(Long deptId) {
        var dbDept = getById(deptId);
        if (dbDept == null) {
            throw new RuntimeException(String.format("部门ID %s 不存在！", deptId));
        }
        // 当前部门，已经拥有的 权限ID
        var haveList = permissionService.listByDeptId(deptId);

        // 将当前角色拥有的权限 pid，挑选出来
        var haveHashSet = haveList.stream().map(SysPermission::getPermissionId).collect(Collectors.toSet());

        // 再次遍历，当前角色拥有的权限，然后 移除父节点 只留下子节点。
        haveList.forEach(res -> haveHashSet.remove(res.getParentId()));

        var all = TreeNodeUtils.findRoots(
                permissionService.list()
        );

        return UpdatePermsVO.builder().have(haveHashSet).all(all).build();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        Long deptId = Long.valueOf(id.toString());
        var wrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getDeptId, deptId);
        if (userMapper.selectCount(wrapper) > 0) {
            throw new RuntimeException("当前部门有 用户引用！无法删除！");
        }
        if (count(SysDept::getParentId, deptId) > 0) {
            throw new RuntimeException("当前部门有 子部门引用！无法删除！");
        }
        // 删除当前 部门 ，关联的权限
        baseMapper.deleteHaveMenus(deptId);
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePerms(Long id, Set<Long> items) {
        SysDept dbDept = getById(id);
        if (dbDept == null) {
            throw new RuntimeException("部门不存在！");
        }
        // 先删除权限，再新增权限
        baseMapper.deleteHaveMenus(id);
        return baseMapper.insertByPerms(id, items) > 0;
    }


}




