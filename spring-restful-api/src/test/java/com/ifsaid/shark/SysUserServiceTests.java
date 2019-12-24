package com.ifsaid.shark;

import com.github.pagehelper.PageInfo;
import com.ifsaid.shark.constant.GenderConstant;
import com.ifsaid.shark.constant.StatusConstant;
import com.ifsaid.shark.entity.SysUser;
import com.ifsaid.shark.service.SysUserService;
import com.ifsaid.shark.util.JsonUtils;
import com.ifsaid.shark.util.QueryParameter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 测试 系统用户 Service 类
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/12 22:36
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@SpringBootTest
public class SysUserServiceTests {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private SysUserService baseService;

    @Test
    public void create() {
        SysUser sysUser1 = SysUser.builder()
                .avatar("https://lzy-file.oss-cn-shenzhen.aliyuncs.com/2018/11/18/19/37/f499ea6a683548a6bb6c986a38284c1d.jpeg")
                .birthday(LocalDate.now())
                .deptId(1)
                .nickname("测试00001")
                .email("test00001@qq.com")
                .gender(GenderConstant.FEMALE)
                .status(StatusConstant.NORMAL)
                .username("test00001")
                .password(encoder.encode("test00001"))
                .build();
        int create = baseService.create(sysUser1);

        log.info("create: {}", create);

        SysUser sysUser2 = SysUser.builder()
                .avatar("https://lzy-file.oss-cn-shenzhen.aliyuncs.com/2018/11/18/18/47/8e4227d68b74444bb5e77b460e1696ca.jpg")
                .birthday(LocalDate.now())
                .deptId(1)
                .nickname("测试00002")
                .email("test00002@qq.com")
                .gender(GenderConstant.MALE)
                .status(StatusConstant.NORMAL)
                .username("test00002")
                .password(encoder.encode("test00002"))
                .build();

        SysUser sysUser3 = SysUser.builder()
                .avatar("http://q17pj3u6q.bkt.clouddn.com/a2693e3320314060b096de0ab1e4486a.jpeg")
                .birthday(LocalDate.now())
                .deptId(1)
                .nickname("测试00003")
                .email("test00003@qq.com")
                .gender(GenderConstant.MALE)
                .status(StatusConstant.NORMAL)
                .username("test00003")
                .password(encoder.encode("test00003"))
                .build();
        int batchCreate = baseService.batchCreate(Arrays.asList(sysUser2, sysUser3));
        log.info("batchCreate: {}", batchCreate);
    }

    @Test
    public void update() {
        SysUser sysUser1 = SysUser.builder()
                .status(StatusConstant.DISABLE)
                .uid(5)
                .build();
        int update = baseService.update(sysUser1);
        log.info("update: {}", update);
    }

    @Test
    public void delete() {
        SysUser sysUser1 = SysUser.builder()
                .status(StatusConstant.DISABLE)
                .build();
        int delete = baseService.delete(sysUser1);
        int deleteById = baseService.deleteById(6);
        log.info("delete: {},  deleteById: {}", delete, deleteById);
    }

    @Test
    public void find() {
        SysUser sysUser1 = SysUser.builder()
                .status(StatusConstant.NORMAL)
                .username("root_admin")
                .build();
        SysUser sysUser = baseService.find(sysUser1);
        log.info("find(sysUser1): {}", sysUser);

        SysUser sysUser2 = baseService.findById(3);
        log.info("findById(1): {}", sysUser2);

        List<SysUser> all = baseService.findAll(null);
        log.info("indAll(null): {}", JsonUtils.objectToJson(all));
        all.forEach(res -> {
            log.info("findAll(sysUser2): {}", res);
        });

        SysUser sysUser3 = SysUser.builder()
                .status(StatusConstant.DISABLE)
                .build();
        List<SysUser> all2 = baseService.findAll(sysUser3);
        all2.forEach(res -> {
            log.info("findAll(sysUser2): {}", res);
        });

        PageInfo<SysUser> allPage = baseService.findAllPage(new QueryParameter());
        log.info("findAllPage: {}", JsonUtils.objectToJson(allPage));
        allPage.getList().forEach(res -> {
            log.info("findAll(sysUser2): {}", res);
        });


    }

    @Test
    public void updateRoles() {
        Set<Integer> roles = new HashSet<>();
        roles.add(1);
        roles.add(2);
        int userRoles = baseService.updateUserRoles(3, roles);
        log.info("userRoles: {}", userRoles);
    }


}
