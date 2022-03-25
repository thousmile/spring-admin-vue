package com.xaaef.robin.util;

/**
 * <p>
 * Twitter 雪花ID 算法
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/8/12 15:43
 */

public class IdUtils {

    private static final Cluster CLUSTER = new Cluster(1, 1);

    /**
     * [集群版] 雪花ID
     */
    public static long getClusterId() {
        return CLUSTER.nextId();
    }

    /**
     * [集群版] 雪花ID
     */
    public static String getClusterStrId() {
        return String.valueOf(CLUSTER.nextId());
    }


    /**
     * <p>
     * 雪花ID 集群版
     * </p>
     */
    private static class Cluster {

        // ==============================Fields===========================================
        /**
         * 开始时间截 (2020-07-15)
         */
        private final long twepoch = 1594800420694L;
        /**
         * 机器id所占的位数
         */
        private final long workerIdBits = 5L;

        /**
         * 数据标识id所占的位数
         */
        private final long datacenterIdBits = 5L;

        /**
         * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
         */
        private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

        /**
         * 支持的最大数据标识id，结果是31
         */
        private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

        /**
         * 序列在id中占的位数
         */
        private final long sequenceBits = 12L;

        /**
         * 机器ID向左移12位
         */
        private final long workerIdShift = sequenceBits;

        /**
         * 数据标识id向左移17位(12+5)
         */
        private final long datacenterIdShift = sequenceBits + workerIdBits;

        /**
         * 时间截向左移22位(5+5+12)
         */
        private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

        /**
         * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
         */
        private final long sequenceMask = -1L ^ (-1L << sequenceBits);

        /**
         * 工作机器ID(0~31)
         */
        private long workerId;

        /**
         * 数据中心ID(0~31)
         */
        private long datacenterId;

        /**
         * 毫秒内序列(0~4095)
         */
        private long sequence = 0L;

        /**
         * 上次生成ID的时间截
         */
        private long lastTimestamp = -1L;

        //==============================Constructors=====================================

        /**
         * 构造函数
         *
         * @param workerId     工作ID (0~31)
         * @param datacenterId 数据中心ID (0~31)
         */
        public Cluster(long workerId, long datacenterId) {
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
            }
            if (datacenterId > maxDatacenterId || datacenterId < 0) {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
            }
            this.workerId = workerId;
            this.datacenterId = datacenterId;
        }

        /**
         * 获得下一个ID (该方法是线程安全的)
         *
         * @return SnowflakeId
         */
        public synchronized long nextId() {
            long timestamp = timeGen();

            //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
            if (timestamp < lastTimestamp) {
                throw new RuntimeException(
                        String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
            }

            //如果是同一时间生成的，则进行毫秒内序列
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask;
                //毫秒内序列溢出
                if (sequence == 0) {
                    //阻塞到下一个毫秒,获得新的时间戳
                    timestamp = tilNextMillis(lastTimestamp);
                }
            }
            //时间戳改变，毫秒内序列重置
            else {
                sequence = 0L;
            }

            //上次生成ID的时间截
            lastTimestamp = timestamp;

            //移位并通过或运算拼到一起组成64位的ID
            return ((timestamp - twepoch) << timestampLeftShift) //
                    | (datacenterId << datacenterIdShift) //
                    | (workerId << workerIdShift) //
                    | sequence;
        }

        /**
         * 阻塞到下一个毫秒，直到获得新的时间戳
         *
         * @param lastTimestamp 上次生成ID的时间截
         * @return 当前时间戳
         */
        protected long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        /**
         * 返回以毫秒为单位的当前时间
         *
         * @return 当前时间(毫秒)
         */
        protected long timeGen() {
            return System.currentTimeMillis();
        }

    }


    /**
     * [单机版] 雪花ID
     */
    public static long getStandaloneId() {
        return Standalone.nextId();
    }


    /**
     * [单机版] 雪花ID
     */
    public static String getStandaloneStrId() {
        return String.valueOf(Standalone.nextId());
    }


    /**
     * <p>
     * 雪花ID 单机版
     * </p>
     */
    private static class Standalone {

        //12位的序列号
        private static long sequence;

        //初始时间戳
        private static long twepoch = 1288834974657L;

        //序列号id长度
        private static long sequenceBits = 4L;
        //序列号最大值
        private static long sequenceMask = -1L ^ (-1L << sequenceBits);

        //时间戳需要左移位数 4位
        private static long timestampLeftShift = sequenceBits;

        //上次时间戳，初始值为负数
        private static long lastTimestamp = -1L;

        //下一个ID生成算法
        public static synchronized long nextId() {
            long timestamp = timeGen();

            //获取当前时间戳如果小于上次时间戳，则表示时间戳获取出现异常
            if (timestamp < lastTimestamp) {
                System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                        lastTimestamp - timestamp));
            }

            //获取当前时间戳如果等于上次时间戳（同一毫秒内），则在序列号加一；否则序列号赋值为0，从0开始。
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask;
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0;
            }

            //将上次时间戳值刷新
            lastTimestamp = timestamp;

            /**
             * 返回结果：
             * (timestamp - twepoch) << timestampLeftShift) 表示将时间戳减去初始时间戳，再左移相应位数
             * (datacenterId << datacenterIdShift) 表示将数据id左移相应位数
             * (workerId << workerIdShift) 表示将工作id左移相应位数
             * | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
             * 因为个部分只有相应位上的值有意义，其它位上都是0，所以将各部分的值进行 | 运算就能得到最终拼接好的id
             */
            return ((timestamp - twepoch) << timestampLeftShift) | sequence;
        }

        //获取时间戳，并与上次时间戳比较
        private static long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        //获取系统时间戳
        private static long timeGen() {
            return System.currentTimeMillis();
        }

    }

}
