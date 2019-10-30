package com.likaladi.search.service.impl;

import com.likaladi.goods.vo.SpuVo;
import com.likaladi.search.entity.Goods;
import com.likaladi.search.service.SearchService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author likaladi
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Override
    public Goods buildGoods(SpuVo spuVo) {

        Goods goods = new Goods();
//
//        // 查询商品分类名称
//        List<String> names = this.categoryClient.queryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
//
//        // 查询sku
//        List<Sku> skus = this.goodsClient.querySkuBySpuId(spu.getId());
//        // 查询详情
//        SpuDetail spuDetail = this.goodsClient.querySpuDetailById(spu.getId());
//        // 查询规格参数
//        List<SpecParam> params = this.specificationClient.querySpecParam(null, spu.getCid3(), true, null);
//
//        // 处理sku，仅封装id、价格、标题、图片，并获得价格集合
//        List<Long> prices = new ArrayList<>();
//        List<Map<String, Object>> skuList = new ArrayList<>();
//        skus.forEach(sku -> {
//            prices.add(sku.getPrice());
//            Map<String, Object> skuMap = new HashMap<>();
//            skuMap.put("id", sku.getId());
//            skuMap.put("title", sku.getTitle());
//            skuMap.put("price", sku.getPrice());
//            skuMap.put("image", StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
//            skuList.add(skuMap);
//        });
//
//        //将商品详情规格属性转为key value形式
//        Map<String, Object> specTempMap = getSpecMap(spuDetail.getSpecifications(), spuDetail.getSpecTemplate());
//
//
//        // 获取可搜索的规格参数
//        Map<String, Object> searchSpec = new HashMap<>();
//
//        // 过滤规格模板，把所有可搜索的信息保存到Map中
//        Map<String, Object> specMap = new HashMap<>();
//        params.forEach(p -> {
//            if (p.getSearching()) {
//                if (p.getGeneric()) {
//                    String value = (String) specTempMap.get(p.getFiledName());
//                    if(p.getNumeric() != null && p.getNumeric()){
//                        value = chooseSegment(value, p);
//                    }
//                    specMap.put(p.getFiledName(), StringUtils.isBlank(value) ? "其它" : value);
//                } else {
//                    specMap.put(p.getFiledName(), specTempMap.get(p.getFiledName()));
//                }
//            }
//        });
//
//        goods.setId(spu.getId());
//        goods.setSubTitle(spu.getSubTitle());
//        goods.setBrandId(spu.getBrandId());
//        goods.setCid1(spu.getCid1());
//        goods.setCid2(spu.getCid2());
//        goods.setCid3(spu.getCid3());
//        goods.setCreateTime(spu.getCreateTime());
//        goods.setAll(spu.getTitle() + " " + StringUtils.join(names, " "));
//        goods.setAll(spu.getTitle());
//        goods.setPrice(prices);
//        goods.setSkus(mapper.writeValueAsString(skuList));
//        goods.setSpecs(specMap);
//        return goods;

        return null;
    }
}
