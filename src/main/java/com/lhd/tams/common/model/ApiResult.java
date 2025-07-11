package com.lhd.tams.common.model;

/**
 * 接口返回值结构
 * @param <T>
 */
public class ApiResult<T> {

    /** 成功状态码 */
    public static final Integer SUCCESS_CODE = 200;
    /** 失败状态码 */
    public static final Integer ERROR_CODE = 500;
    /** 参数错误状态码 */
    public static final Integer PARAM_ERROR_CODE = 400;
    /** 未授权状态码 */
    public static final Integer UNAUTHORIZED_CODE = 401;
    /** 资源不存在状态码 */
    public static final Integer NOT_FOUND_CODE = 404;

    /** 错误编码 */
    private Integer code;
    /** 响应信息 */
    private String msg;
    /** 响应数据 */
    private T data;

    public ApiResult() {
    }

    public ApiResult(String msg) {
        this(msg, null);
    }

    public ApiResult(T data) {
        this("", data);
    }

    public ApiResult(String msg, T data) {
        this(null, msg, data);
    }

    public ApiResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 成功响应方法
    public static <T> ApiResult<T> success() {
        return new ApiResult<>(SUCCESS_CODE, "操作成功", null);
    }

    public static <T> ApiResult<T> success(String msg) {
        return new ApiResult<>(SUCCESS_CODE, msg, null);
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(SUCCESS_CODE, "操作成功", data);
    }

    public static <T> ApiResult<T> success(String msg, T data) {
        return new ApiResult<>(SUCCESS_CODE, msg, data);
    }

    // 错误响应方法
    public static <T> ApiResult<T> error() {
        return error("操作失败");
    }

    public static <T> ApiResult<T> error(String msg) {
        return error(ERROR_CODE, msg);
    }

    public static <T> ApiResult<T> error(Integer code, String msg) {
        return new ApiResult<>(code, msg, null);
    }

    public static <T> ApiResult<T> error(String msg, T data) {
        return new ApiResult<>(ERROR_CODE, msg, data);
    }

    // 参数错误响应
    public static <T> ApiResult<T> paramError() {
        return paramError("参数错误");
    }

    public static <T> ApiResult<T> paramError(String msg) {
        return error(PARAM_ERROR_CODE, msg);
    }

    // 未授权响应
    public static <T> ApiResult<T> unauthorized() {
        return unauthorized("未授权");
    }

    public static <T> ApiResult<T> unauthorized(String msg) {
        return error(UNAUTHORIZED_CODE, msg);
    }

    // 资源不存在响应
    public static <T> ApiResult<T> notFound() {
        return notFound("资源不存在");
    }

    public static <T> ApiResult<T> notFound(String msg) {
        return error(NOT_FOUND_CODE, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {

        this.data = data;

        this.data = data;
    }

}
