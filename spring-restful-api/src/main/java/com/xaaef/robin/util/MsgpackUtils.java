package com.xaaef.robin.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * Msgpack 工具类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com Inc. All rights reserved.
 */

@Slf4j
public class MsgpackUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper(new MessagePackFactory())
            .registerModules(new Jdk8Module(), new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    /**
     * 将 对象 转换成 二进制
     *
     * @param data
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static byte[] toBytes(Object data) {
        try {
            return MAPPER.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 将 二进制 结果集转化为对象
     *
     * @param data
     * @param beanType
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static <T> T toPojo(byte[] data, Class<T> beanType) {
        try {
            return MAPPER.readValue(data, 0, data.length, beanType);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    /**
     * 将 二进制 数据转换成 pojo 对象 list
     *
     * @param data
     * @param beanType
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static <T> List<T> toListPojo(byte[] data, Class<T> beanType) {
        try {
            return MAPPER.readValue(data, MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, beanType));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    /**
     * 将 二进制 数据转换成 Map
     *
     * @param data
     * @param keyType
     * @param valueType
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static <K, V> Map<K, V> toMap(byte[] data, Class<K> keyType, Class<V> valueType) {
        try {
            return MAPPER.readValue(data, MAPPER.getTypeFactory().constructMapType(HashMap.class, keyType, valueType));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    /**
     * 将二进制数据转换成 pojo对象list
     *
     * @param data
     * @param keyType
     * @param valueType
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static <K, V> List<Map<K, V>> toListMap(byte[] data, Class<K> keyType, Class<V> valueType) {
        try {
            return MAPPER.readValue(
                    data,
                    MAPPER.getTypeFactory().constructCollectionType(ArrayList.class,
                            MAPPER.getTypeFactory().constructMapType(HashMap.class, keyType, valueType)
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


}
