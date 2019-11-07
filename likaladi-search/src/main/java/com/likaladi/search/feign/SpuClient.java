package com.likaladi.search.feign;

import com.likaladi.base.PageResult;
import com.likaladi.goods.dto.SpuQueryDto;
import com.likaladi.goods.vo.SpuDetailVo;
import com.likaladi.goods.vo.SpuSearchVo;
import com.likaladi.goods.vo.SpuVo;
import com.likaladi.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "${spring.feign.services.goods}")
public interface SpuClient {

    /**
     * 根据id查询商品
     * @param id spuId
     * @return
     */
    @GetMapping("/spu/{id}")
    ResponseResult<SpuVo> queryById(@PathVariable Long id);

    /**
     * 根据spuId获取Spu详情信息
     * @param id
     * @return
     */
    @GetMapping("spu/detail/{id}")
    ResponseResult<SpuDetailVo> querySpuSkuDetail(@PathVariable Long id);

    /**
     * 分页查询上架商品
     * @param spuQueryDto
     * @return
     */
    @PostMapping("spu/upperShelfSpu")
    ResponseResult<PageResult<SpuSearchVo>> upperShelfSpu(@RequestBody @Valid SpuQueryDto spuQueryDto);
}
