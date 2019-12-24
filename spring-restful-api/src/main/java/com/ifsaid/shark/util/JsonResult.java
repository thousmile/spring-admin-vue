package com.ifsaid.shark.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * RESTful API 统一返回实体类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Getter
@Setter
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = 1456468469856161L;

    /**
     * 返回状态码
     */
    private Integer status;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回内容
     */
    private T data;

    private JsonResult() {
    }

    /**
     * 自定义返回状态码
     *
     * @param status
     * @param message
     * @param data
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    private static <T> JsonResult result(int status, String message, T data) {
        JsonResult result = new JsonResult();
        result.status = status;
        result.message = message;
        result.data = data;
        return result;
    }

    /**
     * 自定义返回状态码
     *
     * @param httpStatus
     * @param data
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    private static <T> JsonResult result(HttpStatus httpStatus, T data) {
        return result(httpStatus.status, httpStatus.value, data);
    }

    /**
     * 自定义返回状态码 [不建议使用，建议扩展方法]
     *
     * @param status
     * @param message
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult result(int status, String message) {
        return result(status, message, null);
    }


    /**
     * 操作成功
     *
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success() {
        return success(HttpStatus.OK.value, null);
    }

    /**
     * 操作成功
     *
     * @param total
     * @param currentPage
     * @param list
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(long total, T list) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("total", total);
        map.put("list", list);
        return success(HttpStatus.OK.value, map);
    }

    /**
     * 操作成功
     *
     * @param message
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(String message) {
        return success(message, null);
    }

    /**
     * 操作成功
     *
     * @param data
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(T data) {
        return success(HttpStatus.OK.value, data);
    }

    /**
     * 操作成功
     *
     * @param message
     * @param data
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult success(String message, T data) {
        return result(HttpStatus.OK.status, message, data);
    }


    /**
     * 操作失败
     *
     * @param message
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult fail(String message) {
        return result(HttpStatus.FAIL.status, message, null);
    }


    /**
     * 未授权
     *
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult error401() {
        return result(HttpStatus.UNAUTHORIZED.status, HttpStatus.UNAUTHORIZED.value, null);
    }


    /**
     * 不支持的请求类型
     *
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult error415() {
        return result(HttpStatus.UNSUPPORTED_MEDIA_TYPE.status, HttpStatus.UNSUPPORTED_MEDIA_TYPE.value, null);
    }


    /**
     * 404 未找到
     *
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult error404() {
        return result(HttpStatus.NOT_FOUND.status, HttpStatus.NOT_FOUND.value, null);
    }


    /**
     * 服务器内部错误
     *
     * @return com.ifsaid.shark.util.JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult error500() {
        return result(HttpStatus.INTERNAL_SERVER_ERROR.status, HttpStatus.INTERNAL_SERVER_ERROR.value, null);
    }


    /**
     * restful api 返回状态码。不够的时候自己扩展
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @return com.ifsaid.shark.util.JsonResult
     * @date 2019/12/12 21:08
     */
    @Getter
    public enum HttpStatus {

        /**
         * 正常 返回代码
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/12 21:08
         */
        OK(200, "Ok"),

        /**
         * 错误 返回代码
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/12 21:08
         */
        FAIL(0, "Fail"),

        /**
         * 404 为找到
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/12 21:08
         */
        NOT_FOUND(404, "Not Found"),

        /**
         * 未经授权
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/12 21:08
         */
        UNAUTHORIZED(401, "你还没有经过授权认证"),

        /**
         * 不支持的媒体类型
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/12 21:08
         */
        UNSUPPORTED_MEDIA_TYPE(415, "不支持此请求类型！"),


        /**
         * 服务器内部错误 返回代码
         *
         * @author Wang Chen Chen<932560435@qq.com>
         * @date 2019/12/12 21:08
         */
        INTERNAL_SERVER_ERROR(500, "服务器内部错误");

        private int status;

        private String value;

        HttpStatus(int status, String value) {
            this.status = status;
            this.value = value;
        }

    }

}