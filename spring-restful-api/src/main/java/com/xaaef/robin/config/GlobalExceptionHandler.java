package com.xaaef.robin.config;

import com.xaaef.robin.enums.OAuth2Error;
import com.xaaef.robin.exception.JwtAuthException;
import com.xaaef.robin.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * 全局异常处理
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/13 13:47
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = JwtAuthException.class)
    public JsonResult<String> bizExceptionHandler(JwtAuthException e) {
        e.printStackTrace();
        log.error("发生业务异常！原因是： {}  {} ", e.getStatus(), e.getMessage());
        return JsonResult.error(e.getStatus(), e.getMessage());
    }


    /**
     * 参数校验统一异常处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult<String> handleBindException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.warn("参数校验异常: {} --> {}", fieldError.getField(), fieldError.getDefaultMessage());
        return JsonResult.fail(fieldError.getDefaultMessage());
    }


    /**
     * 处理空指针的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    public JsonResult<String> exceptionHandler(NullPointerException e) {
        e.printStackTrace();
        log.error("发生空指针异常！原因是: {} ", e.getMessage());
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return JsonResult.error(status, e.getMessage());
    }


    /**
     * 权限不足异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public JsonResult<String> exceptionHandler(AccessDeniedException e) {
        return JsonResult.error(OAuth2Error.ACCESS_DENIED);
    }


    /**
     * 处理空指针的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public JsonResult<String> exceptionHandler(RuntimeException e) {
        e.printStackTrace();
        log.error("发生运行时异常！原因是: {} ", e.getMessage());
        return JsonResult.fail(e.getMessage());
    }


    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public JsonResult<String> exceptionHandler(Exception e) {
        e.printStackTrace();
        log.error("未知异常！原因是: {} ", e.getMessage());
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        return JsonResult.error(status, e.getMessage());
    }

}
