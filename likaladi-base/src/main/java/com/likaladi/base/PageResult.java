package com.likaladi.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author likaladi
 * 分页返回包装类
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    /**
     * 总条数
     */
    private Long total;

    /**
     * 当前页数据
     */
    private List<T> items;

    /**
     * 总页数
     */
    private Long totalPage;

}
