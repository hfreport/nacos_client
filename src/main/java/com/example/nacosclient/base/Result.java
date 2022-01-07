package com.example.nacosclient.base;

/**
 * @author hf
 * date   2022/1/5 9:15
 * description
 */
public class Result<T> {

    private static ResponseCodeEnum SUCCESS = ResponseCodeEnum.SUCCESS;
    private static ResponseCodeEnum FAIL = ResponseCodeEnum.SERVICE_EXCEPTION;

    private Integer code;

    private String message;

    private T data;

    public Result() {
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result buildSuccess(){
        return buildSuccess(SUCCESS.getCode(), SUCCESS.getMessage());
    }

    public static Result buildSuccess(Object data) {
        return new Result(SUCCESS.getCode(), SUCCESS.getMessage(), data);
    }

    public static Result buildFail(ResponseCodeEnum codeEnum) {
        return buildFail(codeEnum.getCode(), codeEnum.getMessage());
    }

    public static Result buildFail(Integer code, String message) {
        return new Result(code, message);
    }

    public static Result buildSuccess(Integer code, String message) {
        return new Result(code, message, null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
