package com.yunli.bigdata.enums;

import lombok.Getter;

/**
 * @author pingchangxin
 * @description 操作类型
 * @date 2021/06/18
 **/
@Getter
public enum OperateTypeEnum {
    //数据上报处理进程
    ADD("a", "新增"),
    UPDATE("u", "更新"),
    DELETE("d", "删除"),
    ;

    OperateTypeEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    public static OperateTypeEnum getByCode(String code){
        OperateTypeEnum[] values =  values();
        for (OperateTypeEnum item : values) {
            if(item.getCode().equals(code)){
                return item;
            }
        }
        return null;
    }

    public static String getNameByCode(String code){
        OperateTypeEnum[] values =  values();
        for (OperateTypeEnum item : values) {
            if(item.getCode().equals(code)){
                return item.getName();
            }
        }
        return "";
    }
}
