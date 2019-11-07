package com.likaladi.goods.mapper;

import com.likaladi.base.CommonMapper;
import com.likaladi.goods.entity.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SkuMapper extends CommonMapper<Sku> {

    @Select({
            "<script>"
                    + "select * from where spu_id in "
                    + "<foreach item='spuId' index='index' collection='spuIds' open='(' separator=',' close=')'>"
                    +   "#{spuId}"
                    + "</foreach>"
                    + "</script>"
    })
    List<Sku> queryBySpuIds(@Param("spuIds") List<Long> spuIds);
}