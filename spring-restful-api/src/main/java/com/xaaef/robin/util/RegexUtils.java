package com.xaaef.robin.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 正则表达式工具类
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/8/25 14:52
 */

@Slf4j
public class RegexUtils {

    /**
     * 判断是否是正确的IP地址
     *
     * @param ip
     * @return boolean true,通过，false，没通过
     */
    public static boolean isIp(String ip) {
        if (StringUtils.isBlank(ip))
            return false;
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ip.matches(regex);
    }

    /**
     * 判断是否是正确的邮箱地址
     *
     * @param email
     * @return boolean true,通过，false，没通过
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isBlank(email))
            return false;
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
    }

    /**
     * 判断是否含有中文，仅适合中国汉字，不包括标点
     *
     * @param text
     * @return boolean true,通过，false，没通过
     */
    public static boolean isChinese(String text) {
        if (StringUtils.isBlank(text))
            return false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(text);
        return m.find();
    }

    /**
     * 判断是否正整数
     *
     * @param number 数字
     * @return boolean true,通过，false，没通过
     */
    public static boolean isNumber(String number) {
        if (StringUtils.isBlank(number))
            return false;
        String regex = "[0-9]*";
        return number.matches(regex);
    }

    /**
     * 判断几位小数(正数)
     *
     * @param decimal 数字
     * @param count   小数位数
     * @return boolean true,通过，false，没通过
     */

    public static boolean isDecimal(String decimal, int count) {
        if (StringUtils.isBlank(decimal))
            return false;
        String regex = "^(-)?(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){" + count + "})?$";
        return decimal.matches(regex);
    }


    /**
     * 判断是否是移动手机号码
     *
     * @param phoneNumber 移动手机号码
     * @return boolean true,通过，false，没通过
     */
    public static boolean isMobilePhoneNumber(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber))
            return false;
        String regex = "^((13[0-9])|(15[0-9])|(18[1-9]))\\d{8}$";
        return phoneNumber.matches(regex);
    }


    /**
     * 判断是否是手机号码
     *
     * @param phoneNumber 移动手机号码
     * @return boolean true,通过，false，没通过
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber))
            return false;
        String regex = "^1\\d{10}$";
        return phoneNumber.matches(regex);
    }


    /**
     * 判断是否含有特殊字符
     *
     * @param text
     * @return boolean true,通过，false，没通过
     */
    public static boolean hasSpecialChar(String text) {
        if (StringUtils.isBlank(text))
            return false;
        if (text.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() == 0) {
            // 如果不包含特殊字符
            return true;
        }
        return false;
    }


    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }


    private static final String IMAGE_REGEX = ".+(.JPEG|.jpeg|.JPG|.jpg|.png|.PNG|.bmp|.BMP)$";

    /**
     * 判断是否是 图片
     *
     * @param fileName 图片名称
     * @return boolean true,通过，false，没通过
     */
    public static boolean isImage(String fileName) {
        if (StringUtils.isBlank(fileName))
            return false;
        return fileName.matches(IMAGE_REGEX);
    }

}
