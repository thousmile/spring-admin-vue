package com.ifsaid.baodao.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/17 15:19
 * @describe： 统一返回实体类
 * @version: 1.0
 */

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * 返回异常信息
     */
    private String error;

    /**
     * 时间搓
     */
    private Long timestamp;

    public static <T> Result result(Integer status, String msg, T data, String error) {
        Result r = new Result();
        r.setStatus(status);
        r.setMessage(msg);
        r.setData(data);
        r.setError(error);
        r.setTimestamp(System.currentTimeMillis());
        return r;
    }

    //快速返回成功
    public static <T> Result success() {
        return success("success", null);
    }

    public static <T> Result success(String msg) {
        return success(msg, null);
    }

    public static <T> Result success(T data) {
        return success("success", data);
    }

    public static <T> Result success(String msg, T data) {
        return result(200, msg, data, null);
    }

    /**
     * @return Result
     * @describe 自定义错误代码
     * @parameter [msg, error]
     * @date 2018/10/20
     * @author Wang Chen Chen
     */
    public static <T> Result error(int code, String msg, String error) {
        return result(code, msg, null, error);
    }

    /**
     * @return Result
     * @describe 1、认证授权相关错误
     * 2、请求参数有误。
     * @parameter [msg, error]
     * @date 2018/10/20
     * @author Wang Chen Chen
     */
    public static <T> Result error401(String msg, String error) {
        return result(401, msg, null, error);
    }


    /**
     * @return Result
     * @describe 1、语义有误，当前请求无法被服务器理解。除非进行修改，否则客户端不应该重复提交这个请求。
     * 2、请求参数有误。
     * @parameter [msg, error]
     * @date 2018/10/20
     * @author Wang Chen Chen
     */
    public static <T> Result error400(String msg, String error) {
        return result(400, msg, null, error);
    }

    /**
     * @return Result
     * @describe 请求失败，请求所希望得到的资源未被在服务器上发现
     * @parameter [msg, error]
     * @date 2018/10/20
     * @author Wang Chen Chen
     */
    public static <T> Result error404(String msg, String error) {
        return result(404, msg, null, error);
    }


    /**
     * @return Result
     * @describe 请求超时。客户端没有在服务器预备等待的时间内完成一个请求的发送。客户端可以随时再次提交这一请求而无需进行任何更改。
     * @parameter [msg, error]
     * @date 2018/10/20
     * @author Wang Chen Chen
     */
    public static <T> Result error408(String msg, String error) {
        return result(408, msg, null, error);
    }


    /**
     * @return Result
     * @describe 对于当前请求的方法和所请求的资源，请求所支持的格式错误
     * @parameter [msg, error]
     * @date 2018/10/20
     * @author Wang Chen Chen
     */
    public static <T> Result error415(String msg, String error) {
        return result(415, msg, null, error);
    }

    /**
     * @return Result
     * @describe 服务器内部错误
     * @parameter [msg, error]
     * @date 2018/10/20
     * @author Wang Chen Chen
     */
    public static <T> Result error500(String msg, String error) {
        return result(500, msg, null, error);
    }

    /**
     * @return Result
     * @describe 网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应。
     * @parameter [msg, error]
     * @date 2018/10/20
     * @author Wang Chen Chen
     */
    public static <T> Result error502(String msg, String error) {
        return result(502, msg, null, error);
    }

}
