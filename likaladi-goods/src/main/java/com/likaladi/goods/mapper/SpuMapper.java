package com.likaladi.goods.mapper;

import com.likaladi.base.CommonMapper;
import com.likaladi.goods.entity.Spu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SpuMapper extends CommonMapper<Spu> {

    @Update({
            "<script>"
                    + "UPDATE spu SET is_enable = 0 where id in "
                    + "<foreach item='id' index='index' collection='ids' open='(' separator=',' close=')'>"
                        + "#{id}"
                    + "</foreach>"
        + "</script>"
    })
    int deleteSpuByIds(@Param("ids") List<Long> ids);
}