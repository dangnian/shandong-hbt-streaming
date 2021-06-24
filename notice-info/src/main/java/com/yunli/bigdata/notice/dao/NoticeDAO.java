package com.yunli.bigdata.notice.dao;

import com.yunli.bigdata.notice.model.Notice;
import java.util.List;

public interface NoticeDAO {
    int deleteByPrimaryKey(String id);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    int insertBatch(List<Notice> list);

    int updateBatch(List<Notice> list);

}