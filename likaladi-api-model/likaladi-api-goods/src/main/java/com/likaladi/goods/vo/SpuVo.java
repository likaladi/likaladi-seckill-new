package com.likaladi.goods.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "商品返回对象")
public class SpuVo {

    private Long id;

    @ApiModelProperty(value = "商家id")
    private Long sellerId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "子标题")
    private String subTitle;

    @ApiModelProperty(value = "主图")
    private String image;

    @ApiModelProperty(value = "1级类目id")
    private CategoryVo category1;

    @ApiModelProperty(value = "2级类目id")
    private CategoryVo category2;

    @ApiModelProperty(value = "3级类目id")
    private CategoryVo category3;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    private String description;

    @ApiModelProperty(value = "最低销售价")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "最高销售价")
    private BigDecimal maxPrice;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JsonIgnore
    private Long cid1;

    @JsonIgnore
    private Long cid2;

    @JsonIgnore
    private Long cid3;
}
