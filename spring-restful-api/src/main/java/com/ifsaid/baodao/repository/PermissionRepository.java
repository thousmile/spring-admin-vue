package com.ifsaid.baodao.repository;

import com.ifsaid.baodao.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/15 13:54
 * @describe： 文章dao接口
 * @version: 1.0
 */

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Query(nativeQuery = true, value = "SELECT p.t_pid,p.t_parent_id,p.t_resources,p.t_title,\n" +
            "p.t_icon,p.t_type,p.t_create_time,p.t_up_time,p.t_describe\n" +
            "FROM tb_user AS u\n" +
            "LEFT JOIN tb_user_role AS ur ON ur.t_user_id = u.t_uid\n" +
            "LEFT JOIN tb_role AS r ON r.t_rid = ur.t_role_id\n" +
            "LEFT JOIN tb_role_permission AS rp ON rp.t_role_id = r.t_rid\n" +
            "LEFT JOIN tb_permission AS p ON p.t_pid = rp.t_permission_id\n" +
            "WHERE u.t_uid = ?1")
    Set<Permission> findAllByUserId(String uid);

    @Query(nativeQuery = true, value = "SELECT t_permission_id FROM tb_role_permission WHERE t_role_id = ?1")
    Set<Integer> findPermissionByRoleId(Integer rid);

}
