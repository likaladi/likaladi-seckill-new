package com.likaladi.search.feign;

import com.likaladi.goods.vo.BrandVo;
import com.likaladi.goods.vo.CategoryVo;
import com.likaladi.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "${spring.feign.services.goods}")
public interface BrandClient {

    /**
     * 根据多个ids查询品牌名称
     * @param brandIds
     * @return
     */
    @PostMapping("brand/ids")
    ResponseResult<List<BrandVo>> queryListByIds(@RequestBody List<Long> brandIds);
}
