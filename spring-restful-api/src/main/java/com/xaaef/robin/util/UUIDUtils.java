package com.xaaef.robin.util;

import java.util.UUID;

/**
 * <p>
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/8/12 15:43
 */

public class UUIDUtils {

    /**
     * 单次获取 uuid id
     *
     * @return
     */
    public static String getStrId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 单次获取 uuid id
     *
     * @return
     */
    public static String getSimpleStrId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


}
