package com.example.nacosclient.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.nacosclient.dao.mapper.CallCenterAppMapper;
import com.example.nacosclient.dao.model.CallCenterAppInfo;
import com.example.nacosclient.service.CallCenterAppService;
import org.springframework.stereotype.Service;

/**
 * @author hf
 * @date 2021/6/9 13:25
 * @description
 */
@Service
public class CallCenterAppServiceImpl extends ServiceImpl<CallCenterAppMapper, CallCenterAppInfo> implements CallCenterAppService {

}
