package com.yunli.bigdata.needdeal.model;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author pingchangxin
 * @description 待办数据实体
 * @date 2021/6/22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Needdeal implements Serializable {

    private static final long serialVersionUID = 7028042691639961507L;

    /**
     * 唯一标识
     */
    private String id;

    /**
     * 系统标识
     */
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
    private String launchUserId;

    /**
     * 发起人姓名
     */
    private String launchUserName;

    /**
     * 处理人ID
     */
    private String dealUserId;

    /**
     * 处理人姓名
     */
    private String dealUserName;

    /**
     * 是否对接app
     */
    private String isApp;

    /**
     * App连接地址
     */
    private String appUrl;

    /**
     * 状态
     */
    private String statusId;

    /**
     * 流程状态
     */
    private String processStatus;

    /**
     * 通知时间
     */
    private String noticeTime;

    /**
     * 办理时间
     */
    private String processingTime;

    /**
     * 新增时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;



}
