package com.ifsaid.baodao.service;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/16 15:49
 * @describe： redis 工具类
 * @version: 1.0
 */


public interface IRedisService<K, V> {

    /**
     * 用户排序通过注册时间的 权重值
     *
     * @param date
     * @return
     */
    double getCreateTimeScore(long date);

    /**
     * 获取Redis中所有的键的key
     *
     * @return
     */
    Set<K> getAllKeys();

    /**
     * 获取所有的普通key-value
     *
     * @return
     */
    Map<K, V> getAllString();

    /**
     * 获取所有的Set -key-value
     *
     * @return
     */
    Map<K, Set<V>> getAllSet();

    /**
     * 获取所有的ZSet正序  -key-value 不获取权重值
     *
     * @return
     */
    Map<K, Set<V>> getAllZSetReverseRange();

    /**
     * 获取所有的ZSet倒序  -key-value 不获取权重值
     *
     * @return
     */
    Map<K, Set<V>> getAllZSetRange();

    /**
     * 获取所有的List -key-value
     *
     * @return
     */
    Map<K, List<V>> getAllList();

    /**
     * 获取所有的Map -key-value
     *
     * @return
     */
    Map<K, Map<K, V>> getAllMap();

    /**
     * 添加一个list
     *
     * @param key
     * @param objectList
     */
    void addList(K key, List<V> objectList);

    /**
     * 向list中增加值
     *
     * @param key
     * @param obj
     * @return 返回在list中的下标
     */
    long addList(K key, V obj);

    /**
     * 向list中增加值
     *
     * @param key
     * @param obj
     * @return 返回在list中的下标
     */
    long addList(K key, V... obj);

    /**
     * 输出list
     *
     * @param key List的key
     * @param s   开始下标
     * @param e   结束的下标
     * @return
     */
    List<V> getList(K key, long s, long e);

    /**
     * 输出完整的list
     *
     * @param key
     */
    List<V> getList(K key);

    /**
     * 获取list集合中元素的个数
     *
     * @param key
     * @return
     */
    long getListSize(K key);

    /**
     * 移除list中某值
     * 移除list中 count个value为object的值,并且返回移除的数量,
     * 如果count为0,或者大于list中为value为object数量的总和,
     * 那么移除所有value为object的值,并且返回移除数量
     *
     * @param key
     * @param object
     * @return 返回移除数量
     */
    long removeListValue(K key, V object);

    /**
     * 移除list中某值
     *
     * @param key
     * @param object
     * @return 返回移除数量
     */
    long removeListValue(K key, V... object);

    /**
     * 批量删除key对应的value
     *
     * @param keys
     */
    void remove(final K... keys);

    /**
     * 删除缓存
     * 根据key精确匹配删除
     *
     * @param key
     */
    void remove(final K key);

    /**
     * 通过分数删除ZSet中的值
     *
     * @param key
     * @param s
     * @param e
     */
    void removeZSetRangeByScore(String key, double s, double e);

    /**
     * 设置Set的过期时间
     *
     * @param key
     * @param time
     * @return
     */
    Boolean setSetExpireTime(String key, Long time);

    /**
     * 设置ZSet的过期时间
     *
     * @param key
     * @param time
     * @return
     */
    Boolean setZSetExpireTime(String key, Long time);

    /**
     * 判断缓存中是否有key对应的value
     *
     * @param key
     * @return
     */
    boolean exists(final K key);

    /**
     * 读取String缓存 可以是对象
     *
     * @param key
     * @return
     */
    V get(final K key);

    /**
     * 读取String缓存 可以是对象
     *
     * @param key
     * @return
     */
    List<V> get(final K... key);

    /**
     * 读取缓存 可以是对象 根据正则表达式匹配
     *
     * @param regKey
     * @return
     */
    List<Object> getByRegular(final K regKey);

    /**
     * 写入缓存 可以是对象
     *
     * @param key
     * @param value
     */
    void set(final K key, V value);

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @param expireTime 过期时间 -单位s
     * @return
     */
    void set(final K key, V value, Long expireTime);

