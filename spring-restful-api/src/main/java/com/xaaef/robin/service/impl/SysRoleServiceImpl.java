package com.xaaef.robin.service.impl;

import com.xaaef.robin.base.service.impl.BaseServiceImpl;
import com.xaaef.robin.entity.SysPermission;
import com.xaaef.robin.entity.SysRole;
import com.xaaef.robin.entity.SysRoleProxy;
import com.xaaef.robin.service.SysPermissionService;
import com.xaaef.robin.service.SysRoleService;
import com.xaaef.robin.mapper.SysRoleMapper;
import com.xaaef.robin.util.TreeNodeUtils;
import com.xaaef.robin.vo.UpdatePermsVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author WangChenChen
 * @description 针对表【sys_role([ 权限 ] 角色表)】的数据库操作Service实现
 * @createDate 2022-03-22 09:59:32
 */


@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole>
        implements SysRoleService {

    private SysPermissionService permissionService;

    @Override
    public Set<SysRole> listByUserId(Long userId) {
        return baseMapper.listByUserId(userId);
    }

    @Override
    public Map<Long, Set<SysRole>> listByUserIds(Set<Long> userIds) {
        Map<Long, List<SysRoleProxy>> collect = baseMapper.listByUserIds(userIds).stream()
                .collect(Collectors.groupingBy(SysRoleProxy::getUserId));
        var longSetMap = new HashMap<Long, Set<SysRole>>();
        collect.forEach((key, values) -> {
            var roles = values.stream()
                    .map(r -> new SysRole(r.getRoleId(), r.getRoleName(), r.getDescription()))
                    .collect(Collectors.toSet());
            longSetMap.put(key, roles);
        });
        return longSetMap;
    }

    @Override
    public UpdatePermsVO listHavePerms(Long roleId) {
        SysRole dbRole = getById(roleId);
        if (dbRole == null) {
            throw new RuntimeException(String.format("角色ID %s 不存在！", roleId));
        }
        // 当前角色，已经拥有的菜单ID
        var haveList = permissionService.listByRoleId(roleId);

        // 将当前角色拥有的权限 pid，挑选出来
        var haveHashSet = haveList.stream().map(SysPermission::getPermissionId).collect(Collectors.toSet());

        // 再次遍历，当前角色拥有的权限，然后 移除父节点 只留下子节点。
        haveList.forEach(res -> haveHashSet.remove(res.getParentId()));

        var all = TreeNodeUtils.findRoots(
                permissionService.list()
        );

        return UpdatePermsVO.builder().have(haveHashSet).all(all).build();
    }


    @Override
    public boolean removeById(Serializable id) {
        Long roleId = Long.valueOf(id.toString());
        if (baseMapper.userReference(roleId) > 0) {
            throw new RuntimeException(String.format("角色Id [ %d ] 还有其他用户关联！无法删除！", roleId));
        }
        // 删除当前角色，关联的权限
        baseMapper.deleteHaveMenus(roleId);
        return super.removeById(id);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePerms(Long roleId, Set<Long> items) {
        var dbRole = getById(roleId);
        if (dbRole == null) {
            throw new RuntimeException("角色不存在！");
        }
        baseMapper.deleteHaveMenus(roleId);
        if (items != null && items.size() > 0) {
            return baseMapper.insertByMenus(roleId, items) > 0;
        }
        return true;
    }


}




