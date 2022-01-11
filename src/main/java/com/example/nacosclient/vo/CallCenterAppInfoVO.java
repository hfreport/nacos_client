package com.example.nacosclient.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author hf
 * date   2022/1/11 15:44
 * description
 */
@Data
public class CallCenterAppInfoVO implements Serializable {
    private static final long serialVersionUID = -1438950207029925549L;

    private Long id;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * token
     */
    private String appToken;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用ID
     */
    private Long projectId;

    /**
     * 应用状态
     */
    private Integer appStatus;

}
