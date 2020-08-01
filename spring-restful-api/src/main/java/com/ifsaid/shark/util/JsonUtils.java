package com.ifsaid.shark.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * jackson Json 工具类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */

@Slf4j
public class JsonUtils {

    private static final ObjectMapper MAPPER;

    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";


    static {
        MAPPER = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)));
        MAPPER.registerModule(javaTimeModule);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将对象转换成json字符串。
     *
     * @param data
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static String objectToJson(Object data) {
        try {
            String result = MAPPER.writeValueAsString(data);
            return result;
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     *
     * @param jsonData
     * @param beanType
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    /**
     * 将json结果集转化为对象
     *
     * @param src
     * @param beanType
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static <T> T jsonToPojo(byte[] src, Class<T> beanType) {
        try {
            return MAPPER.readValue(src, beanType);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     *
     * @param jsonData
     * @param beanType
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static <T> List<T> jsonToListPojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, beanType));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    /**
     * 将json数据转换成 Map
     *
     * @param jsonData
     * @param keyType
     * @param valueType
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static <K, V> Map<K, V> jsonToMap(String jsonData, Class<K> keyType, Class<V> valueType) {
        try {
            return MAPPER.readValue(jsonData, MAPPER.getTypeFactory().constructMapType(HashMap.class, keyType, valueType));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    /**
     * 将json数据转换成pojo对象list
     *
     * @param jsonData
     * @param keyType
     * @param valueType
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static <K, V> List<Map<K, V>> jsonToListMap(String jsonData, Class<K> keyType, Class<V> valueType) {
        try {
            return MAPPER.readValue(
                    jsonData,
                    MAPPER.getTypeFactory().constructCollectionType(List.class,
                            MAPPER.getTypeFactory().constructMapType(HashMap.class, keyType, valueType)
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


}