    /**
     * 设置一个key的过期时间（单位：秒）
     *
     * @param key
     * @param expireTime
     * @return
     */
    boolean setExpireTime(K key, Long expireTime);

    /**
     * 获取key的类型
     *
     * @param key
     * @return
     */
    DataType getType(K key);

    /**
     * 删除map中的某个对象
     *
     * @param key   map对应的key
     * @param field map中该对象的key
     */
    void removeMapField(K key, V... field);

    /*
     * 获取map对象
     * @param key map对应的key
     * @return
     */
    Map<K, V> getMap(K key);

    /*
     * 获取map对象
     * @param key map对应的key
     * @return
     */
    Long getMapSize(K key);

    /**
     * 获取map缓存中的某个对象
     *
     * @param key   map对应的key
     * @param field map中该对象的key
     * @return
     */
    <T> T getMapField(K key, K field);

    /**
     * 判断map中对应key的key是否存在
     *
     * @param key map对应的key
     * @return
     */
    Boolean hasMapKey(K key, K field);

    /**
     * 获取map对应key的value
     *
     * @param key map对应的key
     * @return
     */
    List<V> getMapFieldValue(K key);

    /**
     * 获取map的key
     *
     * @param key map对应的key
     * @return
     */
    Set<V> getMapFieldKey(K key);

    /**
     * 添加map
     *
     * @param key
     * @param map
     */
    void addMap(K key, Map<K, V> map);

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param value 值
     */
    void addMap(K key, K field, Object value);

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param time  过期时间-整个MAP的过期时间
     * @param value 值
     */
    void addMap(K key, K field, V value, long time);

    /**
     * 向set中加入对象
     *
     * @param key 对象key
     * @param obj 值
     */
    void addSet(K key, V... obj);

    /**
     * 处理事务时锁定key
     *
     * @param key
     */
    void watch(String key);

    /**
     * 移除set中的某些值
     *
     * @param key 对象key
     * @param obj 值
     */
    long removeSetValue(K key, V obj);

    /**
     * 移除set中的某些值
     *
     * @param key 对象key
     * @param obj 值
     */
    long removeSetValue(K key, V... obj);

    /**
     * 获取set的对象数
     *
     * @param key 对象key
     */
    long getSetSize(K key);

    /**
     * 判断set中是否存在这个值
     *
     * @param key 对象key
     */
    Boolean hasSetValue(K key, V obj);

    /**
     * 获得整个set
     *
     * @param key 对象key
     */
    Set<V> getSet(K key);

    /**
     * 获得set 并集
     *
     * @param key
     * @param otherKey
     * @return
     */
    Set<V> getSetUnion(K key, K otherKey);

    /**
     * 获得set 并集
     *
     * @param key
     * @param set
     * @return
     */
    Set<V> getSetUnion(K key, Set<Object> set);

    /**
     * 获得set 交集
     *
     * @param key
     * @param otherKey
     * @return
     */
    Set<V> getSetIntersect(K key, K otherKey);

    /**
     * 获得set 交集
     *
     * @param key
     * @param set
     * @return
     */
    Set<V> getSetIntersect(K key, Set<Object> set);

    /**
     * 模糊移除 支持*号等匹配移除
     *
     * @param blears
     */
    void removeBlear(K... blears);

    /**
     * 修改key名 如果不存在该key或者没有修改成功返回false
     *
     * @param oldKey
     * @param newKey
     * @return
     */
    Boolean renameIfAbsent(String oldKey, String newKey);

    /**
     * 模糊移除 支持*号等匹配移除
     *
     * @param blear
     */
    void removeBlear(K blear);

    /**
     * 根据正则表达式来移除key-value
     *
     * @param blears
     */
    void removeByRegular(String... blears);

    /**
     * 根据正则表达式来移除key-value
     *
     * @param blears
     */
    void removeByRegular(String blears);

    /**
     * 根据正则表达式来移除 Map中的key-value
     *
     * @param key
     * @param blears
     */
    void removeMapFieldByRegular(K key, K... blears);

    /**
     * 根据正则表达式来移除 Map中的key-value
     *
     * @param key
     * @param blear
     */
    void removeMapFieldByRegular(K key, K blear);

