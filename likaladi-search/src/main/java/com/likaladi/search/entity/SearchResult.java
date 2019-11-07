package com.likaladi.search.entity;

import com.likaladi.base.PageResult;
import com.likaladi.goods.vo.BrandVo;
import com.likaladi.goods.vo.CategoryVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author likaladi
 * 商品搜索条件
 */
@Data
public class SearchResult extends PageResult<Goods> {

    /**
     * 分类过滤条件
     */
    private List<CategoryVo> categories;

    /**
     * 品牌过滤条件
     */
    private List<BrandVo> brands;

    /**
     * 规格参数过滤条件
     */
    private List<Map<String,Object>> specs;

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<CategoryVo> categories, List<BrandVo> brands, List<Map<String,Object>> specs) {
        super(total, items, Long.parseLong(totalPage+""));
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

}
