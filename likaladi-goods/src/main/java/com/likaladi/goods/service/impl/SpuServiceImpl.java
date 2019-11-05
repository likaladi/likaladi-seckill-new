package com.likaladi.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.likaladi.base.BaseServiceImpl;
import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.*;
import com.likaladi.goods.entity.*;
import com.likaladi.goods.mapper.SpuMapper;
import com.likaladi.goods.service.*;
import com.likaladi.goods.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liwen
 */
@Service
public class SpuServiceImpl extends BaseServiceImpl<Spu> implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailService spuDetailService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpecService specService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSpuSku(SpuDto spuDto) {

        Spu spu = dealWithSpu(spuDto, true);

        dealWithSpuDetail(spu.getId(), spuDto, true);

        dealWithSkus(spu.getId(), spuDto, true);

    }

    @Override
    public void updateSpuSku(SpuDto spuDto) {

        Spu spu = dealWithSpu(spuDto, false);

        dealWithSpuDetail(spu.getId(), spuDto, false);

        dealWithSkus(spu.getId(), spuDto, false);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteSpuSKu(List<Long> ids) {
        if(spuMapper.deleteSpuByIds(ids) <= 0){
            ErrorBuilder.throwMsg("删除失败");
        }
    }

    @Override
    public SpuVo querySpuById(Long id) {

        SpuVo spuVo = spuMapper.querySpuById(id);

        SpuDetail spuDetail = spuDetailService.findById(id);

        if(Objects.isNull(spuVo)){
            ErrorBuilder.throwMsg("不存在匹配的记录");
        }

        Map<Long, CategoryVo> categoryMap = getCategoryMap(spuVo);

        spuVo.setCategory1(categoryMap.get(spuVo.getCid1()));
        spuVo.setCategory2(categoryMap.get(spuVo.getCid2()));
        spuVo.setCategory3(categoryMap.get(spuVo.getCid3()));

        spuVo.setDescription(spuDetail.getDescription());

        return spuVo;
    }

    @Override
    public SpuDetailVo querySpuSkuDetail(Long id) {

        Spu spu = this.findById(id);

        SpuDetail spuDetail = spuDetailService.findById(id);

        if(Objects.isNull(spuDetail)){
            ErrorBuilder.throwMsg("不存在匹配的记录");
        }

        SpuSpecVo spuSpecVo = specService.listByCategoryId(spu.getCid3(), spuDetail);

        List<Sku> skus = skuService.findListBy("spuId", id);

        List<SpuSkuVo> spuSkuVoList = skus.stream().map(sku -> {
            SpuSkuVo spuSkuVo = new SpuSkuVo();
            BeanUtils.copyProperties(sku, spuSkuVo);
            spuSkuVo.setSkuId(sku.getId());
            spuSkuVo.setImageList(JSONObject.parseArray(sku.getImages(), String.class));

            Map<String, Object> jsonMap = JSONObject.parseObject(sku.getOwnSpec(), Map.class);
            spuSkuVo.setSpecs(jsonMap);
            return spuSkuVo;
        }).collect(Collectors.toList());

        return SpuDetailVo.builder()
                .spuSpecVo(spuSpecVo)
                .skus(spuSkuVoList)
                .build();
    }

    @Override
    public PageResult<SpuVo> listByPage(SpuQueryDto spuQueryDto) {

        PageHelper.startPage(spuQueryDto.getPage(),spuQueryDto.getRows());

        Page<SpuVo> page = (Page<SpuVo>) spuMapper.selectByPage(spuQueryDto);

        setCategoryMap(page.getResult());

        return new PageResult<>(page.getTotal(), page.getResult(), null);
    }

    @Override
    public int queryCountByCateogryIds(List<Long> categoryIds) {
        return spuMapper.queryCountByCateogryIds(categoryIds);
    }

    private Spu dealWithSpu(SpuDto spuDto, Boolean isSave){
        Spu spu = new Spu();
        BeanUtils.copyProperties(spuDto, spu);
        spu.setMinPrice(
                /** 最低销售价 */
                spuDto.getSkus()
                        .stream().min((u1, u2) -> u1.getPrice().compareTo(u2.getPrice())).get().getPrice()
        );
        spu.setMaxPrice(
                /** 最高销售价 */
                spuDto.getSkus()
                        .stream().max((u1, u2) -> u1.getPrice().compareTo(u2.getPrice())).get().getPrice()
        );
        if(isSave){
            this.save(spu);
        }else{
            this.update(spu);
        }

        return spu;
    }

    private void dealWithSpuDetail(Long spuId, SpuDto spuDto, boolean isSave){
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spuId);
        spuDetail.setDescription(spuDto.getDescription());
        spuDetail.setSpecifications(JSONObject.toJSONString(spuDto.getAttrs()));
        spuDetail.setSpecTemplate(CollectionUtils.isEmpty(spuDto.getSpecs()) ?
                JSONObject.toJSONString(Arrays.asList()) : JSONObject.toJSONString(spuDto.getSpecs()));
        if(isSave){
            spuDetailService.save(spuDetail);
        }else {
            spuDetailService.update(spuDetail);
        }
    }

    private void dealWithSkus(Long spuId, SpuDto spuDto, boolean isSave){
        List<SpuSkuDto> spuSkuDtos = spuDto.getSkus();

        Date date = new Date();
        List<Sku> skus = spuDto.getSkus().stream().map(spuSkuDto -> {
            Sku sku = new Sku();
            BeanUtils.copyProperties(spuSkuDto, sku);
            sku.setSpuId(spuId);
            sku.setImages(JSONObject.toJSONString(spuSkuDto.getImageList()));
            sku.setIndexes(CollectionUtils.isEmpty(spuSkuDto.getIndexList()) ?
                    JSONObject.toJSONString(Arrays.asList()) : JSONObject.toJSONString(spuSkuDto.getIndexList()));
            sku.setOwnSpec(CollectionUtils.isEmpty(spuSkuDto.getSpecs()) ?
                    JSONObject.toJSONString(Arrays.asList()) : JSONObject.toJSONString(spuSkuDto.getSpecs()));
            sku.setIsEnable(true);
            sku.setCreateTime(date);
            sku.setUpdateTime(date);
            return sku;
        }).collect(Collectors.toList());

        if(!isSave){
            Sku sku = new Sku();
            sku.setSpuId(spuId);
            skuService.deleteByCondition(sku);
        }
        skuService.save(skus);
    }

    private Map<Long, CategoryVo> getCategoryMap(SpuVo spuVo){
        Set<Long> categorySet = new HashSet<>(3);
        categorySet.add(spuVo.getCid1());
        categorySet.add(spuVo.getCid2());
        categorySet.add(spuVo.getCid3());

        List<CategoryVo> categories = categoryService.queryByIds(categorySet);

        /** 将categories集合 转成对应的categoryMap：id -> Category  */
        Map<Long, CategoryVo> categoryMap = categories.stream().collect(Collectors.toMap(CategoryVo::getId, Function.identity()));

        return categoryMap;
    }

    private void setCategoryMap(List<SpuVo> spuVos){

        /** Set集合存储去重的分类id */
        Set<Long> categorySet = new HashSet<>(spuVos.size() * 3);
        spuVos.forEach(spuVo -> {
            categorySet.add(spuVo.getCid1());
            categorySet.add(spuVo.getCid2());
            categorySet.add(spuVo.getCid3());
        });

        List<CategoryVo> categories = categoryService.queryByIds(categorySet);

        /** 将categories集合 转成对应的categoryMap：id -> Category  */
        Map<Long, CategoryVo> categoryMap = categories.stream().collect(Collectors.toMap(CategoryVo::getId, Function.identity()));

        spuVos.forEach(spuVo -> {
            spuVo.setCategory1(categoryMap.get(spuVo.getCid1()));
            spuVo.setCategory2(categoryMap.get(spuVo.getCid2()));
            spuVo.setCategory3(categoryMap.get(spuVo.getCid3()));
        });

    }

}
