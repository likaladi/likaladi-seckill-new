package com.likaladi.search.feign;

import com.likaladi.goods.vo.SpuDetailVo;
import com.likaladi.goods.vo.SpuSpecVo;
import com.likaladi.goods.vo.SpuVo;
import com.likaladi.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${spring.feign.services.goods}")
public interface SpecClient {

    /**
     * 根据三级分类id获取规格属性
     * @param cid
     * @return
     */
    @GetMapping("spec/listBy/{cid}")
    ResponseResult<SpuSpecVo> listByCategoryId(@PathVariable Long cid);

}
