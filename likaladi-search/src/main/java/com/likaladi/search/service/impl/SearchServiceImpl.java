package com.likaladi.search.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.vo.*;
import com.likaladi.response.ResponseResult;
import com.likaladi.search.entity.Goods;
import com.likaladi.search.entity.SearchRequest;
import com.likaladi.search.entity.SearchResult;
import com.likaladi.search.entity.Sku;
import com.likaladi.search.feign.BrandClient;
import com.likaladi.search.feign.CategoryClient;
import com.likaladi.search.feign.SpecClient;
import com.likaladi.search.feign.SpuClient;
import com.likaladi.search.repository.GoodsRepository;
import com.likaladi.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author likaladi
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private SpecClient specClient;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void createIndex(Long spuId) {

        try{
            ResponseResult<SpuVo> spuVoResult = spuClient.queryById(spuId);


            if(!Objects.equals(spuVoResult.getCode(), 0)){
                log.error("商品id = "+spuId+", 创建索引失败：", spuVoResult);
                return;
            }

            SpuVo spuVo = spuVoResult.getData();

            /** 查询商品详情和sku信息 */
            ResponseResult<SpuDetailVo> spuDetailVoResult = spuClient.querySpuSkuDetail(spuVo.getId());

            if(!Objects.equals(spuDetailVoResult.getCode(), 0)){
                log.error("商品id = "+spuId+", 创建索引失败：", spuDetailVoResult);
                return;
            }

            // 构建商品
            Goods goods = this.buildGoods(spuVo, spuDetailVoResult.getData());

            // 保存数据到索引库
            this.goodsRepository.save(goods);

        }catch (Exception e){
            log.error("商品id = "+spuId+", 创建索引失败：", e);
        }
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

        QueryBuilder basicQuery = buildBasicQueryWithFilter(request);

        // 1.1、基本查询
        queryBuilder.withQuery(basicQuery);
        // 通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
        queryBuilder.withSourceFilter(new FetchSourceFilter(
                new String[]{"id", "skus", "subTitle"}, null));

        // 1.2.分页排序
        searchWithPageAndSort(queryBuilder,request);

        /** 1.3、聚合 */

        // 商品分类聚合名称
        String categoryAggName = "category";

        // 品牌聚合名称
        String brandAggName = "brand";

        // 对商品分类进行聚合
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        // 对品牌进行聚合
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        // 2、查询，获取结果
        AggregatedPage<Goods> pageInfo = (AggregatedPage<Goods>) this.goodsRepository.search(queryBuilder.build());

        // 3.2、商品分类的聚合结果
        List<CategoryVo> categories = getCategoryAggResult(pageInfo.getAggregation(categoryAggName));
        // 3.3、品牌的聚合结果
        List<BrandVo> brands = getBrandAggResult(pageInfo.getAggregation(brandAggName));

        // 根据商品分类判断是否需要聚合
        List<Map<String, Object>> specs = getSpec(categories.get(0).getId(), basicQuery);

        // 返回结果
        return new SearchResult(pageInfo.getTotalElements(), pageInfo.getTotalPages(), pageInfo.getContent(), categories, brands, specs);

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
    @Override
    public Goods buildGoods(SpuVo spuVo, SpuDetailVo spuDetailVo) {

        /** 查询商品分类名称集合 */
        List<String> names = Arrays.asList(spuVo.getCategory1().getName(), spuVo.getCategory2().getName(), spuVo.getCategory3().getName());

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
        Map<String, Object> searchMap = getSearchMap(spuDetailVo);


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
                .specs(searchMap)
                .all(spuVo.getTitle() + " " + spuVo.getBrandName() + " " +StringUtils.join(names, " "))
                .build();
    }


    private String getSkuJson(List<Sku> skuList){
        try{
            return mapper.writeValueAsString(skuList);
        } catch (Exception e){
            log.error("List<Map<String, Object>> skuList对象转换json字符异常：",e);
        }

        return null;
    }

    /**
     * 构建基本查询条件
     * @param queryBuilder
     * @param request
     */
    private void searchWithPageAndSort(NativeSearchQueryBuilder queryBuilder, SearchRequest request) {
        /** 准备分页参数 */
        int page = request.getPage();
        int size = request.getSize();

        /** 1、分页 */
        queryBuilder.withPageable(PageRequest.of(page - 1, size));

        /** 2、排序 */
        String sortBy = request.getSortBy();
        Boolean desc = request.getDescending();
        if (StringUtils.isNotBlank(sortBy)) {
            /** 如果不为空，则进行排序 */
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }
    }

//    // 解析商品分类聚合结果
//    private List<Category> getCategoryAggResult(Aggregation aggregation) {
//        try{
//            List<Category> categories = new ArrayList<>();
//            LongTerms categoryAgg = (LongTerms) aggregation;
//            List<Long> cids = new ArrayList<>();
//            for (LongTerms.Bucket bucket : categoryAgg.getBuckets()) {
//                cids.add(bucket.getKeyAsNumber().longValue());
//            }
//            // 根据id查询分类名称
//            List<String> names = this.categoryClient.queryNameByIds(cids);
//
//            for (int i = 0; i < names.size(); i++) {
//                Category c = new Category();
//                c.setId(cids.get(i));
//                c.setName(names.get(i));
//                categories.add(c);
//            }
//            return categories;
//        } catch (Exception e){
//            log.error("分类聚合出现异常：", e);
//            return null;
//        }
//    }

    /**
     * 获取可搜索的规格属性参数
     * @param spuDetailVo
     * @return
     */
    private Map<String, Object> getSearchMap(SpuDetailVo spuDetailVo){
        Map<String, Object> searchMap = new HashMap<>(spuDetailVo.getSpuSpecVo().getAttrs().size());
        spuDetailVo.getSpuSpecVo().getAttrs().forEach(specVo -> {
            specVo.getParams().forEach(specAttrParamVo -> {
                if(specAttrParamVo.getIsSearch() && !CollectionUtils.isEmpty(specAttrParamVo.getV())){
                    searchMap.put(specAttrParamVo.getK(), specAttrParamVo.getV());
                }
            });
        });
        spuDetailVo.getSpuSpecVo().getSpecs().forEach(specVo -> {
            specVo.getParams().forEach(specAttrParamVo -> {
                if(specAttrParamVo.getIsSearch() && !CollectionUtils.isEmpty(specAttrParamVo.getV())){
                    searchMap.put(specAttrParamVo.getK(), specAttrParamVo.getV());
                }
            });
        });

        return searchMap;
    }

    /**
     * 解析商品分类聚合结果
     * @param aggregation
     * @return
     */
    private List<CategoryVo> getCategoryAggResult(Aggregation aggregation) {
        try{
            LongTerms categoryAgg = (LongTerms) aggregation;

            List<Long> cids = categoryAgg.getBuckets().stream().map(bucket -> {
                return bucket.getKeyAsNumber().longValue();
            }).collect(Collectors.toList());

            ResponseResult<List<CategoryVo>> categoryVoResult = categoryClient.queryNamesByIds(cids);
            if(Objects.equals(categoryVoResult.getCode(), 0)){
                return categoryVoResult.getData();
            }

            return null;

        } catch (Exception e){
            log.error("分类聚合出现异常：", e);
            return null;
        }
    }

    /**
     * 解析品牌聚合结果
     * @param aggregation
     * @return
     */
    private List<BrandVo> getBrandAggResult(Aggregation aggregation) {
        try {
            LongTerms brandAgg = (LongTerms) aggregation;

            List<Long> brandIds = brandAgg.getBuckets().stream().map(bucket -> {
                return bucket.getKeyAsNumber().longValue();
            }).collect(Collectors.toList());

            // 根据id查询品牌
            ResponseResult<List<BrandVo>> brandVoResult = brandClient.queryListByIds(brandIds);
            if(Objects.equals(brandVoResult.getCode(), 0)){
                return brandVoResult.getData();
            }

            return null;
        } catch (Exception e){
            log.error("品牌聚合出现异常：", e);
            return null;
        }
    }

    /**
     * 聚合出规格参数
     *
     * @param cid
     * @param query
     * @return
     */
    private List<Map<String, Object>> getSpec(Long cid, QueryBuilder query) {
        try {

            /** 存储可搜索的规格属性 */
            List<SpecAttrParamVo> specAttrParamVos = new ArrayList<>();

            ResponseResult<SpuSpecVo> spuSpecVoResult = specClient.listByCategoryId(cid);

            if(!Objects.equals(spuSpecVoResult.getCode(), 0)){
                return null;
            }

            SpuSpecVo spuSpecVo = spuSpecVoResult.getData();

            spuSpecVo.getAttrs().forEach(specVo -> {
                specAttrParamVos.addAll(
                        specVo.getParams().stream().filter(specAttrParamVo -> specAttrParamVo.getIsSearch()).collect(Collectors.toList())
                );
            });

            spuSpecVo.getSpecs().forEach(specVo -> {
                specAttrParamVos.addAll(
                        specVo.getParams().stream().filter(specAttrParamVo -> specAttrParamVo.getIsSearch()).collect(Collectors.toList())
                );
            });

            List<Map<String, Object>> specs = new ArrayList<>();

            NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
            queryBuilder.withQuery(query);

            // 聚合规格参数
            specAttrParamVos.forEach(specAttrParamVo -> {
                String key = specAttrParamVo.getK();
                //因为规格参数保存时不做分词，因此其名称会自动带上一个.keyword后缀
                queryBuilder.addAggregation(AggregationBuilders.terms(key).field("specs." + key + ".keyword"));

            });

            // 查询
            Map<String, Aggregation> aggs = this.elasticsearchTemplate.query(queryBuilder.build(),
                    SearchResponse::getAggregations).asMap();

            // 解析聚合结果
            specAttrParamVos.forEach(specAttrParamVo -> {
                Map<String, Object> spec = new HashMap<>();
                String key = specAttrParamVo.getK();
                spec.put("k", key);
                StringTerms terms = (StringTerms) aggs.get(key);
                spec.put("options", terms.getBuckets().stream().map(StringTerms.Bucket::getKeyAsString));
                specs.add(spec);
            });

            return specs;
        } catch (Exception e){
            log.error("规格聚合出现异常：", e);
            return null;
        }

    }
}
