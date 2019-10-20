package com.likaladi.goods.mapper;

import com.likaladi.base.CommonMapper;
import com.likaladi.goods.entity.CategoryBrand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryBrandMapper extends CommonMapper<CategoryBrand> {

    @Delete("delete from category_brand where brand_id = #{brandId}")
    int deleteByBrandId(@Param("brandId") Long brandId);


    @Delete({
            "<script>"
                + "delete from category_brand where brand_id in "
                    + "<foreach item='brandId' index='index' collection='brandIds' open='(' separator=',' close=')'>"
                      + "#{brandId}"
                    + "</foreach>"
           + "</script>"
    })
    int deleteByBrandIds(@Param("brandIds") List<Long> brandIds);
}
