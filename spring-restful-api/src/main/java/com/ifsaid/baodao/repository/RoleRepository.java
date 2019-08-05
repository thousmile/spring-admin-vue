package com.ifsaid.baodao.repository;

import com.ifsaid.baodao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 13:54
 * @describe： 部门dao接口
 * @version: 1.0
 */

public interface RoleRepository extends JpaRepository<Role, Integer> {

    /**
     * @describe 删除某个角色的所有权限
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tb_role_permission WHERE t_role_id = ?1")
    Integer deleteRolePermissionAll(Integer rid);

    /**
     * @describe 删除某个用户的所有角色
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tb_user_role WHERE t_user_id = ?1")
    Integer deleteUserRoleAll(String uid);

    /**
     * @describe 根据角色ID赋值权限
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO tb_role_permission(t_role_id,t_permission_id) VALUES(?1 , ?2)")
    Integer addRolePermission(Integer rid, Integer pid);

    /**
     * @describe 根据用户ID赋值角色
     * @date 2018/11/16
     * @author Wang Chen Chen
     */
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO tb_user_role(t_user_id,t_role_id) VALUES(?1 , ?2)")
    Integer addUserRole(String uid, Integer rid);

    @Modifying
    @Query(nativeQuery = true, value = "SELECT r.t_rid,r.t_name,r.t_describe,r.t_create_time,r.t_up_time,r.t_state FROM tb_role AS r \n" +
            "LEFT JOIN tb_user_role AS ur ON r.t_rid = ur.t_role_id WHERE ur.t_user_id = ?1")
    Set<Role> findAllByUserId(String uid);

}
