package com.xaaef.shark;

import com.xaaef.shark.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
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
public class SysRolesServiceTests {

    @Autowired
    private SysRoleService baseService;

    @Test
    public void updateRoles() {
        Set<Integer> permissionIds = new HashSet<>();
        permissionIds.addAll(Arrays.asList(
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                11,
                12,
                13,
                14,
                15,
                16,
                17,
                18,
                19,
                20,
                21,
                22,
                23
        ));
        int userRoles = baseService.updateRolePermissions(1, permissionIds);
        log.info("updateRolePermissions: {}", userRoles);
    }


}
