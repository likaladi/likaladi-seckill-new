package com.likaladi.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.likaladi.base.BaseServiceImpl;
import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.*;
import com.likaladi.goods.entity.Category;
import com.likaladi.goods.entity.Sku;
import com.likaladi.goods.entity.Spu;
import com.likaladi.goods.entity.SpuDetail;
import com.likaladi.goods.mapper.SpuMapper;
import com.likaladi.goods.service.CategoryService;
import com.likaladi.goods.service.SkuService;
import com.likaladi.goods.service.SpuDetailService;
import com.likaladi.goods.service.SpuService;
import com.likaladi.goods.vo.SpuVo;
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
    public SpuVo querySpuSkuById(Long id) {
        SpuVo spuVo = spuMapper.querySpuInfo(id);
        if(Objects.isNull(spuVo)){
            ErrorBuilder.throwMsg("不存在匹配的记录");
        }

        setSpuVos(Arrays.asList(spuVo));

        return spuVo;
    }

    @Override
    public SpuVo querySpuSkuDetail(Long id) {
        SpuDetail spuDetail = spuDetailService.findById(id);
        SpuVo spuVo = new SpuVo();
        spuVo.setId(id);
        spuVo.setDescription(spuDetail.getDescription());
        spuVo.setAttrs(JSONObject.parseArray(spuDetail.getSpecifications(), SpuAttrDto.class));
        spuVo.setSpecs(JSONObject.parseArray(spuDetail.getSpecTemplate(), SpuSpecDto.class));

        List<Sku> skus = skuService.findListBy("spuId", id);

        List<SpuSkuDto> spuSkuDtoList = skus.stream().map(sku -> {
            return SpuSkuDto.builder()
                    .price(sku.getPrice())
                    .stockNum(sku.getStockNum())
                    .barcode(sku.getBarcode())
                    .imageList(JSONObject.parseArray(sku.getImages(), String.class))
                    .specs(JSONObject.parseArray(sku.getOwnSpec(), SkuSpecDto.class))
                    .build();
        }).collect(Collectors.toList());

        return spuVo;
    }

    @Override
    public PageResult<SpuVo> listByPage(SpuQueryDto spuQueryDto) {

        PageHelper.startPage(spuQueryDto.getPage(),spuQueryDto.getRows());

        Page<SpuVo> page = (Page<SpuVo>) spuMapper.selectByPage(spuQueryDto);

        setSpuVos(page.getResult());

        return new PageResult<>(page.getTotal(), page.getResult(), null);
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

    private void setSpuVos(List<SpuVo> spuVos){

        /** Set集合存储去重的分类id */
        Set<Long> categorySet = new HashSet<>(spuVos.size() * 3);
        spuVos.forEach(spuVo -> {
            categorySet.add(spuVo.getCid1());
            categorySet.add(spuVo.getCid2());
            categorySet.add(spuVo.getCid3());
        });

        List<Category> categories = categoryService.findByIds(new ArrayList(categorySet));

        /** 将categories集合 转成对应的categoryMap：id -> Category  */
        Map<Long, Category> categoryMap = categories.stream().collect(Collectors.toMap(Category::getId, Function.identity()));
        spuVos.forEach(spuVo -> {
            spuVo.setCategoryName1(categoryMap.get(spuVo.getCid1()).getName());
            spuVo.setCategoryName2(categoryMap.get(spuVo.getCid2()).getName());
            spuVo.setCategoryName3(categoryMap.get(spuVo.getCid3()).getName());
        });

    }

}
