package com.xaaef.robin.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 深圳市铭灏天智能照明设备有限公司
 * <p>
 * redis 缓存工具类
 * </p>
 *
 * @author WangChenChen
 * @version 1.2
 * @date 2022/5/14 17:16
 * @copyright 2022 http://www.mhtled.com Inc. All rights reserved.
 */


@Slf4j
@Component
@AllArgsConstructor
public class RedisCacheUtils {

    private final StringRedisTemplate template;


    /**
     * 获取 字符串
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public String getString(String key) {
        if (!StringUtils.hasText(key)) {
            return null;
        }
        return template.opsForValue().get(key);
    }


    /**
     * 获取 多字符串
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public Set<String> keys(String keys) {
        if (!StringUtils.hasText(keys)) {
            return null;
        }
        return template.keys(keys);
    }


    /**
     * 获取 数字
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public <T extends Number> T getNumber(String key, Class<T> numberType) {
        if (!StringUtils.hasText(key)) {
            return null;
        }
        String str = template.opsForValue().get(key);
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            return null;
        }
        return NumberUtils.parseNumber(str, numberType);
    }


    /**
     * 设置 字符串
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public void setString(String key, String str) {
        if (!StringUtils.hasText(key) || !StringUtils.hasText(str)) {
            return;
        }
        template.opsForValue().set(key, str);
    }


    /**
     * 设置 字符串
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public void setString(String key, String str, Duration expired) {
        if (!StringUtils.hasText(key) || !StringUtils.hasText(str)) {
            return;
        }
        template.opsForValue().set(key, str, expired);
    }


    /**
     * 获取 对象
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public <T> T getObject(String key, Class<T> beanType) {
        if (!StringUtils.hasText(key)) {
            return null;
        }
        return template.execute((RedisCallback<T>) c -> {
            var commands = c.stringCommands();
            byte[] bytes = commands.get(key.getBytes(StandardCharsets.UTF_8));
            return toPojo(bytes, beanType);
        });
    }


    /**
     * 设置 对象
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public Boolean setObject(String key, Object obj, Duration expired) {
        if (!StringUtils.hasText(key)) {
            return false;
        }
        if (Objects.isNull(obj)) {
            return null;
        }
        return template.execute((RedisCallback<Boolean>) c -> {
            var commands = c.stringCommands();
            byte[] bytes = toBytes(obj);
            Boolean a = commands.set(key.getBytes(StandardCharsets.UTF_8), bytes);
            Boolean b = setExpirationTime(key, expired);
            return a && b;
        });
    }


    /**
     * 设置 对象
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public Boolean setObject(String key, Object obj) {
        return setObject(key, obj, null);
    }


    /**
     * 获取 列表 对象
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public <T> List<T> getListObject(String key, Class<T> beanType) {
        if (!StringUtils.hasText(key)) {
            return null;
        }
        return template.execute((RedisCallback<List<T>>) c -> {
            RedisListCommands commands = c.listCommands();
            Long len = commands.lLen(key.getBytes(StandardCharsets.UTF_8));
            if (len == null || len == 0) {
                return null;
            }
            return Objects.requireNonNull(
                            commands.lRange(key.getBytes(StandardCharsets.UTF_8), 0, len))
                    .stream().map(data -> toPojo(data, beanType))
                    .collect(Collectors.toList());
        });
    }


    /**
     * 设置 列表 对象
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public <T> Boolean setListObject(String key, List<T> list) {
        return setListObject(key, list, null);
    }


    /**
     * 设置 列表 对象
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public <T> Boolean setListObject(String key, List<T> list, Duration expired) {
        if (!StringUtils.hasText(key) || list == null || list.isEmpty()) {
            return false;
        }
        return template.execute((RedisCallback<Boolean>) c -> {
            RedisListCommands commands = c.listCommands();
            list.forEach(obj -> {
                byte[] bytes = toBytes(obj);
                commands.lPush(key.getBytes(StandardCharsets.UTF_8), bytes);
            });
            return setExpirationTime(key, expired);
        });
    }


    /**
     * 获取 Hash 对象
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public <T> Map<String, T> getHashObject(String key, Class<T> beanType) {
        return template.execute((RedisCallback<Map<String, T>>) c -> {
            RedisHashCommands commands = c.hashCommands();
            Map<byte[], byte[]> map = commands.hGetAll(key.getBytes(StandardCharsets.UTF_8));
            assert map != null;
            var result = new LinkedHashMap<String, T>();
            map.forEach((hashKey, value) -> {
                result.put(Arrays.toString(hashKey), toPojo(value, beanType));
            });
            return result;
        });
    }


    /**
     * 获取 Hash 对象
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public <T> T getHashObject(String key, String hashKey, Class<T> beanType) {
        return template.execute((RedisCallback<T>) c -> {
            RedisHashCommands commands = c.hashCommands();
            byte[] bytes = commands.hGet(key.getBytes(StandardCharsets.UTF_8), hashKey.getBytes(StandardCharsets.UTF_8));
            return toPojo(bytes, beanType);
        });
    }


    /**
     * 获取 Hash Values
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public <T> List<T> getHashValues(String key, Class<T> beanType) {
        return template.execute((RedisCallback<List<T>>) c -> {
            RedisHashCommands commands = c.hashCommands();
            var values = commands.hVals(key.getBytes(StandardCharsets.UTF_8));
            assert values != null;
            return values.stream().map(value -> toPojo(value, beanType)).collect(Collectors.toList());
        });
    }


    /**
     * 设置 Hash 对象
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public Boolean setHashObject(String key, String hashKey, Object obj) {
        return setHashObject(key, hashKey, obj, null);
    }


    /**
     * 设置 Hash 对象
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public Boolean setHashObject(String key, String hashKey, Object obj, Duration expired) {
        if (!StringUtils.hasText(key) || !StringUtils.hasText(hashKey) || obj == null) {
            return false;
        }
        return template.execute((RedisCallback<Boolean>) c -> {
            RedisHashCommands commands = c.hashCommands();
            byte[] bytes = toBytes(obj);
            var r = commands.hSet(key.getBytes(StandardCharsets.UTF_8), hashKey.getBytes(StandardCharsets.UTF_8), bytes);
            return r && setExpirationTime(key, expired);
        });
    }


    /**
     * 设置 Hash 对象
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public Boolean setHashObject(String key, Map<String, Object> data) {
        return setHashObject(key, data, null);
    }


    public Boolean setHashObject(String key, Map<String, Object> data, Duration expired) {
        if (!StringUtils.hasText(key) || data == null || data.size() == 0) {
            return false;
        }
        return template.execute((RedisCallback<Boolean>) c -> {
            RedisHashCommands commands = c.hashCommands();
            data.forEach((hashKey, value) -> {
                byte[] bytes = toBytes(value);
                commands.hSet(key.getBytes(StandardCharsets.UTF_8), hashKey.getBytes(StandardCharsets.UTF_8), bytes);
            });
            return setExpirationTime(key, expired);
        });
    }

    /**
     * 删除  Hash key
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public Long deleteHashKey(String key, String hashKey) {
        return template.opsForHash().delete(key, hashKey);
    }

    /**
     * 判断 Hash key
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public Boolean hasHashKey(String key, String hashKey) {
        return template.opsForHash().hasKey(key, hashKey);
    }


    /**
     * 删除 key
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public Boolean hasKey(String key) {
        return template.hasKey(key);
    }


    /**
     * 删除 key
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public Boolean deleteKey(String key) {
        return template.delete(key);
    }


    /**
     * 删除 keys
     *
     * @author WangChenChen
     * @date 2022/5/14 17:51
     */
    public Long deleteKeys(List<String> keys) {
        return template.delete(keys);
    }


    /**
     * 设置过期时间
     *
     * @param key
     * @param expired
     * @return boolean
     * @author WangChenChen
     * @date 2022/5/14 18:19
     */
    public Boolean setExpirationTime(String key, Duration expired) {
        if (expired == null) {
            return true;
        }
        return template.expire(key, expired);
    }


    /**
     * 二进制 转 对象
     *
     * @param data     二进制
     * @param beanType 对象类型
     * @return T
     * @author WangChenChen
     * @date 2022/5/14 18:19
     */
    private static <T> T toPojo(byte[] data, Class<T> beanType) {
        // return JsonUtils.toPojo(data, beanType);
        // return FSTUtils.toPojo(data, beanType);
        return MsgpackUtils.toPojo(data, beanType);
    }


    /**
     * 对象转 二进制
     *
     * @param data 对象
     * @return byte[]
     * @author WangChenChen
     * @date 2022/5/14 18:19
     */
    private static byte[] toBytes(Object data) {
        // return JsonUtils.toBytes(data);
        // return FSTUtils.toBytes(data);
        return MsgpackUtils.toBytes(data);
    }

}
