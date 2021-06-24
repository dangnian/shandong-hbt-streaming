package com.yunli.bigdata.enums;

import lombok.Getter;

/**
 * @author pingchangxin
 * @description 处理状态
 * @date 2021/06/18
 **/
@Getter
public enum StatusEnum {
    //数据上报处理进程
    PENDING("1", "待处理"),
    PROCESSED("2", "已处理"),
    DELETE("3", "删除"),
    ;

    StatusEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    public static StatusEnum getByCode(String code){
        StatusEnum[] values =  values();
        for (StatusEnum item : values) {
            if(item.getCode().equals(code)){
                return item;
            }
        }
        return null;
    }

    public static String getNameByCode(String code){
        StatusEnum[] values =  values();
        for (StatusEnum item : values) {
            if(item.getCode().equals(code)){
                return item.getName();
            }
        }
        return "";
    }
}
