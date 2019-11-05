package com.likaladi.goods.mapper;

import com.likaladi.base.CommonMapper;
import com.likaladi.goods.dto.SpuQueryDto;
import com.likaladi.goods.entity.Spu;
import com.likaladi.goods.vo.SpuVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SpuMapper extends CommonMapper<Spu> {

    String SQL_FILE = "select s.id, s.title, s.sub_title subTitle, s.image, s.min_price minPrice, s.max_price maxPrice, s.create_time createTime," +
            "s.cid1, s.cid2, s.cid3, b.id brandId, b.name brandName from spu s left join brand b on(s.brand_id = b.id) ";

    @Update({
            "<script>"
                    + "UPDATE spu SET is_enable = 0 where id in "
                    + "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>"
                        + "#{id}"
                    + "</foreach>"
        + "</script>"
    })
    int deleteSpuByIds(@Param("ids") List<Long> ids);


    @Select({
            "<script>"
                 + SQL_FILE + "where 1 = 1 "
                 + "<if test='condition.title != null and condition.title != \"\"'>"
                 +     "<bind name='_title' value=\"'%' + condition.title + '%'\"/>"
                 +     "and s.title like #{_title} "
                 + "</if>"
                 + "<if test='condition.saleable != null'>"
                 +     "and s.saleable = #{condition.saleable} "
                 + "</if>"
                 + "<if test='condition.price != null'>"
                 +     "and s.min_price <![CDATA[<=]]>  #{condition.price} "
                 +     "and s.max_price <![CDATA[>=]]> #{condition.price} "
                 + "</if>"
            + "</script>"
    })
    List<SpuVo> selectByPage(@Param("condition") SpuQueryDto spuQueryDto);

    @Select(SQL_FILE + " where s.id = #{id}")
    SpuVo querySpuById(@Param("id") Long id);

    @Select({
            "<script>"
                    + "select count(1) from spu where cid3 in "
                    + "<foreach item='cateogryId' index='index' collection='cateogryIds' open='(' separator=',' close=')'>"
                    + "#{cateogryId}"
                    + "</foreach>"
                    + "</script>"
    })
    int queryCountByCateogryIds(@Param("cateogryIds") List<Long> categoryIds);
}
