package com.example.nacosclient.config;

import com.example.nacosclient.base.ResponseCodeEnum;
import com.example.nacosclient.exception.BizException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hf
 * date   2022/1/10 10:10
 * description 发生io异常不会走此类
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("feign 调用异常");
        return new BizException(ResponseCodeEnum.EXT_SERVICE_EXCEPTION);
    }
}
