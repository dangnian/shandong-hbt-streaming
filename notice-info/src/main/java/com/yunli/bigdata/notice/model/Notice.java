package com.yunli.bigdata.notice.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Notice {

    private String id;

    private String systemSign;

    private String title;

    private String url;

    private String noticeUserId;

    private String noticeUserName;

    private String statusId;

    private String isApp;

    private String appUrl;

    private String noticeTime;

    private String readTime;

    private Integer isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

}