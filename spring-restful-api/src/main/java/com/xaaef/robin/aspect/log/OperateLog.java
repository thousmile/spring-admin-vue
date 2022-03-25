package com.xaaef.robin.aspect.log;

import java.lang.annotation.*;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/8/10 15:00
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperateLog {

    String title() default "";

    LogType type() default LogType.SELECT;

}
