package com.ifsaid.shark.aspect;

import com.ifsaid.shark.util.JsonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * <p>
 * HibernateValidator 错误结果处理切面
 * </p>
 *
 * @author Wang Chen Chen<932560435@qq.com>
 * @version 1.0
 * @createTime 2020/3/7 0007 16:02
 */

@Aspect
@Component
@Order(2)
public class BindingResultAspect {

    @Pointcut("execution(public * com.ifsaid.shark.controller.*.*(..))")
    public void BindingResult() {
    }

    @Around("BindingResult()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    FieldError fieldError = result.getFieldError();
                    if (fieldError != null) {
                        return JsonResult.fail(fieldError.getDefaultMessage());
                    } else {
                        return JsonResult.fail("请求参数错误！");
                    }
                }
            }
        }
        return joinPoint.proceed();
    }

}
