package com.likaladi.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.likaladi.base.BaseServiceImpl;
import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.SpecQueryDto;
import com.likaladi.goods.entity.Specification;
import com.likaladi.goods.entity.SpuDetail;
import com.likaladi.goods.enums.SpecTypEnum;
import com.likaladi.goods.mapper.SpecificationMapper;
import com.likaladi.goods.service.SpecService;
import com.likaladi.goods.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liwen
 */
@Service
public class SpecServiceImpl extends BaseServiceImpl<Specification> implements SpecService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public SpecParamVo queryById(Long id) {

        SpecParamVo specParamVo = specificationMapper.queryById(id);
        if(Objects.isNull(specParamVo)){
            ErrorBuilder.throwMsg("记录不存在");
        }
        specParamVo.setDatas(JSONObject.parseArray(specParamVo.getOptions(), String.class));
        specParamVo.setTypeName(SpecTypEnum.getValue(specParamVo.getType()));

        return specParamVo;
    }

    @Override
    public SpuSpecVo getSpuSpecVo(List<Long> ids) {
        List<SpecParamVo> specParamVos = specificationMapper.queryListByIds(ids);
        return getCommonSpuSpecVo(specParamVos, null);
    }

    @Override
    public PageResult<SpecParamVo> listByPage(SpecQueryDto specQueryDto) {
        PageHelper.startPage(specQueryDto.getPage(),specQueryDto.getRows());

        Page<SpecParamVo> page = (Page<SpecParamVo>) specificationMapper.selectByPage(specQueryDto);

        page.getResult().forEach(specVo -> {
            specVo.setDatas(JSONObject.parseArray(specVo.getOptions(), String.class));
            specVo.setTypeName(SpecTypEnum.getValue(specVo.getType()));
        });

        return new PageResult<>(page.getTotal(), page.getResult(), null);
    }

    @Override
    public SpuSpecVo listByCategoryId(Long categoryId, SpuDetail spuDetail) {

        List<SpecParamVo> specParamVos = specificationMapper.queryByCategoryId(categoryId);

        return getCommonSpuSpecVo(specParamVos, spuDetail);

    }

    @Override
    public SpuSpecVo formatBySpecAttrList(List<SpecParamVo> specParamVos, SpuDetail spuDetail) {
        return getCommonSpuSpecVo(specParamVos, spuDetail);
    }

    @Override
    public List<SpecParamVo> listByCategoryIds(Collection categoryIds) {
        List<SpecParamVo> specParamVos = specificationMapper.queryByCategoryIds(categoryIds);
        return specParamVos;
    }

    private SpuSpecVo getCommonSpuSpecVo(List<SpecParamVo> specParamVos, SpuDetail spuDetail){
        /** 筛选属性 */
        List<SpecParamVo> attrList = specParamVos.stream()
                .filter(specParamVo -> specParamVo.getIsGloab())
                .map(specParamVo -> {
                    specParamVo.setDatas(JSONObject.parseArray(specParamVo.getOptions(), String.class));
                    return specParamVo;
                 })
                .collect(Collectors.toList());

              /** 筛选规格 */
        List<SpecParamVo> specList = specParamVos.stream()
                .filter(specParamVo -> !specParamVo.getIsGloab())
                .map(specParamVo -> {
                    specParamVo.setDatas(JSONObject.parseArray(specParamVo.getOptions(), String.class));
                    return specParamVo;
                }).collect(Collectors.toList());

        /** 根据groupName分组将 attrList 转换对应的 attrMap： groupName -> List<SpecParamVo>*/
        Map<String, List<SpecParamVo>> attrMap = attrList.stream().collect(Collectors.groupingBy(SpecParamVo::getGroupName));

        /** 根据groupName分组将 specList 转换对应的 specMap： groupName -> List<SpecParamVo>*/
        Map<String, List<SpecParamVo>> specMap = specList.stream().collect(Collectors.groupingBy(SpecParamVo::getGroupName));

        Map<String, SpecAttrParamVo> attrVoMap = null;
        Map<String, SpecAttrParamVo> specVoMap = null;
        if(Objects.nonNull(spuDetail)){
            List<SpecAttrParamVo> attrParamList = JSONObject.parseArray(spuDetail.getSpecifications(), SpecAttrParamVo.class);
            List<SpecAttrParamVo> specParamList = JSONObject.parseArray(spuDetail.getSpecTemplate(), SpecAttrParamVo.class);

            /** 将List<SpecAttrParamVo> attrParamList 转成对应的attrVoMap：k -> SpecAttrParamVo */
            attrVoMap = attrParamList.stream().collect(Collectors.toMap(SpecAttrParamVo::getK, Function.identity()));
            /** 将List<SpecAttrParamVo> specParamList 转成对应的specVoMap：k -> SpecAttrParamVo */
            specVoMap = specParamList.stream().collect(Collectors.toMap(SpecAttrParamVo::getK, Function.identity()));
        }

        List<SpecVo> attrVoList = getSpecVo(attrMap, attrVoMap);

        List<SpecVo> specVoList = getSpecVo(specMap, specVoMap);

        return SpuSpecVo.builder()
                .attrs(attrVoList)
                .specs(specVoList)
                .build();
    }


    private List<SpecVo> getSpecVo(Map<String, List<SpecParamVo>> specMap, Map<String, SpecAttrParamVo> specAttrParamVoMap){

        List<SpecVo> specVoList = new ArrayList<>();

        specMap.forEach((k, v) -> {

            List<SpecAttrParamVo> spuSpecParamVos = v.stream().map(specParamVo -> {

                List<String> options = JSONObject.parseArray(specParamVo.getOptions(), String.class);

                SpecAttrParamVo specAttrParamVo = null;

                if(Objects.nonNull(specAttrParamVoMap) && !CollectionUtils.isEmpty(options)){

                    specAttrParamVo = specAttrParamVoMap.get(specParamVo.getId());

                    if(Objects.nonNull(specAttrParamVo)){
                        /** 获取商品规格属性选中值 */
                        List<String> choiceData = specAttrParamVoMap.get(specParamVo.getId()).getV();

                        /** 如果商品选中值不存在 分类对应的规格属性值中，则添加到分类的属性值中 */
                        choiceData.forEach(s -> {
                            if(!options.contains(s)){
                                options.add(s);
                            }
                        });
                    }
                }

                return SpecAttrParamVo.builder()
                        .k(String.valueOf(specParamVo.getId()))
                        .v(Objects.isNull(specAttrParamVoMap) ? Arrays.asList() : Objects.nonNull(specAttrParamVo) ? specAttrParamVo.getV() : Arrays.asList())
                        .name(specParamVo.getName())
                        .options(options)
                        .type(specParamVo.getType())
                        .isSearch(specParamVo.getIsSearch())
                        .build();

            }).collect(Collectors.toList());

            specVoList.add(
                    SpecVo.builder()
                            .groupName(k)
                            .params(spuSpecParamVos)
                            .build()
            );
        });

        return specVoList;
    }
}
