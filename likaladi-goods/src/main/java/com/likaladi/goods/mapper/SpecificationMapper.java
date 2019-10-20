package com.likaladi.goods.mapper;

import com.likaladi.base.CommonMapper;
import com.likaladi.goods.dto.SpecQueryDto;
import com.likaladi.goods.entity.Specification;
import com.likaladi.goods.vo.SpecVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SpecificationMapper extends CommonMapper<Specification> {

    String SQL = "SELECT s.id, s.group, s.name, s.options, s.type, s.unit, s.is_gloab isGloab, " +
            "s.is_search isSearch, s.category_id categoryId, c.name categoryName " +
            "FROM specification s LEFT JOIN category c ON (s.category_id = c.id) ";

    @Select(SQL + "WHERE s.id = #{id}")
    SpecVo queryById(@Param("id") Long id);

    @Select({
            "<script>"
                + SQL
                +"WHERE 1 = 1 "
                +    "<if test='condition.name != null and condition.name != \"\"'>"
                +       "<bind name='_name' value=\"'%' + condition.name + '%'\"/>"
                +       " and s.name like #{_name}"
                +   "</if>"
                +    "<if test='condition.categoryId != null'>"
                +       " and s.category_id = #{condition.categoryId}"
                +   "</if>"
                +    "<if test='condition.sortBy != null and condition.sortBy != \"\"'>"
                +       " order by ${condition.sortBy} ${condition.sortEnum}"
                +   "</if>"
            + "</script>"
    })
    List<SpecVo> selectByPage(@Param("condition") SpecQueryDto specQueryDto);
}
