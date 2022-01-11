package com.example.nacosclient.service;

import com.example.nacosclient.bo.HelloBO;
import com.example.nacosclient.vo.CallCenterAppInfoVO;

/**
 * @author hf
 * date   2022/1/5 9:08
 * description
 */
public interface ExampleService {

    /**
     * 示例请求
     * @return
     */
    HelloBO hello();

    /**
     * 保存数据到数据库
     * @param bizData
     * @return
     */
    boolean saveData(CallCenterAppInfoVO bizData);

}
