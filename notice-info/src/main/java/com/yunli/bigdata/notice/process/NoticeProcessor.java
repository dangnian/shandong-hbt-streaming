package com.yunli.bigdata.notice.process;

import static com.yunli.bigdata.enums.OperateTypeEnum.ADD;
import static com.yunli.bigdata.enums.OperateTypeEnum.DELETE;
import static com.yunli.bigdata.enums.OperateTypeEnum.UPDATE;

import com.alibaba.fastjson.JSON;
import com.yunli.bigdata.enums.StatusEnum;
import com.yunli.bigdata.notice.dao.NoticeDAO;
import com.yunli.bigdata.notice.domain.MessageData;
import com.yunli.bigdata.notice.domain.NoticeDomain;
import com.yunli.bigdata.notice.model.Notice;
import com.yunli.bigdata.streaming.InputMessage;
import com.yunli.bigdata.streaming.OutputMessage;
import com.yunli.bigdata.streaming.Processor;
import com.yunli.bigdata.util.BeanBaseUtils;
import com.yunli.bigdata.util.SqlSessionFactoryUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

/**
 * @author pingchangxin
 * @description 通知消息处理器
 * @date 2021/6/22
 **/
@Slf4j
public class NoticeProcessor implements Processor {

    private static final long serialVersionUID = -5460780420648339177L;

    @Override
    public List<OutputMessage> process(List<InputMessage> list, Map<String, String> map) {
        log.info("通知消息处理器开始处理,数据{}", JSON.toJSONString(list));
        if (CollectionUtils.isEmpty(list)) {
            log.error("通知消息处理器接收数据为空");
            return Collections.emptyList();
        }
        // 按照操作类型分组
        Map<String, List<Notice>> noticeMap = list.stream()
                .map(this::convertNoticeDomain)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.groupingBy(NoticeDomain::getOperateType,
                        Collectors.collectingAndThen(Collectors.toList(), this::convertNoticeList)));

        // 获取sqlSession
        NoticeDAO noticeDao = SqlSessionFactoryUtil.getMapper(NoticeDAO.class);

        // 新增
        List<Notice> addNoticeList = noticeMap.get(ADD.getCode());
        if (CollectionUtils.isNotEmpty(addNoticeList)) {
            try {
                int result = noticeDao.insertBatch(addNoticeList);
                if (result <= 0) {
                    log.error("批量新增通知消息失败");
                }
            } catch (Exception e) {
                log.error("批量新增通知消息异常,数据{},异常信息{}", JSON.toJSONString(addNoticeList), e.getMessage());
            }
        }

        // 更新
        List<Notice> updateNoticeList = noticeMap.get(UPDATE.getCode());
        if (CollectionUtils.isNotEmpty(updateNoticeList)) {
            try {
                int result = noticeDao.updateBatch(updateNoticeList);
                if (result <= 0) {
                    log.error("批量更新通知消息失败");
                }
            } catch (Exception e) {
                log.error("批量更新通知消息异常,数据{},异常信息{}", JSON.toJSONString(updateNoticeList), e.getMessage());
            }
        }

        // 删除
        List<Notice> deleteNoticeList = noticeMap.get(DELETE.getCode());
        if (CollectionUtils.isNotEmpty(deleteNoticeList)) {
            try {
                int result = noticeDao.updateBatch(deleteNoticeList);
                if (result <= 0) {
                    log.error("批量删除通知消息失败");
                }
            } catch (Exception e) {
                log.error("批量删除通知消息异常,数据{},异常信息{}", JSON.toJSONString(deleteNoticeList), e.getMessage());
            }
        }

        return Collections.emptyList();
    }

    private List<NoticeDomain> convertNoticeDomain(InputMessage inputMessage) {
        MessageData messageData = (MessageData) inputMessage.getBody();
        return messageData.getData();
    }

    private List<Notice> convertNoticeList(List<NoticeDomain> noticeDomainList) {
        return noticeDomainList.stream().map(this::convertNotice).collect(Collectors.toList());
    }

    private Notice convertNotice(NoticeDomain noticeDomain) {
        Notice notice = new Notice();
        BeanBaseUtils.copyBean(noticeDomain, notice, true);
        Date date = new Date();
        if (ADD.getCode().equals(noticeDomain.getOperateType())) {
            notice.setGmtCreate(date);
        }
        // 删除写死状态（约定）
        if (DELETE.getCode().equals(noticeDomain.getOperateType())) {
            notice.setStatusId(StatusEnum.DELETE.getCode());
        }
        notice.setGmtModified(date);
        return notice;
    }



}
