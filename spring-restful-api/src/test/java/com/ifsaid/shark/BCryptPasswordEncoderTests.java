package com.ifsaid.shark;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 用户密码加密
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/25 21:47
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public class BCryptPasswordEncoderTests {

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("xiaoxiannv"));
        System.out.println(encoder.encode("xiannva"));
    }

}
