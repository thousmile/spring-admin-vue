package com.xaaef.robin;

import com.xaaef.robin.entity.SysDept;
import com.xaaef.robin.entity.SysRole;
import com.xaaef.robin.entity.SysUser;
import com.xaaef.robin.enums.GenderEnum;
import com.xaaef.robin.util.FSTUtils;
import com.xaaef.robin.util.IdUtils;
import com.xaaef.robin.util.JsonUtils;
import com.xaaef.robin.util.MsgpackUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class NoSpringTests {

    /**
     * 5748337749776
     * <p>
     * 5748337749815
     */
    @Test
    void contextLoads() {
        for (int i = 0; i < 100; i++) {
            System.out.println(IdUtils.getStandaloneId());
        }
    }


    private static List<SysUser> randomUsers() {
        var users = new ArrayList<SysUser>();
        for (int i = 0; i < 1000; i++) {
            var user = SysUser.builder()
                    .userId(IdUtils.getStandaloneId())
                    .avatar(RandomStringUtils.randomAlphabetic(100))
                    .password(RandomStringUtils.randomAlphabetic(200))
                    .mobile(String.valueOf(RandomUtils.nextInt()))
                    .email(RandomStringUtils.randomAlphabetic(50))
                    .nickname(RandomStringUtils.random(10))
                    .gender(GenderEnum.create(RandomUtils.nextInt(0, 2)))
                    .deptId(RandomUtils.nextLong())
                    .dept(
                            SysDept.builder()
                                    .deptId(RandomUtils.nextLong())
                                    .parentId(RandomUtils.nextLong())
                                    .deptName(RandomStringUtils.randomAlphabetic(30))
                                    .leader(RandomStringUtils.randomAlphabetic(20))
                                    .leaderMobile(RandomStringUtils.randomAlphabetic(11))
                                    .description(RandomStringUtils.randomAlphabetic(50))
                                    .sort(RandomUtils.nextInt())
                                    .build()
                    )
                    .roles(
                            List.of(
                                    SysRole.builder()
                                            .roleId(RandomUtils.nextLong())
                                            .roleName(RandomStringUtils.randomAlphabetic(20))
                                            .description(RandomStringUtils.randomAlphabetic(50))
                                            .build(),
                                    SysRole.builder()
                                            .roleId(RandomUtils.nextLong())
                                            .roleName(RandomStringUtils.randomAlphabetic(20))
                                            .description(RandomStringUtils.randomAlphabetic(50))
                                            .build(),
                                    SysRole.builder()
                                            .roleId(RandomUtils.nextLong())
                                            .roleName(RandomStringUtils.randomAlphabetic(20))
                                            .description(RandomStringUtils.randomAlphabetic(50))
                                            .build()
                            )
                    )
                    .build();

            user.setCreateTime(LocalDateTime.now());
            user.setCreateUser(RandomUtils.nextLong());
            user.setLastUpdateTime(LocalDateTime.now());
            user.setLastUpdateUser(RandomUtils.nextLong());
            users.add(user);
        }
        return users;
    }

    @Test
    void test1() throws IOException {
        var users = randomUsers();

/*
        long start1 = System.currentTimeMillis();
        byte[] jsonBytes = JsonUtils.toBytes(users);
        long end1 = System.currentTimeMillis() - start1;
        System.out.printf("对象 转 json: 耗时: %d ms  字节数组大小: %d \r\n", end1, jsonBytes.length);
        // 对象 转 json: 耗时: 377 ms  字节数组大小: 1737877
        FileCopyUtils.copy(jsonBytes,new File("jsonBytes.json"));
*/

        long start2 = System.currentTimeMillis();
        byte[] msgBytes = MsgpackUtils.toBytes(users);
        long end2 = System.currentTimeMillis() - start2;
        System.out.printf("对象 Msgpack: 耗时: %d ms  字节数组大小: %d \r\n", end2, msgBytes.length);
        // 对象 Msgpack: 耗时: 379 ms  字节数组大小: 1126293
        FileCopyUtils.copy(msgBytes, new File("msgBytes.msg"));
    }


    @Test
    void test2() throws IOException {
        FileInputStream stream = new FileInputStream("jsonBytes.json");
        byte[] bytes = stream.readAllBytes();

        long start2 = System.currentTimeMillis();
        List<SysUser> sysUsers = JsonUtils.toListPojo(bytes, SysUser.class);
        long end2 = System.currentTimeMillis() - start2;

        System.out.printf("Json 转对象: 耗时: %d ms \r\n", end2);
        // Json 转对象: 耗时: 511 ms
    }


    @Test
    void test3() throws IOException {
        FileInputStream stream = new FileInputStream("msgBytes.msg");
        byte[] bytes = stream.readAllBytes();
        long start1 = System.currentTimeMillis();
        List<SysUser> sysUsers = MsgpackUtils.toListPojo(bytes, SysUser.class);
        long end1 = System.currentTimeMillis() - start1;

        System.out.printf("Msgpack 转对象: 耗时: %d ms \r\n", end1);
        // Msgpack 转对象: 耗时: 419 ms
    }


    @Test
    void test4() throws IOException {
        var users = randomUsers();

        long start2 = System.currentTimeMillis();
        byte[] fstBytes = FSTUtils.toBytes(users);
        long end2 = System.currentTimeMillis() - start2;
        System.out.printf("对象 FST: 耗时: %d ms  字节数组大小: %d \r\n", end2, fstBytes.length);
        // 对象 FST: 耗时: 236 ms  字节数组大小: 933589
        FileCopyUtils.copy(fstBytes, new File("fstBytes.fst"));
    }


    @Test
    void test5() throws IOException {
        FileInputStream stream = new FileInputStream("fstBytes.fst");
        byte[] bytes = stream.readAllBytes();
        long start1 = System.currentTimeMillis();
        List<SysUser> sysUsers = FSTUtils.toListPojo(bytes, SysUser.class);
        long end1 = System.currentTimeMillis() - start1;

        System.out.printf("FST 转对象: 耗时: %d ms \r\n", end1);
        // FST 转对象: 耗时: 219 ms
    }

}
