package com.example.nacosclient.interceptor;

import com.example.nacosclient.exception.BizException;
import com.example.nacosclient.base.ResponseCodeEnum;
import com.example.nacosclient.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author hf
 * date   2022/1/5 15:03
 * description
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {


    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Object handlerException(Exception e, HttpServletResponse response) {
        log.error("出现异常 {} ",e);
        return Result.buildFail(ResponseCodeEnum.SERVICE_EXCEPTION.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BizException.class)
    public Object handlerBizException(BizException e, HttpServletResponse response) {
        log.error("出现异常 {} ",e);
        return Result.buildFail(ResponseCodeEnum.of(e.getCode()));
    }


}
