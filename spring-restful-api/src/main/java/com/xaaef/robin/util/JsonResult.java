package com.xaaef.robin.util;

import com.xaaef.robin.domain.Pagination;
import com.xaaef.robin.enums.OAuth2Error;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * RESTful API 统一返回实体类
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 9:31
 */

@Getter
@Setter
public class JsonResult<T> implements Serializable {

    private static final int SUCCESS = 200;

    private static final int FAIL = 100;

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
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    private static <T> JsonResult<T> result(int status, String message, T data) {
        var result = new JsonResult<T>();
        result.status = status;
        result.message = message;
        result.data = data;
        return result;
    }

    /**
     * 自定义返回状态码
     *
     * @param status
     * @param data
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    private static <T> JsonResult<T> result(int status, T data) {
        return result(status, "ok", data);
    }

    /**
     * 自定义返回状态码 [不建议使用，建议扩展方法]
     *
     * @param status
     * @param message
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult<String> result(int status, String message) {
        return result(status, message, null);
    }


    /**
     * 操作成功
     *
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult<String> success() {
        return result(SUCCESS, "ok");
    }

    /**
     * 操作成功
     *
     * @param total
     * @param list
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult<Pagination<T>> success(long total, List<T> list) {
        return success(new Pagination<T>(total, list));
    }


    /**
     * 操作成功
     *
     * @param message
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult<String> success(String message) {
        return result(SUCCESS, message, null);
    }

    /**
     * 操作成功
     *
     * @param data
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult<T> success(T data) {
        return result(SUCCESS, "ok", data);
    }

    /**
     * 操作成功
     *
     * @param message
     * @param data
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult<T> success(String message, T data) {
        return result(SUCCESS, message, data);
    }


    /**
     * 操作失败
     *
     * @param message
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult<String> fail(String message) {
        return result(FAIL, message, null);
    }

    /**
     * 操作失败
     *
     * @param message
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult<T> fail(String message, Class<T> data) {
        return result(FAIL, message, null);
    }


    /**
     * 操作失败
     *
     * @param message
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult<T> fail(String message, T data) {
        return result(FAIL, message, null);
    }


    /**
     * 异常错误
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult<String> error(int status, String message) {
        return result(status, message, null);
    }

    /**
     * @param error
     * @return JsonResult
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static <T> JsonResult<String> error(OAuth2Error error) {
        return result(error.getStatus(), error.getError(), null);
    }


    /**
     * 获取 成功的常量
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static int getSuccess() {
        return SUCCESS;
    }

    /**
     * 获取 失败的常量
     *
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/12 21:08
     */
    public static int getFail() {
        return FAIL;
    }

}
