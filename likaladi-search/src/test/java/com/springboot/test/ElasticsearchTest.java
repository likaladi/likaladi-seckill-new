//package com.springboot.test;
//
//import com.likaladi.search.SearchServiceApplication;
//import com.likaladi.search.entity.Goods;
//import com.likaladi.search.repository.GoodsRepository;
//import com.likaladi.search.service.SearchService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = SearchServiceApplication.class)
//public class ElasticsearchTest {
//
//    @Autowired
//    private GoodsRepository goodsRepository;
//
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    @Autowired
//    private SearchService searchService;
//
//    @Test
//    public void createIndex(){
//        // 创建索引
//        this.elasticsearchTemplate.createIndex(Goods.class);
//        // 配置映射
//        this.elasticsearchTemplate.putMapping(Goods.class);
//    }
//
//    @Test
//    public void loadData(){
//        // 创建索引
//        this.elasticsearchTemplate.createIndex(Goods.class);
//
//        // 配置映射
//        this.elasticsearchTemplate.putMapping(Goods.class);
//
//
//        int page = 1;
//        int rows = 100;
//        int size = 0;
//        do {
//            // 查询分页数据
//            PageResult<SpuBo> result = this.goodsClient.querySpuByPage(page, rows, true, null);
//            List<SpuBo> spus = result.getItems();
//            size = spus.size();
//            // 创建Goods集合
//            List<Goods> goodsList = new ArrayList<>();
//            // 遍历spu
//            for (SpuBo spu : spus) {
//                try {
//                    Goods goods = this.searchService.buildGoods(spu);
//                    goodsList.add(goods);
//                } catch (Exception e) {
//                    break;
//                }
//            }
//
//            this.goodsRepository.saveAll(goodsList);
//            page++;
//        } while (size == 100);
//    }
//
//}
