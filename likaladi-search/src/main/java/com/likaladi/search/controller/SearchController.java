package com.likaladi.search.controller;

import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.search.entity.Goods;
import com.likaladi.search.entity.SearchRequest;
import com.likaladi.search.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author likaladi
 * 搜索商品
 */
@Api(value = "商品搜索接口", description = "商品搜索接口")
@RestController
@RequestMapping
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 搜索商品
     *
     * @param request
     * @return
     */
    @ApiOperation(value="搜索商品", notes="搜索商品")
    @PostMapping("page")
    public PageResult<Goods> search(@RequestBody SearchRequest request) {
        PageResult<Goods> result = this.searchService.search(request);

        if(Objects.isNull(request)){
            ErrorBuilder.throwMsg("不存在匹配的记录");
        }

        return result;
    }
}
