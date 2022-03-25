package com.xaaef.robin.aspect.log;

import com.xaaef.robin.entity.OperLog;
import com.xaaef.robin.jwt.JwtSecurityUtils;
import com.xaaef.robin.util.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * <p>
 * 操作日志记录处理
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/8/10 15:01
 */


@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class OperateLogAspect {

    private final LogStorage logStorage;

    /**
     * 统一切面日志,参数校验、统一异常处理、日志打印
     */
    @Pointcut("@annotation(com.xaaef.robin.aspect.log.OperateLog)")
    public void logPointCut() {
    }


    /**
     * 环绕增强，相当于MethodInterceptor
     */
    @Around("logPointCut()")
    public Object doAfterReturning(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();
        Object resp = null;
        // 耗时，单位毫秒
        long timeCost = 0L;
        try {
            resp = joinPoint.proceed();
            timeCost = System.currentTimeMillis() - startTime;
        } catch (Throwable ew) {
            log.error(ew.getMessage());
            resp = JsonResult.fail(ew.getMessage());
            //方法执行完成后增加日志
            addOperationLog(joinPoint, resp, ew, timeCost);
        } finally {
            //方法执行完成后增加日志
            addOperationLog(joinPoint, resp, null, timeCost);
        }
        return resp;
    }


    private void addOperationLog(JoinPoint joinPoint, Object resp, Throwable e, long timeCost) {
        var signature = (MethodSignature) joinPoint.getSignature();
        var annotation = signature.getMethod().getAnnotation(OperateLog.class);
        var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        var operLog = new OperLog();
        operLog.setMessageId(IdUtils.getClusterStrId());

        // 全类名，方法名称
        var methodName = String.format("%s.%s()", signature.getDeclaringTypeName(), signature.getName());
        operLog.setStatus(HttpStatus.OK.value());
        if (e != null) {
            operLog.setErrorLog(e.getMessage());
            operLog.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        var ipAddr = IpUtils.getIpAddr(request);
        operLog.setTitle(annotation.title());
        operLog.setOperType(annotation.type().name());
        operLog.setMethod(methodName);
        if (joinPoint.getArgs() != null) {
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof BindingResult
                        || args[i] instanceof HttpServletRequest
                        || args[i] instanceof HttpServletResponse
                        || args[i] instanceof HttpHeaders
                ) {
                    args[i] = null;
                }
            }
            operLog.setMethodArgs(JsonUtils.toJson(args));
        }

        operLog.setRequestUrl(RequestUtils.getFullPath(request));

        operLog.setRequestMethod(request.getMethod());
        operLog.setRequestIp(ipAddr);
        operLog.setAddress(IpUtils.getRealAddressByIP(ipAddr));
        operLog.setResponseResult(JsonUtils.toJson(resp));
        operLog.setTimeCost(timeCost);

        if (JwtSecurityUtils.getAuthentication() != null && JwtSecurityUtils.getLoginUser() != null) {
            operLog.setUserId(JwtSecurityUtils.getUserId());
        }

        operLog.setCreateTime(LocalDateTime.now());

        // 保存 操作日志
        logStorage.asyncOperateSave(operLog);
    }


}
