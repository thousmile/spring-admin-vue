package com.xaaef.robin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.nustaq.serialization.FSTConfiguration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FSTUtils {

    private final static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();

    private final static ObjectMapper MAPPER = JsonUtils.getMapper();

    /**
     * 将对象转换成 字节数组。
     *
     * @param data
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static byte[] toBytes(Object data) {
        return conf.asByteArray(data);
    }


    /**
     * 将 字节数组 转化为对象
     *
     * @param src
     * @param beanType
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static <T> T toPojo(byte[] src, Class<T> beanType) {
        Object obj = conf.asObject(src);
        return MAPPER.convertValue(obj, beanType);
    }


    /**
     * 将 字节数组 转换成pojo对象list
     *
     * @param src
     * @param beanType
     * @return java.util.List<T>
     * @author Wang Chen Chen <932560435@qq.com>
     * @date 2019/4/18 15:26
     */
    public static <T> List<T> toListPojo(byte[] src, Class<T> beanType) {
        Object obj = conf.asObject(src);
        return MAPPER.convertValue(obj, MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, beanType));
    }

}
