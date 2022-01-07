package com.example.nacosclient.exception;

import com.example.nacosclient.base.ResponseCodeEnum;

/**
 * @author hf
 * date   2022/1/5 15:12
 * description
 */
public class BizException extends RuntimeException{

    private Integer code;

    public BizException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public BizException(String message, Integer code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(ResponseCodeEnum code) {
        this(code.getMessage(), code.getCode());
    }

    public BizException(ResponseCodeEnum code, Throwable cause) {
        this(code.getMessage(), code.getCode(), cause);
    }

    public Integer getCode() {
        return code;
    }
}