    /**
     * 移除key 对应的value
     *
     * @param key
     * @param value
     * @return
     */
    Long removeZSetValue(K key, V... value);

    /**
     * 移除key ZSet
     *
     * @param key
     * @return
     */
    void removeZSet(K key);

    /**
     * 删除，键为K的集合，索引start<=index<=end的元素子集
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    void removeZSetRange(K key, Long start, Long end);

    /**
     * 并集 将key对应的集合和key1对应的集合合并到key2中
     * 如果分数相同的值，都会保留
     * 原来key2的值会被覆盖
     *
     * @param key
     * @param key1
     * @param key2
     */
    void setZSetUnionAndStore(String key, String key1, String key2);

    /**
     * 获取整个有序集合ZSET，正序
     *
     * @param key
     */
    <T> T getZSetRange(K key);

    /**
     * 获取有序集合ZSET
     * 键为K的集合，索引start<=index<=end的元素子集，正序
     *
     * @param key
     * @param start 开始位置
     * @param end   结束位置
     */
    <T> T getZSetRange(K key, long start, long end);

    /**
     * 获取整个有序集合ZSET，倒序
     *
     * @param key
     */
    Set<Object> getZSetReverseRange(K key);

    /**
     * 获取有序集合ZSET
     * 键为K的集合，索引start<=index<=end的元素子集，倒序
     *
     * @param key
     * @param start 开始位置
     * @param end   结束位置
     */
    Set<V> getZSetReverseRange(K key, long start, long end);

    /**
     * 通过分数(权值)获取ZSET集合 正序 -从小到大
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<V> getZSetRangeByScore(String key, double start, double end);

    /**
     * 通过分数(权值)获取ZSET集合 倒序 -从大到小
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<V> getZSetReverseRangeByScore(String key, double start, double end);

    /**
     * 键为K的集合，索引start<=index<=end的元素子集
     * 返回泛型接口（包括score和value），正序
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<ZSetOperations.TypedTuple<V>> getZSetRangeWithScores(K key, long start, long end);

    /**
     * 键为K的集合，索引start<=index<=end的元素子集
     * 返回泛型接口（包括score和value），倒序
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<ZSetOperations.TypedTuple<V>> getZSetReverseRangeWithScores(K key, long start, long end);

    /**
     * 键为K的集合
     * 返回泛型接口（包括score和value），正序
     *
     * @param key
     * @return
     */
    Set<ZSetOperations.TypedTuple<V>> getZSetRangeWithScores(K key);

    /**
     * 键为K的集合
     * 返回泛型接口（包括score和value），倒序
     *
     * @param key
     * @return
     */
    Set<ZSetOperations.TypedTuple<V>> getZSetReverseRangeWithScores(K key);

    /**
     * 键为K的集合，sMin<=score<=sMax的元素个数
     *
     * @param key
     * @param sMin
     * @param sMax
     * @return
     */
    long getZSetCountSize(K key, double sMin, double sMax);

    /**
     * 获取Zset 键为K的集合元素个数
     *
     * @param key
     * @return
     */
    long getZSetSize(K key);

    /**
     * 获取键为K的集合，value为obj的元素分数
     *
     * @param key
     * @param value
     * @return
     */
    double getZSetScore(K key, V value);

    /**
     * 元素分数增加，delta是增量
     *
     * @param key
     * @param value
     * @param delta
     * @return
     */
    double incrementZSetScore(K key, V value, double delta);

    /**
     * 添加有序集合ZSET
     * 默认按照score升序排列，存储格式K(1)==V(n)，V(1)=S(1)
     *
     * @param key
     * @param score
     * @param value
     * @return
     */
    Boolean addZSet(String key, double score, Object value);

    /**
     * 添加有序集合ZSET
     *
     * @param key
     * @param value
     * @return
     */
    Long addZSet(K key, TreeSet<V> value);

    /**
     * 添加有序集合ZSET
     *
     * @param key
     * @param score
     * @param value
     * @return
     */
    Boolean addZSet(K key, double[] score, Object[] value);


}
