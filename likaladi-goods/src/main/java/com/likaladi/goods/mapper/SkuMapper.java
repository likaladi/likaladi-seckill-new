package com.likaladi.goods.mapper;

import com.likaladi.base.CommonMapper;
import com.likaladi.goods.entity.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SkuMapper extends CommonMapper<Sku> {

    String SQL_FILED = "select id, spu_id spuId, images, barcode, price, indexes, own_spec ownSpec," +
            "stock_num stockNum, is_enable isEnable, create_time createTime, update_time updateTime ";

    @Select({
            "<script>"
                    + SQL_FILED + "from sku where spu_id in "
                    + "<foreach item='spuId' index='index' collection='spuIds' open='(' separator=',' close=')'>"
                    +   "#{spuId}"
                    + "</foreach>"
                    + "</script>"
    })
    List<Sku> queryBySpuIds(@Param("spuIds") List<Long> spuIds);

    @Update({
            "<script>"
                    + "UPDATE sku SET saleable = #{saleable} where spu_id in "
                    + "<foreach item='spuId' index='index' collection='spuIds' open='(' separator=',' close=')'>"
                    + "#{spuId}"
                    + "</foreach>"
        + "</script>"
    })
    int updateSkuStateBySpuIds(@Param("spuIds") List<Long> spuIds, @Param("saleable") Integer saleable);

    @Update({
            "<script>"
                    + "UPDATE sku SET saleable = #{saleable} where id in "
                    + "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>"
                    + "#{id}"
                    + "</foreach>"
                    + "</script>"
    })
    int updateSkuStateByIds(@Param("ids") List<Long> ids, @Param("saleable") Integer saleable);
}
