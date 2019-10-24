package com.likaladi.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.likaladi.base.BaseServiceImpl;
import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.SpuDto;
import com.likaladi.goods.dto.SpuQueryDto;
import com.likaladi.goods.dto.SpuSkuDto;
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
        this.save(spu);

        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetail.setDescription(spuDto.getDescription());
        spuDetail.setSpecifications(JSONObject.toJSONString(spuDto.getAttrs()));
        spuDetail.setSpecTemplate(CollectionUtils.isEmpty(spuDto.getSpecs()) ?
                        JSONObject.toJSONString(Arrays.asList()) : JSONObject.toJSONString(spuDto.getSpecs()));
        spuDetailService.save(spuDetail);

        List<SpuSkuDto> spuSkuDtos = spuDto.getSkus();
        List<Sku> skus = spuDto.getSkus().stream().map(spuSkuDto -> {
            Sku sku = new Sku();
            BeanUtils.copyProperties(spuSkuDto, sku);
            sku.setImages(JSONObject.toJSONString(spuSkuDto.getImageList()));
            sku.setIndexes(CollectionUtils.isEmpty(spuSkuDto.getIndexList()) ?
                    JSONObject.toJSONString(Arrays.asList()) : JSONObject.toJSONString(spuSkuDto.getIndexList()));
            sku.setOwnSpec(CollectionUtils.isEmpty(spuSkuDto.getSpecs()) ?
                    JSONObject.toJSONString(Arrays.asList()) : JSONObject.toJSONString(spuSkuDto.getSpecs()));
            return sku;
        }).collect(Collectors.toList());
        skuService.save(skus);
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
        List<Long> categoryIds = Arrays.asList();
        categoryIds.add(spuVo.getCid1());
        categoryIds.add(spuVo.getCid2());
        categoryIds.add(spuVo.getCid3());
        List<Category> categories = categoryService.findByIds(categoryIds);

        /** 将categories集合 转成对应的categoryMap：id -> Category  */
        Map<Long, Category> categoryMap = categories.stream().collect(Collectors.toMap(Category::getId, Function.identity()));

        spuVo.setCategoryName1(categoryMap.get(spuVo.getCid1()).getName());
        spuVo.setCategoryName1(categoryMap.get(spuVo.getCid2()).getName());
        spuVo.setCategoryName1(categoryMap.get(spuVo.getCid3()).getName());
        return spuVo;
    }

    @Override
    public PageResult<SpuVo> listByPPage(SpuQueryDto spuQueryDto) {

        PageHelper.startPage(spuQueryDto.getPage(),spuQueryDto.getRows());

        Page<SpuVo> page = (Page<SpuVo>) spuMapper.selectByPage(spuQueryDto);

        return new PageResult<>(page.getTotal(), page.getResult(), null);
    }
}
