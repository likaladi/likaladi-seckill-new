package com.likaladi.goods.mapper;

import com.likaladi.base.CommonMapper;
import com.likaladi.goods.entity.Category;
import com.likaladi.goods.vo.CategoryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface CategoryMapper extends CommonMapper<Category> {

    String SQL_FIELD = "select cb.category_id id, c.name, c.parent_id parentId, c.sort from category_brand cb"+
                       " left join category c on (cb.category_id = c.id)";

    @Select({
            "<script>"+
                    SQL_FIELD+ " where cb.brand_id = #{brandId}"
            + "</script>"
    })
    List<CategoryVo> queryByBrandId(@Param("brandId") Long brandId);

    @Select({
            "<script>"
                    + "select c.id, c.name, c.parent_id parentId, c.sort from category c where c.id in"
                    + "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>"
                    +   "#{id}"
                    + "</foreach>"
            + "</script>"
    })
    List<CategoryVo> queryByIds(@Param("ids") Set<Long> ids);
}
