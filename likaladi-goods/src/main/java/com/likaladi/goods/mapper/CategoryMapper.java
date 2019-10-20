package com.likaladi.goods.mapper;

import com.likaladi.base.CommonMapper;
import com.likaladi.goods.entity.Category;
import com.likaladi.goods.vo.CategoryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper extends CommonMapper<Category> {

    @Select({
            "<script>"
                    + "select cb.category_id id, c.name, c.parent_id parentId, c.sort from category_brand cb"
                    + " left join category c on (cb.category_id = c.id)"
                    + " where cb.brand_id = #{brandId}"
            + "</script>"
    })
    List<CategoryVo> queryByBrandId(@Param("brandId") Long brandId);
}
