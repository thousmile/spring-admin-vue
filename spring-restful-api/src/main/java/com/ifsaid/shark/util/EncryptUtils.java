package com.ifsaid.shark.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 加密解密工具类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Slf4j
public class EncryptUtils {

    /**
     * base64 加密
     *
     * @param source
     * @return java.lang.String
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/5/10 10:58
     */
    public static String base64Encoder(String source) {
        return Base64.getEncoder().encodeToString(source.getBytes());
    }


    /**
     * 生成一个模板的key
     *
     * @param parkId
     * @param templateName
     * @return java.lang.String
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/6/13 15:25
     */
    public static String createTemplateKey(String parkId, String templateName) {
        String format = String.format("%s#%s", parkId, templateName);
        log.debug("format: {}", format);
        return base64Encoder(format);
    }

    /**
     * base64 加密
     *
     * @return java.lang.String
     * @param: source
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/5/10 10:58
     */
    public static String base64Encoder(byte[] source) {
        return Base64.getEncoder().encodeToString(source);
    }

    /**
     * base64 解密
     *
     * @param source
     * @return java.lang.String
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/5/10 11:04
     */
    public static String base64Decoder(String source) {
        byte[] decode = Base64.getDecoder().decode(source);
        return new String(decode);
    }

}
