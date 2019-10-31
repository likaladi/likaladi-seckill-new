package com.likaladi.search.feign;

import com.likaladi.goods.vo.SpuVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${spring.feign.services.goods}")
public interface SpuClient {

    /**
     * 根据id查询商品
     * @param id spuId
     * @return
     */
    @GetMapping("/spu/{id}")
    SpuVo queryById(@PathVariable Long id);

    /**
     * 根据spuId获取Spu详情信息
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    SpuVo querySpuSkuDetail(@PathVariable Long id);
}
