package com.xaaef.robin.service.impl;

import com.xaaef.robin.base.service.impl.BaseServiceImpl;
import com.xaaef.robin.entity.SysConfig;
import com.xaaef.robin.service.SysConfigService;
import com.xaaef.robin.mapper.SysConfigMapper;
import com.xaaef.robin.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.xaaef.robin.util.JsonUtils.*;

/**
 * @author WangChenChen
 * @description 针对表【sys_config([ 通用 ] 参数配置表)】的数据库操作Service实现
 * @createDate 2022-03-22 09:59:32
 */

@Slf4j
@Service
@AllArgsConstructor
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfigMapper, SysConfig>
        implements SysConfigService {

    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean updateById(SysConfig entity) {
        if (StringUtils.isNotBlank(entity.getConfigValue())) {
            String key = String.format("sys_config_cache:%s", entity.getConfigKey());
            redisTemplate.opsForValue().set(key, entity.getConfigValue(), 1, TimeUnit.HOURS);
        }
        return super.updateById(entity);
    }


    @Override
    public boolean removeById(Serializable id) {
        SysConfig byId = getById(id);
        if (byId == null) {
            throw new RuntimeException("配置不存在！");
        }
        String key = String.format("sys_config_cache:%s", byId.getConfigKey());
        redisTemplate.delete(key);
        return super.removeById(byId.getConfigId());
    }


    @Override
    public String findValueByKey(String configKey) {
        String key = String.format("sys_config_cache:%s", configKey);
        if (redisTemplate.hasKey(key)) {
            return redisTemplate.opsForValue().get(key);
        } else {
            SysConfig sysConfig = getOne(SysConfig::getConfigKey, configKey);
            if (sysConfig != null && StringUtils.isNotBlank(sysConfig.getConfigValue())) {
                redisTemplate.opsForValue().set(key, sysConfig.getConfigValue());
                return sysConfig.getConfigValue();
            }
        }
        return null;
    }


    @Override
    public List<String> findValueListByKey(String configKey) {
        String str = findValueByKey(configKey);
        return JsonUtils.toListPojo(str, String.class);
    }

    @Override
    public Map<String, Object> findValueMapByKey(String configKey) {
        String str = findValueByKey(configKey);
        return JsonUtils.toMap(str, String.class, Object.class);
    }

    @Override
    public Integer findValueIntByKey(String configKey) {
        String str = findValueByKey(configKey);
        return Integer.parseInt(str);
    }

    @Override
    public Double findValueDoubleByKey(String configKey) {
        String str = findValueByKey(configKey);
        return Double.parseDouble(str);
    }

    @Override
    public LocalDateTime findValueDateTimeByKey(String configKey) {
        String str = findValueByKey(configKey);
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_PATTERN));
    }

    @Override
    public LocalTime findValueTimeByKey(String configKey) {
        String str = findValueByKey(configKey);
        return LocalTime.parse(str, DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN));
    }

    @Override
    public LocalDate findValueDateByKey(String configKey) {
        String str = findValueByKey(configKey);
        return LocalDate.parse(str, DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN));
    }


}




