package com.likaladi.search.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "${spring.feign.services.goods}")
public interface CategoryClient {

    /**
     * 根据多个ids查询分类名称
     * @param ids
     * @return
     */
    @PostMapping("names")
    List<String> queryNamesByIds(@RequestBody List<Long> ids);
}
