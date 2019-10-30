package com.likaladi.search.feign;

import com.likaladi.goods.vo.SpuVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${spring.feign.services.goods}")
public interface SpuClient {

    @GetMapping("/spu/{id}")
    SpuVo queryById(@PathVariable Long id);
}
