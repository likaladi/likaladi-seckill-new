package com.likaladi.goods.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author likaladi
 * 规格属性操作枚举
 */
@Getter
@AllArgsConstructor
public enum SpecTypEnum {

    TEXTBOX(1, "文本框"),
    RADIO(2, "单选框"),
    CHECK_BOX(3, "复选框"),
    ROP_DOWN_BOX(4, "下拉框");

    private Integer code;

    private String value;

    public static String getValue(int code){
        for (SpecTypEnum specTypEnum : values()) {
            if(Objects.equals(specTypEnum.code, code)){
                return specTypEnum.value;
            }
        }
        return null;
    }
}
