package com.example.nacosclient.base;

import java.util.Arrays;

/**
 * @author hf
 * date   2022/1/5 9:16
 * description
 */
public enum ResponseCodeEnum {

    /**
     * 成功
     */
    SUCCESS(1, "成功"),
    URL_NOT_FOUND(404, "url not found"),
    SERVICE_EXCEPTION(10000, "服务异常"),
    EXT_SERVICE_EXCEPTION(10001, "外部服务异常"),
    BIZ_EXCEPTION(10002, "业务异常"),

    ;

    private Integer code;

    private String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ResponseCodeEnum of(Integer code) {
        ResponseCodeEnum[] values = values();
        return Arrays.stream(values).filter(p -> p.getCode().equals(code)).findFirst().get();
    }

}
