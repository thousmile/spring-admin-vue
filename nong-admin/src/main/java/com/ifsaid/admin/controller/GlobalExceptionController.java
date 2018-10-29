package com.ifsaid.admin.controller;

import com.ifsaid.admin.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/29 10:48
 * @describe：
 * @version: 1.0
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionController {


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        log.error("could_not_read_json...", e.getMessage());
        return Result.error(500, "JSON 格式错误", e.getMessage());
    }

    /**
     * 认证错误
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthenticationException.class)
    public Result handleAuthenticationException(
            AuthenticationException e) {
        log.error("认证错误", e.getMessage());
        return Result.error(401, e.getMessage(), e.getMessage());
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result handleValidationException(MethodArgumentNotValidException e) {
        log.error("parameter_validation_exception...", e);
        return Result.error(405, "请求类型不支持！", e.getMessage());
    }

    /**
     * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("request_method_not_supported...", e);
        return Result.error(405, "不被支持的访问方法！", e.getMessage());
    }

    /**
     * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
     * 是ServletException的子类,需要Servlet API支持
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Result handleHttpMediaTypeNotSupportedException(Exception e) {
        log.error("content_type_not_supported...", e);
        return Result.error415("不支持的媒体类型！", e.getMessage());
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> globalException(HttpServletRequest request, Exception e) {
        return Result.error(getStatus(request).value(), e.getMessage(), e.getCause().toString());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
