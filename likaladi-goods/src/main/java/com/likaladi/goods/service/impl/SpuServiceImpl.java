package com.likaladi.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.likaladi.base.BaseServiceImpl;
import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.*;
import com.likaladi.goods.entity.*;
import com.likaladi.goods.enums.SaleableEnum;
import com.likaladi.goods.enums.SpuSkuEnum;
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

        List<SpuSkuVo> spuSkuVoList = getSpuSkuVo(skus);

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

    @Override
    public PageResult<SpuSearchVo> upperShelfSpu(SpuQueryDto spuQueryDto) {

        PageResult<SpuVo> pageResult = listByPage(spuQueryDto);

        List<SpuSearchVo> spuSearchVos = null;

        if(!CollectionUtils.isEmpty(pageResult.getItems())){
            spuSearchVos = querySpuSkuDetailByIds(pageResult.getItems());
        }

        return new PageResult<>(pageResult.getTotal(), spuSearchVos, null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editState(SpuSkuStateDto spuSkuStateDto) {
        if(Objects.equals(SpuSkuEnum.SPU.getCode(), spuSkuStateDto.getOperateType())){
            int result = spuMapper.updateMoreSpuSaleable(spuSkuStateDto.getIds(), spuSkuStateDto.getSaleable());
            if(result <= 0){
                ErrorBuilder.throwMsg("更新spu上下级状态失败");
            }
            skuService.updateSkuStateBySpuIds(spuSkuStateDto.getIds(), spuSkuStateDto.getSaleable());
            return;
        }

        List<Sku> skus = skuService.findByIds(spuSkuStateDto.getIds());
        if(Objects.equals(spuSkuStateDto.getIds(), skus.size())){
            ErrorBuilder.throwMsg("sku存在不匹配的记录");
        }

        List<Long> spuIds = skus.stream().map(Sku::getSpuId).collect(Collectors.toList());

        //修改SKU上架操作
        if(Objects.equals(SaleableEnum.UPPER_SHELF.getCode(), spuSkuStateDto.getSaleable())){
            skuService.updateSkuStateByIds(spuSkuStateDto.getIds(), spuSkuStateDto.getSaleable());
            Spu spu = new Spu();
            spu.setId(spuIds.get(0));
            spu.setSaleable(true);
            this.update(spu);
            return;
        }

        skuService.updateSkuStateByIds(spuSkuStateDto.getIds(), spuSkuStateDto.getSaleable());
        List<Sku> skuList = skuService.findListBy("spuId", spuIds.get(0));
        Spu spu = this.findById(spuIds.get(0));
        if(Objects.equals(SaleableEnum.UPPER_SHELF.getCode(), spuSkuStateDto.getSaleable())){
            //过滤当前SPU对应的下架状态的SKU数量
            int size = skuList.stream().
                    filter(sku -> Objects.equals(SaleableEnum.LOWER_SHELF.getCode(), sku.getState()))
                    .collect(Collectors.toList()).size();

            //如果SPU过滤后的下架状态的SKU数量和入参修改的id数量保持一致，则更新对应的SPU为下架状态
            if(Objects.equals(spuSkuStateDto.getIds().size(), size)){
                Spu spuContion = new Spu();
                spuContion.setId(spuIds.get(0));
                spuContion.setSaleable(false);
                this.update(spuContion);
            }
        }

    }


    private Spu dealWithSpu(SpuDto spuDto, Boolean isSave){
        Spu spu = new Spu();
        BeanUtils.copyProperties(spuDto, spu);
        if(isSave){
            spu.setId(null);
        }
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
            sku.setState(0);
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

        if(!CollectionUtils.isEmpty(categorySet)){
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

    private List<SpuSkuVo> getSpuSkuVo(List<Sku> skus){
        List<SpuSkuVo> spuSkuVoList = skus.stream().map(sku -> {
            SpuSkuVo spuSkuVo = new SpuSkuVo();
            BeanUtils.copyProperties(sku, spuSkuVo);
            spuSkuVo.setSkuId(sku.getId());
            spuSkuVo.setImageList(JSONObject.parseArray(sku.getImages(), String.class));

            Map<String, Object> jsonMap = JSONObject.parseObject(sku.getOwnSpec(), Map.class);
            spuSkuVo.setSpecs(jsonMap);
            return spuSkuVo;
        }).collect(Collectors.toList());

        return spuSkuVoList;
    }

    /**
     * List<SpuVo>转换对应的List<SpuDetailVo>
     * @param spuVos
     * @return
     */
    private List<SpuSearchVo> querySpuSkuDetailByIds(List<SpuVo> spuVos) {

        //将spuVos列表  转换对应的 categoryId 去重集合
        Set<Long> categoryIds = spuVos.stream().map(spuVo -> spuVo.getCid3()).collect(Collectors.toSet());
        //根据多个分类id查询规格属性列表
        List<SpecParamVo> specParamVos = specService.listByCategoryIds(categoryIds);
        //根据分类id分组 将specParamVos对象转换成对应的 specParamMap：categoryId ->  List<SpecParamVo>
        Map<Long, List<SpecParamVo>> specParamMap = specParamVos.stream().collect(Collectors.groupingBy(SpecParamVo::getCategoryId));

        //从spuVos列表中 取spuId列表
        List<Long> spuIds = spuVos.stream().map(SpuVo::getId).collect(Collectors.toList());
        //根据商品ids列表查询对应的 SpuDetail集合
        List<SpuDetail> spuDetails = spuDetailService.findByIds(spuIds);
        //将spuVos列表对象转换成对应的 spuVoMap：spuId -> SpuVo
        Map<Long, SpuDetail> spuDetailMap = spuDetails.stream().collect(Collectors.toMap(SpuDetail::getSpuId, Function.identity()));

        //根据多个spuIds查询sku列表
        List<Sku> skusList = skuService.queryBySpuIds(spuIds);
        //根据spuId分组 将skus对象转换成对应的 skuMap：spuId ->  List<Sku>
        Map<Long, List<Sku>> skuMap = skusList.stream().collect(Collectors.groupingBy(Sku::getSpuId));

        List<SpuSearchVo> spuSearchVos = new ArrayList<>();

        spuVos.forEach(spuVo -> {
            //获取对应分类的规格属性
            List<SpecParamVo> specParamVoList = specParamMap.get(spuVo.getCid3());
            SpuSpecVo spuSpecVo = specService.formatBySpecAttrList(specParamVoList, spuDetailMap.get(spuVo.getId()));
            List<SpuSkuVo> spuSkuVoList = getSpuSkuVo(skuMap.get(spuVo.getId()));
            spuSearchVos.add(
                    SpuSearchVo.builder().spuVo(spuVo).spuDetailVo(
                            SpuDetailVo.builder()
                                    .spuSpecVo(spuSpecVo)
                                    .skus(spuSkuVoList)
                                    .build()
                    ).build()
            );
        });

        return spuSearchVos;
    }

}
