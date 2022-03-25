package com.xaaef.robin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xaaef.robin.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.Formatter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * <p>
 * spring mvc 配置
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 9:31
 */

@Slf4j
@Configuration
@AllArgsConstructor
public class SpringMvcConfiguration implements WebMvcConfigurer {

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return JsonUtils.getMapper();
    }

    /***
     * Date日期类型转换器
     */
    @Bean
    public Formatter<Date> dateFormatter() {
        return new Formatter<>() {

            @Override
            public Date parse(String text, Locale locale) {
                Date date = null;
                try {
                    date = DateUtils.parseDate(text, locale, JsonUtils.DEFAULT_DATE_TIME_PATTERN);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                return date;
            }

            @Override
            public String print(Date date, Locale locale) {
                return DateFormatUtils.format(date, JsonUtils.DEFAULT_DATE_TIME_PATTERN, TimeZone.getDefault(), locale);
            }
        };
    }

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<>() {
            @Override
            public LocalDate parse(String text, Locale locale) {
                return LocalDate.parse(text, DateTimeFormatter.ofPattern(JsonUtils.DEFAULT_DATE_PATTERN, locale));
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return DateTimeFormatter.ofPattern(JsonUtils.DEFAULT_DATE_PATTERN, locale).format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalTime> localTimeFormatter() {
        return new Formatter<>() {
            @Override
            public LocalTime parse(String text, Locale locale) {
                return LocalTime.parse(text, DateTimeFormatter.ofPattern(JsonUtils.DEFAULT_TIME_PATTERN, locale));
            }

            @Override
            public String print(LocalTime object, Locale locale) {
                return DateTimeFormatter.ofPattern(JsonUtils.DEFAULT_TIME_PATTERN, locale).format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<>() {

            @Override
            public String print(LocalDateTime localDateTime, Locale locale) {
                return DateTimeFormatter.ofPattern(JsonUtils.DEFAULT_DATE_TIME_PATTERN, locale).format(localDateTime);
            }

            @Override
            public LocalDateTime parse(String text, Locale locale) {
                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(JsonUtils.DEFAULT_DATE_TIME_PATTERN, locale));
            }
        };
    }

}
