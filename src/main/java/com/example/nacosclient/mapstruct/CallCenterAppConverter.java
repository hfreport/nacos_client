package com.example.nacosclient.mapstruct;

import com.example.nacosclient.bo.CallCenterAppInfoBO;
import com.example.nacosclient.dao.model.CallCenterAppInfo;
import com.example.nacosclient.vo.CallCenterAppInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hf
 * date   2022/1/11 15:48
 * description
 */
@Mapper
public interface CallCenterAppConverter {

    CallCenterAppConverter INSTANCE = Mappers.getMapper(CallCenterAppConverter.class);

    /**
     * 转model
     * @param bizData
     * @return
     */
    CallCenterAppInfo convertCallCenterAppInfo(CallCenterAppInfoVO bizData);
}
