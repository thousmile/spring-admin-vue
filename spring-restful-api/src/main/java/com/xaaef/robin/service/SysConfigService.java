package com.xaaef.robin.service;

import com.xaaef.robin.base.service.BaseService;
import com.xaaef.robin.entity.SysConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
* @author WangChenChen
* @description 针对表【sys_config([ 通用 ] 参数配置表)】的数据库操作Service
* @createDate 2022-03-22 09:59:32
*/

public interface SysConfigService extends BaseService<SysConfig> {


    /**
     * 根据 configKey 查看配置信息,，返回 String
     *
     * @param configKey
     * @return SysConfig
     * @author Wang Chen Chen
     * @date 2021/7/16 11:22
     */
    String findValueByKey(String configKey);


    /**
     * 根据 configKey 查看配置信息，返回 List
     *
     * @param configKey
     * @return SysConfig
     * @author Wang Chen Chen
     * @date 2021/7/16 11:22
     */
    List<String> findValueListByKey(String configKey);


    /**
     * 根据 configKey 查看配置信息，返回 Map
     *
     * @param configKey
     * @return SysConfig
     * @author Wang Chen Chen
     * @date 2021/7/16 11:22
     */
    Map<String, Object> findValueMapByKey(String configKey);


    /**
     * 根据 configKey 查看配置信息，返回 Integer
     *
     * @param configKey
     * @return SysConfig
     * @author Wang Chen Chen
     * @date 2021/7/16 11:22
     */
    Integer findValueIntByKey(String configKey);

    /**
     * 根据 configKey 查看配置信息，返回 Double
     *
     * @param configKey
     * @return SysConfig
     * @author Wang Chen Chen
     * @date 2021/7/16 11:22
     */
    Double findValueDoubleByKey(String configKey);


    /**
     * 根据 configKey 查看配置信息，返回 LocalDateTime
     *
     * @param configKey
     * @return SysConfig
     * @author Wang Chen Chen
     * @date 2021/7/16 11:22
     */
    LocalDateTime findValueDateTimeByKey(String configKey);

    /**
     * 根据 configKey 查看配置信息 ，返回 LocalTime
     *
     * @param configKey
     * @return SysConfig
     * @author Wang Chen Chen
     * @date 2021/7/16 11:22
     */
    LocalTime findValueTimeByKey(String configKey);

    /**
     * 根据 configKey 查看配置信息，返回 LocalDate
     *
     * @param configKey
     * @return SysConfig
     * @author Wang Chen Chen
     * @date 2021/7/16 11:22
     */
    LocalDate findValueDateByKey(String configKey);


}
