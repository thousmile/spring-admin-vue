package com.ifsaid.admin;

import com.ifsaid.admin.entity.SysRole;
import com.ifsaid.admin.entity.SysUser;
import com.ifsaid.admin.service.ISysRoleService;
import com.ifsaid.admin.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class NongAdminApplicationTests {

    @Autowired
    public ISysUserService userService;

    @Autowired
    public ISysRoleService roleService;

    @Test
    public void contextLoads() {

        SysUser admin = userService.findByUsername("admin");

        log.info("admin:  {}", admin);

        Set<SysRole> sysRoles = roleService.selectByUserName(admin.getUsername());
        sysRoles.forEach(System.out::println);

    }

}
