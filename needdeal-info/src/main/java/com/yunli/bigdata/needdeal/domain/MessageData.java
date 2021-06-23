package com.yunli.bigdata.needdeal.domain;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author pingchangxin
 * @description 消息体
 * @date 2021/6/22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageData implements Serializable {

    private static final long serialVersionUID = -5759533354385730677L;

    private String type;

    private List<NeeddealDomain> data;
}
