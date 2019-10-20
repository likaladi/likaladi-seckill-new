package com.likaladi.goods.mapper;

import com.likaladi.base.CommonMapper;
import com.likaladi.goods.dto.BrandQueryDto;
import com.likaladi.goods.entity.Brand;
import com.likaladi.goods.vo.BrandVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper extends CommonMapper<Brand> {


    @Select({
            "<script>"
                    + "select id, name, image, first_char firstChar from brand"
                    + " where 1 = 1"
                    +    "<if test='condition.name != null and condition.name != \"\"'>"
                    +       "<bind name='_name' value=\"'%' + condition.name + '%'\"/>"
                    +       " and name like #{_name}"
                    +   "</if>"
                    +    "<if test='condition.sortBy != null and condition.sortBy != \"\"'>"
                    +       " order by ${condition.sortBy} ${condition.sortEnum}"
                    +   "</if>"
                    + "</script>"
    })
    List<BrandVo> selectByPage(@Param("condition") BrandQueryDto queryDto);
}
