package com.yunli.bigdata.needdeal.formatter;

import com.yunli.bigdata.needdeal.domain.MessageData;
import com.yunli.bigdata.streaming.Deserializer;
import com.yunli.bigdata.util.JsonUtil;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pingchangxin
 * @description 待办消息反序列化
 * @date 2021/6/22
 **/
@Slf4j
public class NeeddealDeserializer implements Deserializer {

    @Override
    public Serializable deserialize(byte[] bytes) {
        try {
            MessageData messageData = JsonUtil
                    .readValue(new String(bytes, StandardCharsets.UTF_8), MessageData.class);
            if (messageData != null && messageData.getType() != null ) {
                return messageData;
            } else {
                log.error("待办消息返序列化为空");
            }
        } catch (IOException e) {
            log.error("待办消息反序列异常", e);
        }
        return null;
    }

}
