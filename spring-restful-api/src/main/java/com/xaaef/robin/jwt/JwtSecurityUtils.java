package com.xaaef.robin.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>
 * 安全服务工具类
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 10:50
 */

public class JwtSecurityUtils {

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    /**
     * 获取用户账户
     **/
    public static Long getUserId() {
        return getLoginUser().getUserId();
    }


    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        return getLoginUser().getUsername();
    }


    /**
     * 获取用户
     **/
    public static JwtLoginUser getLoginUser() {
        try {
            return (JwtLoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息异常");
        }
    }


    private final static PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static PasswordEncoder getPasswordEncoder() {
        return PASSWORD_ENCODER;
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        return PASSWORD_ENCODER.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
    }

}
