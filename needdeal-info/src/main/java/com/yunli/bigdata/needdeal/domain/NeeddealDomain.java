package com.yunli.bigdata.needdeal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author pingchangxin
 * @description 待办消息实体
 * @date 2021/6/22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class NeeddealDomain implements Serializable {

    private static final long serialVersionUID = 7028042691639961507L;

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 系统标识
     */
    @JsonProperty("system_sign")
    private String systemSign;

    /**
     * 标题
     */
    private String title;

    /**
     * 连接地址
     */
    private String url;

    /**
     * 发起人ID
     */
    @JsonProperty("launch_user_id")
    private String launchUserId;

    /**
     * 发起人姓名
     */
    @JsonProperty("launch_user_name")
    private String launchUserName;

    /**
     * 处理人ID
     */
    @JsonProperty("deal_user_id")
    private String dealUserId;

    /**
     * 处理人姓名
     */
    @JsonProperty("deal_user_name")
    private String dealUserName;

    /**
     * 是否对接app
     */
    @JsonProperty("is_app")
    private String isApp;

    /**
     * App连接地址
     */
    @JsonProperty("app_url")
    private String appUrl;

    /**
     * 状态
     */
    @JsonProperty("status_id")
    private String statusId;

    /**
     * 流程状态
     */
    @JsonProperty("process_status")
    private String processStatus;

    /**
     * 通知时间
     */
    @JsonProperty("notice_time")
    private String noticeTime;

    /**
     * 办理时间
     */
    @JsonProperty("processing_time")
    private String processingTime;

    /**
     * 操作类型
     */
    @JsonProperty("operate_type")
    private String operateType;



}
