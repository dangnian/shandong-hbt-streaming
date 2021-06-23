package com.yunli.bigdata.needdeal.process;

import static com.yunli.bigdata.enums.OperateTypeEnum.ADD;
import static com.yunli.bigdata.enums.OperateTypeEnum.DELETE;
import static com.yunli.bigdata.enums.OperateTypeEnum.UPDATE;

import com.yunli.bigdata.needdeal.dao.NeeddealDAO;
import com.yunli.bigdata.needdeal.domain.MessageData;
import com.yunli.bigdata.needdeal.domain.NeeddealDomain;
import com.yunli.bigdata.needdeal.model.Needdeal;
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
 * @description 待办消息处理器
 * @date 2021/6/22
 **/
@Slf4j
public class NeeddealProcessor implements Processor {

    private static final long serialVersionUID = -5460780420648339177L;

    @Override
    public List<OutputMessage> process(List<InputMessage> list, Map<String, String> map) {
        if (CollectionUtils.isEmpty(list)) {
            log.error("待办消息处理器接收数据为空");
            return Collections.emptyList();
        }
        // 按照操作类型分组
        Map<String, List<Needdeal>> needdealMap = list.stream()
                .map(this::convertNeeddealDomain)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.groupingBy(NeeddealDomain::getOperateType,
                        Collectors.collectingAndThen(Collectors.toList(), this::convertNeeddealList)));

        // 获取sqlSession
        NeeddealDAO needdealDao = SqlSessionFactoryUtil.getMapper(NeeddealDAO.class);

        // 新增
        if (CollectionUtils.isNotEmpty(needdealMap.get(ADD.getCode()))) {
            int result = needdealDao.insertBatch(needdealMap.get(ADD.getCode()));
            if (result <= 0) {
                log.error("批量新增待办消息失败");
            }
        }

        // 更新
        if (CollectionUtils.isNotEmpty(needdealMap.get(UPDATE.getCode()))) {
            int result = needdealDao.updateBatch(needdealMap.get(UPDATE.getCode()));
            if (result <= 0) {
                log.error("批量更新待办消息失败");
            }
        }

        // 删除
        if (CollectionUtils.isNotEmpty(needdealMap.get(DELETE.getCode()))) {
            int result = needdealDao.deleteBatch(needdealMap.get(DELETE.getCode()).stream()
                    .map(Needdeal::getId)
                    .collect(Collectors.toList()));
            if (result <= 0) {
                log.error("批量删除待办消息失败");
            }
        }

        return Collections.emptyList();
    }

    private List<NeeddealDomain> convertNeeddealDomain(InputMessage inputMessage) {
        MessageData messageData = (MessageData) inputMessage.getBody();
        return messageData.getData();
    }

    private List<Needdeal> convertNeeddealList(List<NeeddealDomain> needdealDomainList) {
        return needdealDomainList.stream().map(this::convertNeeddeal).collect(Collectors.toList());
    }

    private Needdeal convertNeeddeal(NeeddealDomain needdealDomain) {
        Needdeal needdeal = new Needdeal();
        BeanBaseUtils.copyBean(needdealDomain, needdeal, true);
        Date date = new Date();
        if (ADD.getCode().equals(needdealDomain.getOperateType())) {
            needdeal.setIsDeleted(0);
            needdeal.setGmtCreate(date);
        }
        needdeal.setGmtModified(date);
        return needdeal;
    }



}
