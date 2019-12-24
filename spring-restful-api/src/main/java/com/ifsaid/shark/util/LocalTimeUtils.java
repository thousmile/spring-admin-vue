package com.ifsaid.shark.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * 时间工具类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


public class LocalTimeUtils {

    /**
     * @description: 获取当前时间戳(秒)
     * @author: Wang Chen Chen<932560435@qq.com>
     * @date: 2019/8/9 16:52
     */
    public static long getCurrentTimeSecond() {
        return LocalDateTime.now().toEpochSecond(OffsetDateTime.now().getOffset());
    }

    /**
     * @description: 获取当前时间戳(毫秒)
     * @author: Wang Chen Chen<932560435@qq.com>
     * @date: 2019/8/9 16:52
     */
    public static long getCurrentTimeMilli() {
        return LocalDateTime.now().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
    }

    /**
     * @description: 时间戳格式化
     * @author: Wang Chen Chen<932560435@qq.com>
     * @date: 2019/8/9 16:52
     */
    public static String timestampSecondFormat(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(Long.valueOf(timestamp + "000")));
    }

    /**
     * @description: 时间戳(毫秒) 格式化 yyyy-MM-dd HH:mm:ss
     * @author: Wang Chen Chen<932560435@qq.com>
     * @date: 2019/8/9 16:52
     */
    public static String timestampMilliFormat(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }


}
