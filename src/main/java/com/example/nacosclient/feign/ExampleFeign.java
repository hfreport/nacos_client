package com.example.nacosclient.feign;

import com.example.nacosclient.base.Result;
import com.example.nacosclient.bo.HelloBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hf
 * date   2022/1/8 11:12
 * description
 */
@FeignClient(value = "store", contextId = "ExampleFeign", fallback = ExampleFeign.ExampleFeignImpl.class)
public interface ExampleFeign {

    @RequestMapping("/example/hello")
    Result<HelloBO> hello();

    @Component
    @Slf4j
    class ExampleFeignImpl implements ExampleFeign {

        @Override
        public Result<HelloBO> hello() {
            log.info(" 服务降级 ");
            HelloBO helloBO = new HelloBO();
            helloBO.setMes("降级");
            return Result.buildSuccess(helloBO);
        }
    }

}
