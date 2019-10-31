package com.likaladi.search.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author likaladi
 * 可搜索的规格属性
 */
@Data
public class SpecAttr {

    /**
     * 规格属性名称
     */
    private String key;

    /**
     * 规格属性对应列表值
     */
    private List<String> values;
}
