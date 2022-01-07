package com.example.nacosclient.service.impl;

import com.example.nacosclient.bo.HelloBO;
import com.example.nacosclient.service.ExampleService;
import org.springframework.stereotype.Service;

/**
 * @author hf
 * date   2022/1/5 9:10
 * description
 */
@Service
public class ExampleServiceImpl implements ExampleService {

    @Override
    public HelloBO hello() {
        HelloBO res = new HelloBO();
        res.setMes("hello");
        return res;
    }
}
