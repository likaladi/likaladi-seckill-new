package com.likaladi.search.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.vo.BrandVo;
import com.likaladi.goods.vo.CategoryVo;
import com.likaladi.goods.vo.SpuDetailVo;
import com.likaladi.goods.vo.SpuVo;
import com.likaladi.search.entity.Goods;
import com.likaladi.search.entity.SearchRequest;
import com.likaladi.search.entity.Sku;
import com.likaladi.search.feign.CategoryClient;
import com.likaladi.search.feign.SpuClient;
import com.likaladi.search.repository.GoodsRepository;
import com.likaladi.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author likaladi
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private GoodsRepository goodsRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void createIndex(Long spuId) {
        SpuVo spuVo = spuClient.queryById(spuId);

        // 构建商品
        Goods goods = this.buildGoods(spuVo);

        // 保存数据到索引库
        this.goodsRepository.save(goods);
    }

    @Override
    public void deleteIndex(Long spuId) {
        this.goodsRepository.deleteById(spuId);
    }

    @Override
    public PageResult<Goods> search(SearchRequest request) {
        /** 判断是否有搜索条件，如果没有，直接返回null。不允许搜索全部商品 */
        if (StringUtils.isBlank(request.getKey())) {
            return null;
        }

        // 1、构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //MatchQueryBuilder basicQuery = QueryBuilders.matchQuery("all",  request.getKey()).operator(Operator.AND);
        QueryBuilder basicQuery = buildBasicQueryWithFilter(request);

        // 1.1、基本查询
        queryBuilder.withQuery(basicQuery);
        // 通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
        queryBuilder.withSourceFilter(new FetchSourceFilter(
                new String[]{"id", "skus", "subTitle"}, null));

//        // 1.2.分页排序
//        searchWithPageAndSort(queryBuilder,request);
//
//        // 1.3、聚合
//        String categoryAggName = "category"; // 商品分类聚合名称
//        String brandAggName = "brand"; // 品牌聚合名称
//        // 对商品分类进行聚合
//        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
//        // 对品牌进行聚合
//        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));
//
//        // 2、查询，获取结果
//        AggregatedPage<Goods> pageInfo = (AggregatedPage<Goods>) this.goodsRepository.search(queryBuilder.build());
//
//        // 3.2、商品分类的聚合结果
//        List<CategoryVo> categories =
//                getCategoryAggResult(pageInfo.getAggregation(categoryAggName));
//        // 3.3、品牌的聚合结果
//        List<BrandVo> brands = getBrandAggResult(pageInfo.getAggregation(brandAggName));
//
//        // 根据商品分类判断是否需要聚合
//        List<Map<String, Object>> specs = new ArrayList<>();
//        if (categories.size() == 1) {
//            // 如果商品分类只有一个才进行聚合，并根据分类与基本查询条件聚合
//            specs = getSpec(categories.get(0).getId(), basicQuery);
//        }
//
//        // 返回结果
//        return new SearchResult(pageInfo.getTotalElements(), pageInfo.getTotalPages(), pageInfo.getContent(), categories, brands, specs);

        return null;
    }


    /**
     * 构建基本查询条件
     * @param request
     * @return
     */
    private QueryBuilder buildBasicQueryWithFilter(SearchRequest request) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // 基本查询条件
        queryBuilder.must(QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND));

        // 整理过滤条件
        Map<String, String> filter = request.getFilter();
        if(Objects.nonNull(filter)){
            // 过滤条件构建器
            BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();

            for (Map.Entry<String, String> entry : filter.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                // 商品分类和品牌要特殊处理
                if (key != "cid3" && key != "brandId") {
                    key = "specs." + key + ".keyword";
                }
                // 字符串类型，进行term查询
                filterQueryBuilder.must(QueryBuilders.termQuery(key, value));
            }
            // 添加过滤条件
            queryBuilder.filter(filterQueryBuilder);
        }

        return queryBuilder;
    }

    /**
     * 创建es商品对象
     * @param spuVo
     * @return
     */
    private Goods buildGoods(SpuVo spuVo) {

        /** 查询商品分类名称集合 */
        List<String> names = categoryClient.queryNamesByIds(Arrays.asList(spuVo.getCid1(), spuVo.getCid2(), spuVo.getCid3()));

        /** 查询商品详情和sku信息 */
        SpuDetailVo spuDetailVo = spuClient.querySpuSkuDetail(spuVo.getId());

        /** 处理sku，仅封装id、价格、标题、图片，并获得价格集合 */
        List<Double> prices = new ArrayList<>();
        List<Sku> skuList = new ArrayList<>();

        spuDetailVo.getSkus().forEach(spuSkuVo -> {
            prices.add(spuSkuVo.getPrice().doubleValue());
            skuList.add(
                    Sku.builder()
                            .id(spuSkuVo.getSkuId())
                            .title(spuVo.getTitle())
                            .price(spuSkuVo.getPrice().doubleValue())
                            .image(spuSkuVo.getImageList().get(0))
                            .build()
            );
        });

        String skuJson = getSkuJson(skuList);
        if(Objects.isNull(skuJson)){
            ErrorBuilder.throwMsg("创建goods索引失败");
        }

        /** 获取可搜索的规格属性参数 */
        //Map<String, Object> searchMap = getSearchMap(spuDetail);

        return Goods.builder()
                .id(spuVo.getId())
                .subTitle(spuVo.getSubTitle())
                .brandId(spuVo.getBrandId())
                .cid1(spuVo.getCid1())
                .cid2(spuVo.getCid2())
                .cid3(spuVo.getCid3())
                .createTime(spuVo.getCreateTime())
                .price(prices)
                .skus(skuJson)
                //.specs(searchMap)
                .all(spuVo.getTitle() + " " + spuVo.getBrandName() + " " +StringUtils.join(names, " "))
                .build();
    }

//    /**
//     * 将商品搜索的规格属性 转成对应的 searchMap
//     * @param spuDetail
//     * @return
//     */
//    private Map<String, Object> getSearchMap(SpuVo spuDetail) {
//
//        Map<String, Object> searchMap = new HashMap<>(spuDetail.getSpecs().size());
//
//        /** 筛选搜索的规格存储到 searchMap*/
//        spuDetail.getSpecs().forEach(spuSpecDto -> {
//            if(spuSpecDto.getIsSearch()){
//                searchMap.put(spuSpecDto.getKey(), spuSpecDto.getValue());
//            }
//        });
//
//        /** 筛选搜索的属性存储到 searchMap*/
//        spuDetail.getAttrs().forEach(spuAttrDto -> {
//            spuAttrDto.getParams().forEach(attrsDto -> {
//                if(attrsDto.getIsSearch()){
//                    searchMap.put(attrsDto.getName(), attrsDto.getChoiceVal());
//                }
//            });
//        });
//
//        return searchMap;
//    }

    private String getSkuJson(List<Sku> skuList){
        try{
            return mapper.writeValueAsString(skuList);
        } catch (Exception e){
            log.error("List<Map<String, Object>> skuList对象转换json字符异常：",e);
        }

        return null;
    }

}
