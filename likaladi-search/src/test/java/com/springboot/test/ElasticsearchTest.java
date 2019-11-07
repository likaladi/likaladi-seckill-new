package com.springboot.test;

import com.alibaba.fastjson.JSONObject;
import com.likaladi.base.PageResult;
import com.likaladi.goods.dto.SpuQueryDto;
import com.likaladi.goods.vo.SpuSearchVo;
import com.likaladi.response.ResponseResult;
import com.likaladi.search.SearchServiceApplication;
import com.likaladi.search.entity.Goods;
import com.likaladi.search.feign.SpuClient;
import com.likaladi.search.repository.GoodsRepository;
import com.likaladi.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchServiceApplication.class)
public class ElasticsearchTest {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private SearchService searchService;

    @Autowired
    private SpuClient spuClient;

    @Test
    public void createIndex(){
        // 创建索引
        this.elasticsearchTemplate.createIndex(Goods.class);
        // 配置映射
        this.elasticsearchTemplate.putMapping(Goods.class);
    }

    @Test
    public void loadData(){
        // 创建索引
        this.elasticsearchTemplate.createIndex(Goods.class);

        // 配置映射
        this.elasticsearchTemplate.putMapping(Goods.class);

        int page = 1;
        int rows = 100;
        int size = 0;
        SpuQueryDto spuQueryDto = new SpuQueryDto();
        spuQueryDto.setRows(rows);
        spuQueryDto.setSaleable(1);
        do {
            spuQueryDto.setPage(page);

            ResponseResult<PageResult<SpuSearchVo>> responseResult = this.spuClient.upperShelfSpu(spuQueryDto);
            if(!Objects.equals(responseResult.getCode(), 0)){
                log.error("分页查询失败；request = {}, response = {}", JSONObject.toJSONString(spuQueryDto),
                        JSONObject.toJSONString(responseResult));
                break;
            }

            // 查询分页数据
            PageResult<SpuSearchVo> result = responseResult.getData();
            List<SpuSearchVo> spuSearchVos = result.getItems();
            size = spuSearchVos.size();
            // 创建Goods集合
            List<Goods> goodsList = new ArrayList<>();

            for(SpuSearchVo spuSearchVo : spuSearchVos){
                try {
                    Goods goods = this.searchService.buildGoods(spuSearchVo.getSpuVo(), spuSearchVo.getSpuDetailVo());
                    goodsList.add(goods);
                } catch (Exception e) {
                    break;
                }
            }
            this.goodsRepository.saveAll(goodsList);
            page++;
        } while (size == 100);
    }

}
