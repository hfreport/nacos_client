package com.example.nacosclient.controller;

import com.example.nacosclient.base.BaseReq;
import com.example.nacosclient.bo.HelloBO;
import com.example.nacosclient.service.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hf
 * date   2022/1/5 9:07
 * description
 */
@RestController
@RequestMapping("/example")
@Slf4j
public class ExampleController {

    @Resource
    private ExampleService exampleService;

    @RequestMapping("/hello")
    public HelloBO hello() {
        log.info(" hello ");
        return exampleService.hello();
    }
    @RequestMapping("/baseReq")
    public String baseReq(@RequestBody BaseReq req) {
        log.info(" token {} ", req.getToken());
        return req.getToken();
    }

    @RequestMapping("/str")
    public String str() {
        return "str";
    }

}
