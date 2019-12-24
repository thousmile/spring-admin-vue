package com.ifsaid.shark.util;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * Twitter 的分布式雪花算法 SnowFlake 每秒自增生成26个万个可排序的ID
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 2.0
 * @date 2019/12/19 23:14
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

public class SnowFlakeUtils {

    private final static SnowFlake SNOW_FLAKE = new SnowFlake(2, 3);

    public static long getId() {
        return SNOW_FLAKE.nextId();
    }

    static class SnowFlake {

        /**
         * 起始的时间戳
         */
        private final static long START_STMP = 1480166465631L;

        /**
         * 每一部分占用的位数
         */
        //序列号占用的位数
        private final static long SEQUENCE_BIT = 12;

        //机器标识占用的位数
        private final static long MACHINE_BIT = 5;

        //数据中心占用的位数
        private final static long DATACENTER_BIT = 5;

        /**
         * 每一部分的最大值
         */
        private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
        private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
        private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

        /**
         * 每一部分向左的位移
         */
        private final static long MACHINE_LEFT = SEQUENCE_BIT;
        private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
        private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

        //数据中心
        private long datacenterId;

        //机器标识
        private long machineId;

        //序列号
        private long sequence = 0L;

        //上一次时间戳
        private long lastStmp = -1L;

        public SnowFlake(long datacenterId, long machineId) {
            if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
                throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
            }
            if (machineId > MAX_MACHINE_NUM || machineId < 0) {
                throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
            }
            this.datacenterId = datacenterId;
            this.machineId = machineId;
        }

        /**
         * 产生下一个ID
         *
         * @return
         */
        public synchronized long nextId() {
            long currStmp = getNewstmp();
            if (currStmp < lastStmp) {
                throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
            }

            if (currStmp == lastStmp) {
                //相同毫秒内，序列号自增
                sequence = (sequence + 1) & MAX_SEQUENCE;
                //同一毫秒的序列数已经达到最大
                if (sequence == 0L) {
                    currStmp = getNextMill();
                }
            } else {
                //不同毫秒内，序列号置为0
                sequence = 0L;
            }

            lastStmp = currStmp;

            return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                    | datacenterId << DATACENTER_LEFT       //数据中心部分
                    | machineId << MACHINE_LEFT             //机器标识部分
                    | sequence;                             //序列号部分
        }

        private long getNextMill() {
            long mill = getNewstmp();
            while (mill <= lastStmp) {
                mill = getNewstmp();
            }
            return mill;
        }

        private long getNewstmp() {
            return System.currentTimeMillis();
        }

    }

}