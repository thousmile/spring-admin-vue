package com.xaaef.robin.util;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.xaaef.robin.util.JsonUtils.*;

/**
 * <p>
 * 时间工具类
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/28 15:10
 */

@Data
public class TimeUtils {

    /**
     * 时间格式化
     */
    public static String format(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN));
    }

    /**
     * 时间格式化
     */
    public static String dateFormat(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN));
    }

    /**
     * 时间格式化
     */
    public static String dateTimeFormat(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN));
    }


    /**
     * 判断两个时间段是否重叠
     * <p>
     * 下面的例子。没有重叠
     * slot1 = 10:00:00 12:00:00
     * elapsedTimes = [{12:00:00 14:00:00}]
     * <p>
     * 下面的例子。重叠了
     * slot1 = 10:00:00 12:00:00
     * elapsedTimes = [{11:59:00 14:00:00},{14:30:00 16:00:00}]
     *
     * @param slot1
     * @param elapsedTimes
     * @return
     */
    public static boolean overlapped(TimeSlot slot1, List<TimeSlot> elapsedTimes) {
        for (var slot2 : elapsedTimes) {
            if (overlapped(slot1, slot2)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断两个时间段是否重叠
     * <p>
     * 下面的例子。重叠了
     * slot1 = 10:00:00 12:00:00
     * elapsedTimes = 11:59:00 14:00:00
     */
    public static boolean overlapped(TimeSlot slot1, TimeSlot slot2) {
        TimeSlot previous, next;
        previous = slot1.startTime.isBefore(slot2.startTime) ? slot1 : slot2;
        next = slot2.startTime.isAfter(slot1.startTime) ? slot2 : slot1;
        // 这里业务需要，允许时间点的重叠
        // 例如某个时间段的起始时间：2020-06-29 00:00:00
        // 和另一个时间段的终止时间：2020-06-29 00:00:00
        // 它们俩可以有交点。如果不需要这种逻辑只把le改成lt
        // ，ge改成gt就可
        return !(le(previous, next) || ge(previous, next));
    }


    /**
     * 构造一个时间段
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static TimeSlot buildSlot(LocalTime startTime, LocalTime endTime) {
        return new TimeSlot(startTime, endTime);
    }


    /**
     * less equal
     * 小于等于
     *
     * @param prev
     * @param next
     * @return
     */
    private static boolean le(TimeSlot prev, TimeSlot next) {
        return lt(prev, next) || next.endTime.equals(prev.startTime);
    }


    /**
     * greater equal
     * 大于等于
     *
     * @param prev
     * @param next
     * @return
     */
    private static boolean ge(TimeSlot prev, TimeSlot next) {
        return gt(prev, next) || prev.endTime.equals(next.startTime);
    }


    /**
     * greater than
     * 大于
     *
     * @param prev
     * @param next
     * @return
     */
    private static boolean gt(TimeSlot prev, TimeSlot next) {
        return prev.endTime.isBefore(next.startTime);
    }


    /**
     * less than
     * 小于
     *
     * @param prev
     * @param next
     * @return
     */
    private static boolean lt(TimeSlot prev, TimeSlot next) {
        return next.endTime.isBefore(prev.startTime);
    }


    /**
     * 时间段类
     */
    @Data
    public static class TimeSlot {

        private LocalTime startTime;

        private LocalTime endTime;

        public TimeSlot(LocalTime startTime, LocalTime endTime) {
            if (startTime.isAfter(endTime)) {
                this.startTime = endTime;
                this.endTime = startTime;
            } else {
                this.startTime = startTime;
                this.endTime = endTime;
            }
        }

    }


}

