package com.yunli.bigdata.notice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author pingchangxin
 * @description 通知实体
 * @date 2021/6/22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class NoticeDomain implements Serializable {

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
     * 通知人ID
     */
    @JsonProperty("notice_user_id")
    private String noticeUserId;

    /**
     * 通知人姓名
     */
    @JsonProperty("notice_user_name")
    private String noticeUserName;

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
     * 通知时间
     */
    @JsonProperty("notice_time")
    private String noticeTime;

    /**
     * 已读时间
     */
    @JsonProperty("read_time")
    private String readTime;

    /**
     * 操作类型
     */
    @JsonProperty("operate_type")
    private String operateType;

}
