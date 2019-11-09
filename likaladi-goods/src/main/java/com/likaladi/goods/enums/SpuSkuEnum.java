package com.likaladi.goods.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author likaladi
 * 区分spu和sku的枚举
 */
@Getter
@AllArgsConstructor
public enum SpuSkuEnum {

    SPU(1, "商品spu"),
    SKU(2, "商品sku");

    private Integer code;

    private String value;

    public static String getValue(int code){
        for (SpuSkuEnum specTypEnum : values()) {
            if(Objects.equals(specTypEnum.code, code)){
                return specTypEnum.value;
            }
        }
        return null;
    }
}
