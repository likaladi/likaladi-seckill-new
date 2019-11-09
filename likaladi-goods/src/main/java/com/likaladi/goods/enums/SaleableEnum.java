package com.likaladi.goods.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author likaladi
 * 上架加状态
 */
@Getter
@AllArgsConstructor
public enum SaleableEnum {

    LOWER_SHELF(0, "下架"),
    UPPER_SHELF(1, "上架"),
    DELETE(2, "删除");

    private Integer code;

    private String value;

    public static String getValue(int code){
        for (SaleableEnum specTypEnum : values()) {
            if(Objects.equals(specTypEnum.code, code)){
                return specTypEnum.value;
            }
        }
        return null;
    }
}
