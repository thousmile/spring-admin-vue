package com.ifsaid.shark.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
     * 获取当前时间戳(秒)
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/8/9 16:52
     */
    public static long getCurrentTimeSecond() {
        return LocalDateTime.now().toEpochSecond(OffsetDateTime.now().getOffset());
    }

    /**
     * 获取当前时间戳(毫秒)
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/8/9 16:52
     */
    public static long getCurrentTimeMilli() {
        return LocalDateTime.now().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
    }

    /**
     * 时间戳 (秒) 格式化 yyyy-MM-dd HH:mm:ss
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/8/9 16:52
     */
    public static String timestampSecondFormat(long timestamp) {
        return LocalDateTime
                .ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 时间戳(毫秒) 格式化 yyyy-MM-dd HH:mm:ss.SSS
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/8/9 16:52
     */
    public static String timestampMilliFormat(long timestamp) {
        return LocalDateTime
                .ofInstant(
                        Instant.ofEpochMilli(timestamp),
                        ZoneId.systemDefault()
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }


    /**
     * 2019-12-19 23:20:50.292 转 LocalDateTime
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/8/9 16:52
     */
    public static LocalDateTime stringMilliToDateTime(String datetime) {
        return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * 2019-12-19 23:20:50 转 LocalDateTime
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/8/9 16:52
     */
    public static LocalDateTime stringSecondToDateTime(String datetime) {
        return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    public static void main(String[] args) {
        long second = getCurrentTimeSecond();
        long milli = getCurrentTimeMilli();
        String s1 = timestampSecondFormat(second);
        String s2 = timestampMilliFormat(milli);
        LocalDateTime t1 = stringSecondToDateTime(s1);
        LocalDateTime t2 = stringMilliToDateTime(s2);
        System.out.println(String.valueOf(second) + "  :  " + s1 + "  :  " + t1);
        System.out.println(String.valueOf(milli) + "  :  " + s2 + " : " + t2);

    }

}
