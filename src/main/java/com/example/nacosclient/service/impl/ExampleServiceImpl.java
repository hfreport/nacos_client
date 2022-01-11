package com.example.nacosclient.service.impl;

import com.example.nacosclient.bo.HelloBO;
import com.example.nacosclient.dao.model.CallCenterAppInfo;
import com.example.nacosclient.mapstruct.CallCenterAppConverter;
import com.example.nacosclient.service.CallCenterAppService;
import com.example.nacosclient.service.ExampleService;
import com.example.nacosclient.vo.CallCenterAppInfoVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hf
 * date   2022/1/5 9:10
 * description
 */
@Service
public class ExampleServiceImpl implements ExampleService {

    @Resource
    private CallCenterAppService callCenterAppService;

    @Override
    public HelloBO hello() {
        HelloBO res = new HelloBO();
        res.setMes("hello");
        return res;
    }

    @Override
    public boolean saveData(CallCenterAppInfoVO bizData) {
        CallCenterAppInfo entity = CallCenterAppConverter.INSTANCE.convertCallCenterAppInfo(bizData);
        return callCenterAppService.save(entity);
    }
}
