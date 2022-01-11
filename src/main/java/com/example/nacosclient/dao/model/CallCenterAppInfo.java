package com.example.nacosclient.dao.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author hf
 * @date 2021/6/7 14:49
 * @description 呼叫中心拨号计划
 */
@Data
@TableName("call_center_app")
public class CallCenterAppInfo implements Serializable {
    private static final long serialVersionUID = 4109127868041084048L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 创建时间
     */
    private Instant createdTime;

    /**
     * 更新时间
     */
    private Instant updatedTime;

}
